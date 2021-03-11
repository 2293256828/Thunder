package Server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class Databaseoperation {
    Connection connection;
    Socket s;

    public Databaseoperation(Socket socket) {
        connectdatabase();
        s = socket;
        int a = 0;
        try {
            a = s.getInputStream().read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (a == 0) {
            confirm();
        } else if (a == 1) {
            register();
        } else if (a == 2) {
            save_double_record();
        } else if (a == 3) {
            save_alone_record();
        } else if (a == 4) {
            get_double_rank_from_database();
        } else if (a == 5) {
            get_alone_rank_from_database();
        } else if (a == 6) {
            modify();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void modify() {

        String sql = "select*from thunder.userdata where username=?";
        String sql2 = "update thunder.userdata set password=? where username=?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String username = dis.readUTF();
            String oldpassword = dis.readUTF();
            String newpassword = dis.readUTF();
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("password").equals(oldpassword)) {
                    PreparedStatement ps2 = connection.prepareStatement(sql2);
                    ps2.setString(1, newpassword);
                    ps2.setString(2, username);
                    ps2.execute();
                    ps2.close();
                    connection.commit();
                    dos.write(1);
                } else {
                    dos.write(0);
                }
            }
            ps.close();
            rs.close();



        } catch (SQLException throwables) {
            throwables.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void confirm() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String username = dis.readUTF();
            String st = dis.readUTF();
            String password = null;
            String sql = "select*from userdata where username=?";
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            connection.commit();
            boolean b = rs.next();


            if (b) {//账号正确，判断密码
                password = rs.getString("password");
                if (st.equals(password)) {
                    s.getOutputStream().write(0);//0代表登录成功////
                } else {
                    s.getOutputStream().write(2);
                }//2是密码错误
            } else {
                s.getOutputStream().write(1);//1是用户名不存在
            }
            ps.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectdatabase() {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/thunder?serverTimezone=UTC", "root", "ab135799");
            connection = ConnectionPool.getConn();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void get_alone_rank_from_database() {

        String rank = "";
        String sql = "select username,score from thunder.rank order by score desc limit 10";
        try {
            int i = 1;
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String username = dis.readUTF();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                rank += "NO." + i++ + ":        " + rs.getString("username") + "           " + rs.getInt("score") + "分\n";
            }
            dos.writeUTF("Rank"+"     "+"Username"+"     "+"Score  \n"+rank + "我的历史最好成绩： " + get_alone_max_score(username) + "分");
            rs.close();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void save_alone_record() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int score = s.getInputStream().read();
            String p1_user_name = dis.readUTF();
            if (score > get_alone_max_score(p1_user_name)) {
                String sql = "update thunder.userdata set alonemaxscore=? where username=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, score);
                ps.setString(2, p1_user_name);
                connection.setAutoCommit(false);
                ps.execute();
                connection.commit();
            }
            String sql = "insert into thunder.rank values(id,?,?)";
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, p1_user_name);
            ps.setInt(2, score);
            ps.execute();
            ps.close();
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save_double_record() {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int totalscore = s.getInputStream().read();
            String p1_user_name = dis.readUTF();
            String p2_user_name = dis.readUTF();
            String double_username = p1_user_name + "与" + p2_user_name;
            if (totalscore > get_double_max_score(p1_user_name)) {
                String sql = "update thunder.userdata set doublemaxscore=?,bestcompanion=? where username=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, totalscore);
                ps.setString(2, p2_user_name);
                ps.setString(3, p1_user_name);
                connection.setAutoCommit(false);
                ps.execute();
                connection.commit();
            }
            if (totalscore > get_double_max_score(p2_user_name)) {
                String sql = "update thunder.userdata set doublemaxscore=?,bestcompanion=? where username=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, totalscore);
                ps.setString(2, p1_user_name);
                ps.setString(3, p2_user_name);
                connection.setAutoCommit(false);
                ps.execute();
                connection.commit();
            }
            String sql = "insert into thunder.doublerank values(id,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            ps.setString(1, double_username);
            ps.setInt(2, totalscore);
            ps.execute();
            ps.close();
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get_double_rank_from_database() {
        String sql = "select doubleusername,totalscore from thunder.doublerank order by totalscore desc limit 10";
        String sql2 = "select bestcompanion from thunder.userdata where username=?";
        String rank = "";
        String mybestcompanion = "";
        try {
            int i = 1;
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String username = dis.readUTF();
            PreparedStatement ps = connection.prepareStatement(sql);
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            connection.setAutoCommit(false);
            ps2.setString(1, username);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            connection.commit();
            while (rs.next()) {
                rank += "NO." + i++ + ":   " + rs.getString("doubleusername") + "     " + rs.getInt("totalscore") + "分\n";
            }
            while (rs2.next()) {
                mybestcompanion = rs2.getString("bestcompanion");
            }
            rs.close();
            rs2.close();
            ps.close();
            ps2.close();
            dos.writeUTF("Rank"+"     "+"Username"+"        "+"Score  \n"+rank + "我的历史最好成绩：" + get_double_max_score(username) + "分\n" + "最佳拍档：" + mybestcompanion);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int get_double_max_score(String username) {
        int my_max_double_score = 0;
        String sql = "select doublemaxscore from thunder.userdata where username=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            connection.commit();

            while (rs.next()) {
                my_max_double_score = rs.getInt("doublemaxscore");
            }
            rs.close();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return my_max_double_score;
    }

    public int get_alone_max_score(String username) {
        int my_max_alone_score = 0;
        String sql = "select alonemaxscore from thunder.userdata where username=?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            connection.commit();
            while (rs.next()) {
                my_max_alone_score = rs.getInt("alonemaxscore");
            }
            rs.close();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return my_max_alone_score;
    }


    public void register() {

        String sql = "insert into thunder.userdata values(?,?,alonemaxscore,doublemaxscore,bestcompanion)";
        try {

            DataInputStream dis = new DataInputStream(s.getInputStream());
            String username = dis.readUTF();
            String password = dis.readUTF();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.execute();
            connection.commit();
            ps.close();
            s.getOutputStream().write(0);//0是注册成功
        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                s.getOutputStream().write(1);//1是用户名已存在
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}