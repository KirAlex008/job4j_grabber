package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.Calendar;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {
    private final Properties cfg = new Properties();

    public Store store() {
        PsqlStore storeDB = new PsqlStore(cfg);
        return storeDB;
    }

    public void web(Store store) {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(Integer.parseInt(cfg.getProperty("port")))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (OutputStream out = socket.getOutputStream()) {
                        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                        for (Post post : store.getAll()) {
                            out.write(post.toString().getBytes());
                            out.write(System.lineSeparator().getBytes());
                            out.write(post.getId().toString().getBytes());
                            out.write(System.lineSeparator().getBytes());
                            out.write(post.getName().toString().getBytes());
                            out.write(System.lineSeparator().getBytes());
                            out.write(post.getText().toString().getBytes());
                            out.write(System.lineSeparator().getBytes());
                            out.write(post.getLink().toString().getBytes());
                            out.write(post.getCreated().toString().getBytes());

                            out.write(System.lineSeparator().getBytes());
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public void cfg() throws IOException {
        try (InputStream in = Grabber.class.getClassLoader().getResourceAsStream("app.properties")) {
            cfg.load(in);
        }
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException, InterruptedException {
        Date startTime = null;
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+3"));
        //int hour = 0;
        int start = 0;
        int stop = 1;
        //calendar.add(Calendar.HOUR, + hour);
        calendar.add(Calendar.MINUTE, + start);
        startTime = calendar.getTime();
        System.out.println(startTime);
        calendar.add(Calendar.MINUTE, + stop);
        Date stopTime = calendar.getTime();
        System.out.println(stopTime);
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("time")))
                //.repeatForever();
                .withRepeatCount(2);
        Trigger trigger = newTrigger()
                .startAt(startTime)
                .withSchedule(times)
                .endAt(stopTime)
                .build();
        scheduler.scheduleJob(job, trigger);
        //Thread.sleep(60000);
        //scheduler.shutdown();
    }

    public static class GrabJob implements Job {

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            List<Post> list = new ArrayList<>();
            String link = "https://www.sql.ru/forum/job-offers";
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (Store) map.get("store");
            Parse parse = (Parse) map.get("parse");
            try {
                list = parse.list(link);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (var el : list) {
                store.save(el);
            }
/*            List<Post> list2 = store.getAll();
            for (var el : list2) {
                System.out.println(el.getId());
                System.out.println(el.getName());
                System.out.println(el.getText());
                System.out.println(el.getLink());
                System.out.println(el.getCreated());
            }
            Post post = store.findById("1");
            System.out.println(post.getId());
            System.out.println(post.getName());
            System.out.println(post.getText());
            System.out.println(post.getLink());
            System.out.println(post.getCreated());*/
        }

    }

    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.cfg();
        Scheduler scheduler = grab.scheduler();
        Store store = grab.store();
        grab.init(new SqlRuParse(), store, scheduler);
        grab.web(store);
        //Thread.sleep(60000);
        scheduler.shutdown();
    }
}