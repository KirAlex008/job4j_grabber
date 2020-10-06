
package ru.job4j.quartz;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class  AlertRabbitDB implements Store{
    private final Connection cn;

    public AlertRabbitDB(Connection cn) {
        this.cn = cn;
    }

    public Connection init() {
        try (InputStream in = AlertRabbitDB.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("jdbc.driver"));
            return DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void add(long time) {
        try (PreparedStatement statement = this.cn.prepareStatement("insert into rabbit (create_date) values (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, new Timestamp(time));
            int Ans = statement.executeUpdate();
            //System.out.println(Ans + " Test");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement subSt = cn.prepareStatement("SELECT * FROM rabbit")) {
            ResultSet rs = subSt.executeQuery();
            while (rs.next())
            {
                System.out.println(String.format("%s", rs.getTimestamp("create_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //throw new IllegalStateException("Could not create new date");
    }

    @Override
    public List<Long> findAll() {
        List<Long> list = new ArrayList<>();
        ResultSet rs;
        try (Statement st = cn.createStatement()) {
            rs = st.executeQuery("SELECT * FROM rabbit");
            while (rs.next()) {
                long time = rs.getTimestamp(2).getTime();
                list.add(time);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return list;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }


}

