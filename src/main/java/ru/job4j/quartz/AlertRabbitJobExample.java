package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbitJobExample {

    public static void main(String[] args) {
        //Store storeBD = new AlertRabbitForWork();
        //Connection conn = storeBD.init();
        try {
            //List<Long> store = new ArrayList<>();
            try (Store storeBD = new AlertRabbitForWork()) {
                storeBD.init();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", storeBD );
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            List<Long> store = storeBD.findAll();
            /*for (var elem : store) {
                System.out.println(elem + " TEST2");
            }*/
                System.out.println(store);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception se) {
            se.printStackTrace();
        }

    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            long timeForStore = System.currentTimeMillis();
            //System.out.println(timeForStore + "Test4");
            //List<Long> store = (List<Long>) context.getJobDetail().getJobDataMap().get("store");
            Store store = (Store) context.getJobDetail().getJobDataMap().get("store");
            /*for (var elem : store) {
                System.out.println(elem + " TEST2");
            }*/
            //System.out.println(store);
            store.add(timeForStore);
        }
    }
}
