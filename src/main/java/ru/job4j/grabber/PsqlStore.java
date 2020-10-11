package ru.job4j.grabber;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            //Class.forName(cfg.getProperty("posts.properties"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cnn.prepareStatement("insert into post " +
                "(name, text, link, created) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getName());
            statement.setString(2, post.getText());
            statement.setString(3, post.getLink());
            long time = post.getCreated().getTime();
            statement.setTimestamp(4, new Timestamp(time));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> list = new ArrayList<>();
        ResultSet rs;
        try (Statement st = cnn.createStatement()) {
            rs = st.executeQuery("SELECT * FROM post");
            while (rs.next()) {
                Post post = new Post();
                Integer postId = rs.getInt(1);
                post.setId(Integer.toString(postId));
                post.setName(rs.getString(2));
                post.setText(rs.getString(3));
                post.setLink(rs.getString(4));
                post.setCreated(rs.getTimestamp(5));
                list.add(post);
            }
        } catch(Exception e){
            throw new IllegalStateException(e);
        }
        return list;
    }

    @Override
    public Post findById(String id) {
        Post post = new Post();
        ResultSet rs;
        try (PreparedStatement st = cnn.prepareStatement("SELECT * FROM post where id=?")) {
            st.setInt(1, Integer.parseInt(id));
            rs = st.executeQuery();
            while (rs.next()) {
                int postId = rs.getInt(1);
                post.setId(Integer.toString(postId));
                post.setName(rs.getString(2));
                post.setText(rs.getString(3));
                post.setLink(rs.getString(4));
                post.setCreated(rs.getTimestamp(5));
            }
        } catch(Exception e){
            throw new IllegalStateException(e);
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        //String path = "C:/projects/job4j_grabber/src/main/resources";
        String path = "./src/main/resources/posts.properties";
        Properties properties= new Properties();
        properties.load(new FileInputStream(new File(path)));
        PsqlStore store = new PsqlStore(properties);
        SqlRuParse parser = new SqlRuParse();
        String linkOfFirstPost = "https://www.sql.ru/forum/1329123/inzhener-po-avtomatizirovannomu-testirovaniu-middle-senior";
        String linkOfSecondPost = "https://www.sql.ru/forum/1328504/ka-senior-java-developer-team-lead-180000-200000";
        String linkOfThirdPost = "https://www.sql.ru/forum/1328427/director-of-monetization-at-qublix";
        Post postFirst = parser.detail(linkOfFirstPost);
        Post postSecond = parser.detail(linkOfSecondPost);
        Post postThird = parser.detail(linkOfThirdPost);
        store.save(postFirst);
        store.save(postSecond);
        store.save(postThird);
        List<Post> list = store.getAll();
        for (var el : list) {
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
            System.out.println(post.getCreated());
        }
}