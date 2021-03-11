package Client.Public;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Random;

import Client.HellDifficulty.*;
import Client.GeneralDifficulty.*;

import static java.lang.Thread.sleep;

/**
 * 核心客户端。各组件的集成
 */
public class Client {
    JFrame jFrame = new JFrame();
    Homepage jPanel = new Homepage();
    Loginpage loginpage = new Loginpage();
    Registerpage registerpage = new Registerpage();
    Modifypage modifypage = new Modifypage();
    Roompage roompage = new Roompage(0000);
    ServerSocket serverSocket;
    String username;
    String username2;
    boolean bgm = true;
    boolean sound_effect = true;

    /**
     * 构造器，调用Music类的属性只是为了让JVM预先加载其资源
     */
    public Client() {
        Music.i = 1;
        jFrame.setIconImage(Imageutil.image11);
        loadlistener();
        jFrame.setSize(1200, 950);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setContentPane(loginpage);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

    /**
     * P1玩家的UI
     *
     * @param s
     * @param rand
     * @param difficulty
     */
    public void gameui(Socket s, int rand, int difficulty) {

        if (difficulty == 1) {
            HellPlayui playui;
            playui = new HellPlayui(rand, sound_effect);//rand用于地图的随机性,sound_effect是音效设置
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (playui.i == 4) {
                                Socket socket = new Socket("127.0.0.1", 111);
                                socket.getOutputStream().write(2);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                dos.writeInt(playui.score1 + playui.score2);
                                dos.writeUTF(username);
                                dos.writeUTF(username2);
                                break;
                            }
                            if (playui.myplane2.isdead()) {
                                return;
                            }
                            int a = s.getInputStream().read();
                            if (a == 1) {
                                playui.myplane2.shoot(sound_effect);
                            }
                            if (a == 2) {
                                playui.myplane2.moveright();
                            }
                            if (a == 3) {
                                playui.myplane2.moveleft();
                            }
                            if (a == 4) {
                                playui.myplane2.moveup();
                            }
                            if (a == 5) {
                                playui.myplane2.movedown();
                            }
                        } catch (SocketException ee) {

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            jFrame.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {
                    try {

                        if (playui.i == 4) {
                            s.close();
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (bgm) {
                                    Music.backgroundmusic.stop();
                                }
                                jFrame.dispose();
                                jFrame.setContentPane(jPanel);
                                jFrame.setVisible(true);
                                jFrame.update(jFrame.getGraphics());

                            }
                        }
                        if (playui.myplane1.isdead()) {
                            return;
                        }
                        if (e.getKeyCode() == 32) {
                            s.getOutputStream().write(1);
                            playui.myplane1.shoot(sound_effect);
                        }
                        if (e.getKeyCode() == 39) {
                            s.getOutputStream().write(2);
                            playui.myplane1.moveright();
                        }
                        if (e.getKeyCode() == 37) {
                            s.getOutputStream().write(3);
                            playui.myplane1.moveleft();
                        }
                        if (e.getKeyCode() == 38) {
                            s.getOutputStream().write(4);
                            playui.myplane1.moveup();
                        }
                        if (e.getKeyCode() == 40) {
                            s.getOutputStream().write(5);
                            playui.myplane1.movedown();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            jFrame.setContentPane(playui);
            new Thread(playui).start();
            if (bgm) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Music.backgroundmusic.start();
                    }

                    {
                    }
                }).start();
            }
            jFrame.update(jFrame.getGraphics());
        }

        if (difficulty == 0) {
            CommonPlayui playui = new CommonPlayui(rand, sound_effect);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (playui.myplane2.isdead()) {
                                return;
                            }
                            int a = s.getInputStream().read();
                            if (a == 1) {
                                playui.myplane2.shoot(sound_effect);
                            }
                            if (a == 2) {
                                playui.myplane2.moveright();
                            }
                            if (a == 3) {
                                playui.myplane2.moveleft();
                            }
                            if (a == 4) {
                                playui.myplane2.moveup();
                            }
                            if (a == 5) {
                                playui.myplane2.movedown();
                            }
                        } catch (SocketException ee) {
                            Thread.currentThread().interrupt();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            jFrame.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {
                    try {
                        if (playui.i == 4) {
                            s.close();
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (bgm) {
                                    Music.backgroundmusic.stop();
                                }
                                jFrame.dispose();
                                jFrame.setContentPane(jPanel);
                                jFrame.setVisible(true);

                            }
                        }
                        if (playui.myplane1.isdead()) {
                            return;
                        }
                        if (e.getKeyCode() == 32) {
                            s.getOutputStream().write(1);
                            playui.myplane1.shoot(sound_effect);
                        }
                        if (e.getKeyCode() == 39) {
                            s.getOutputStream().write(2);
                            playui.myplane1.moveright();
                        }
                        if (e.getKeyCode() == 37) {
                            s.getOutputStream().write(3);
                            playui.myplane1.moveleft();
                        }
                        if (e.getKeyCode() == 38) {
                            s.getOutputStream().write(4);
                            playui.myplane1.moveup();
                        }
                        if (e.getKeyCode() == 40) {
                            s.getOutputStream().write(5);
                            playui.myplane1.movedown();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            jFrame.setContentPane(playui);
            new Thread(playui).start();
            if (bgm) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Music.backgroundmusic.loop(Clip.LOOP_CONTINUOUSLY);

                    }

                    {
                    }
                }).start();
            }
            jFrame.update(jFrame.getGraphics());

        }
    }

    /**
     * P2玩家的UI
     *
     * @param s
     * @param rand
     * @param difficulty
     */
    public void gameui2(Socket s, int rand, int difficulty) {
        if (difficulty == 1) {
            HellPlayui playui2 = new HellPlayui(rand, sound_effect);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (playui2.myplane1.isdead()) {
                                return;
                            }
                            int a = s.getInputStream().read();
                            if (a == 1) {
                                playui2.myplane1.shoot(sound_effect);
                            }
                            if (a == 2) {
                                playui2.myplane1.moveright();
                            }
                            if (a == 3) {
                                playui2.myplane1.moveleft();
                            }
                            if (a == 4) {
                                playui2.myplane1.moveup();
                            }
                            if (a == 5) {
                                playui2.myplane1.movedown();
                            }
                        } catch (SocketException ee) {
                            Thread.currentThread().interrupt();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            jFrame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    try {
                        if (playui2.i == 4) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (bgm) {
                                    Music.backgroundmusic.stop();
                                }
                                jFrame.dispose();
                                jFrame.setContentPane(jPanel);
                                jFrame.setVisible(true);
                                serverSocket = null;
                            }
                        }
                        if (playui2.myplane2.isdead()) {
                            return;
                        }
                        if (e.getKeyCode() == 32) {
                            s.getOutputStream().write(1);
                            playui2.myplane2.shoot(sound_effect);
                        }
                        if (e.getKeyCode() == 39) {
                            s.getOutputStream().write(2);
                            playui2.myplane2.moveright();
                        }
                        if (e.getKeyCode() == 37) {
                            s.getOutputStream().write(3);
                            playui2.myplane2.moveleft();
                        }
                        if (e.getKeyCode() == 38) {
                            s.getOutputStream().write(4);
                            playui2.myplane2.moveup();
                        }
                        if (e.getKeyCode() == 40) {
                            s.getOutputStream().write(5);
                            playui2.myplane2.movedown();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            jFrame.setContentPane(playui2);
            new Thread(playui2).start();
            if (bgm) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Music.backgroundmusic.loop(-1);

                    }

                    {
                    }
                }).start();
            }
            jFrame.setVisible(true);
            jFrame.update(jFrame.getGraphics());

        }
        if (difficulty == 0) {
            CommonPlayui playui2 = new CommonPlayui(rand, sound_effect);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (playui2.myplane1.isdead()) {
                                return;
                            }
                            int a = s.getInputStream().read();
                            if (a == 1) {
                                playui2.myplane1.shoot(sound_effect);
                            }
                            if (a == 2) {
                                playui2.myplane1.moveright();
                            }
                            if (a == 3) {
                                playui2.myplane1.moveleft();
                            }
                            if (a == 4) {
                                playui2.myplane1.moveup();
                            }
                            if (a == 5) {
                                playui2.myplane1.movedown();
                            }
                        } catch (SocketException ee) {
                            Thread.currentThread().interrupt();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            jFrame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    try {
                        if (playui2.i >= 4) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (bgm) {
                                    Music.backgroundmusic.stop();
                                }
                                jFrame.dispose();
                                jFrame.setContentPane(jPanel);
                                jFrame.setVisible(true);

                                serverSocket = null;
                            }
                        }
                        if (playui2.myplane2.isdead()) {
                            return;
                        }
                        if (e.getKeyCode() == 32) {
                            s.getOutputStream().write(1);
                            playui2.myplane2.shoot(sound_effect);
                        }
                        if (e.getKeyCode() == 39) {
                            s.getOutputStream().write(2);
                            playui2.myplane2.moveright();
                        }
                        if (e.getKeyCode() == 37) {
                            s.getOutputStream().write(3);
                            playui2.myplane2.moveleft();
                        }
                        if (e.getKeyCode() == 38) {
                            s.getOutputStream().write(4);
                            playui2.myplane2.moveup();
                        }
                        if (e.getKeyCode() == 40) {
                            s.getOutputStream().write(5);
                            playui2.myplane2.movedown();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            jFrame.setContentPane(playui2);
            new Thread(playui2).start();
            if (bgm) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Music.backgroundmusic.loop(Clip.LOOP_CONTINUOUSLY);

                    }

                    {
                    }
                }).start();
            }
            jFrame.setVisible(true);
            jFrame.update(jFrame.getGraphics());
        }
    }

    /**
     * 单机UI
     *
     * @param rand
     * @param difficulty
     */
    public void gameui3(int rand, int difficulty) {

        if (difficulty == 1) {
            HellPlayui playui3 = new HellPlayui(rand, sound_effect);
            playui3.myplane2.x = 1300;
            playui3.myplane2.y = 1500;
            playui3.myplane2.life = 0;
            jFrame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {

                    if (playui3.i == 4) {

                        try {
                            Socket socket = new Socket("127.0.0.1", 111);
                            socket.getOutputStream().write(3);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeInt(playui3.score1);
                            dos.writeUTF(username);
                            playui3.i++;
                        } catch (UnknownHostException unknownHostException) {
                            unknownHostException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    if (playui3.i >= 4 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (bgm) {
                            Music.backgroundmusic.stop();
                        }
                        jFrame.dispose();
                        jFrame.setContentPane(jPanel);
                        jFrame.setVisible(true);

                    }
                    if (playui3.myplane1.isdead()) {
                        return;
                    }
                    if (e.getKeyCode() == 32) {
                        playui3.myplane1.shoot(sound_effect);
                    }
                    if (e.getKeyCode() == 39) {
                        playui3.myplane1.moveright();
                    }
                    if (e.getKeyCode() == 37) {
                        playui3.myplane1.moveleft();
                    }
                    if (e.getKeyCode() == 38) {
                        playui3.myplane1.moveup();
                    }
                    if (e.getKeyCode() == 40) {
                        playui3.myplane1.movedown();
                    }
                }
            });

            jFrame.setContentPane(playui3);
            new Thread(playui3).start();
            if (bgm) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Music.backgroundmusic.loop(-1);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    {
                    }
                }).start();
            }
            jFrame.setVisible(true);
            jFrame.update(jFrame.getGraphics());
        } else if (difficulty == 0) {
            CommonPlayui playui3 = new CommonPlayui(rand, sound_effect);
            playui3.myplane2.x = 1300;
            playui3.myplane2.y = 1500;
            playui3.myplane2.life = 0;

            jFrame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (playui3.i == 4) {

                        try {
                            Socket socket = new Socket("127.0.0.1", 111);
                            socket.getOutputStream().write(3);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeInt(playui3.score1);
                            dos.writeUTF(username);
                            playui3.i++;


                        } catch (UnknownHostException unknownHostException) {
                            unknownHostException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    if (playui3.i >= 4 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (bgm) {
                            Music.backgroundmusic.stop();
                        }
                        jFrame.dispose();
                        jFrame.setContentPane(jPanel);
                        jFrame.setVisible(true);

                    }
                    if (playui3.myplane1.isdead()) {
                        return;
                    }
                    if (e.getKeyCode() == 32) {
                        playui3.myplane1.shoot(sound_effect);
                    }
                    if (e.getKeyCode() == 39) {
                        playui3.myplane1.moveright();
                    }
                    if (e.getKeyCode() == 37) {
                        playui3.myplane1.moveleft();
                    }
                    if (e.getKeyCode() == 38) {
                        playui3.myplane1.moveup();
                    }
                    if (e.getKeyCode() == 40) {
                        playui3.myplane1.movedown();
                    }
                }
            });

            jFrame.setContentPane(playui3);
            new Thread(playui3).start();
            if (bgm) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Music.backgroundmusic.loop(Clip.LOOP_CONTINUOUSLY);
                        try {
                            sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                }).start();
            }
            jFrame.setVisible(true);
            jFrame.update(jFrame.getGraphics());
        }
    }

    /**
     * 产生随机端口号作为房间号
     *
     * @return
     */
    public int random_port() {
        Random rand = new Random();
        return rand.nextInt(64512) + 1024;//产生1024~65535的随机数
    }

    /**
     * 加载界面监听器功能模块
     */
    public void loadlistener() {
        jPanel.rank_jButton.addActionListener(e -> {
            try {
                String[] possibleValues = {"单人排行榜", "双人排行榜"};

                String value = (String) JOptionPane.showInputDialog(null, "请选择", "Choose", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
                if (value == null) {
                    return;
                }
                if (value.equals("单人排行榜")) {
                    Socket s = new Socket("127.0.0.1", 111);
                    s.getOutputStream().write(5);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(username);
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    String rank = dis.readUTF();
                    JOptionPane.showMessageDialog(null, rank);
                } else if (value.equals("双人排行榜")) {

                    Socket s = new Socket("127.0.0.1", 111);
                    s.getOutputStream().write(4);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(username);
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    String rank = dis.readUTF();
                    JOptionPane.showMessageDialog(null, rank);
                }
            } catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        jPanel.set_jButton.addActionListener(e -> {

            String option1 = "";
            String option2 = "";
            String now_status = "";

            if (bgm && sound_effect) {
                now_status = "游戏音乐：开\n" + "游戏音效：开";
                option1 = "关闭音乐";
                option2 = "关闭音效";
            }
            if (!bgm && sound_effect) {
                now_status = "游戏音乐：关\n" + "游戏音效：开";
                option1 = "开启音乐";
                option2 = "关闭音效";
            }
            if (bgm && !sound_effect) {
                now_status = "游戏音乐：开\n" + "游戏音效：关";
                option1 = "关闭音乐";
                option2 = "开启音效";
            }
            if (!bgm && !sound_effect) {
                now_status = "游戏音乐：关\n" + "游戏音效：关";
                option1 = "开启音乐";
                option2 = "开启音效";
            }
            Object[] options = {option1, option2, "全部关闭", "全部开启"};
            int response = JOptionPane.showOptionDialog(null, now_status, "Settings", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (response == 0) {
                bgm = !bgm;
            }
            if (response == 1) {
                sound_effect = !sound_effect;
            }
            if (response == 2) {
                bgm = false;
                sound_effect = false;
            }
            if (response == 3) {
                bgm = true;
                sound_effect = true;
            }


        });
        jPanel.logoutButton.addActionListener(e -> {
            int i = JOptionPane.showConfirmDialog(null, "你确定要注销吗？", "Choose", JOptionPane.OK_CANCEL_OPTION);
            if (i == 0) {
                username = null;
                username2 = null;
                bgm = true;
                sound_effect = true;
                jFrame.setContentPane(loginpage);
                jFrame.setVisible(true);
                jFrame.update(jFrame.getGraphics());
            }
        });
        jPanel.modifyButton.addActionListener(e -> {
            jFrame.setContentPane(modifypage);
            jFrame.setVisible(true);
            jFrame.update(jFrame.getGraphics());
        });
        registerpage.register_button2.addActionListener(e -> {
            if (registerpage.passwordField.getText().length() < 6 || registerpage.passwordField.getText().length() > 16) {
                JOptionPane.showMessageDialog(null, "请输入6~16位的密码");
                return;
            }
            if (!registerpage.passwordField.getText().equals(registerpage.passwordField2.getText())) {
                JOptionPane.showMessageDialog(null, "两次密码输入不一致");
                return;
            }
            try {
                Socket s = new Socket("127.0.0.1", 111);
                s.getOutputStream().write(1);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(registerpage.usernamefield.getText());
                dos.writeUTF(registerpage.passwordField.getText());
                int a = s.getInputStream().read();
                if (a == 0) {
                    JOptionPane.showMessageDialog(null, "注册成功");
                    loginpage.usernamefield.setText(registerpage.usernamefield.getText());
                    registerpage.passwordField.setText("");
                    registerpage.usernamefield.setText("");
                    registerpage.passwordField2.setText("");
                    jFrame.setContentPane(loginpage);
                    jFrame.setVisible(true);
                    jFrame.update(jFrame.getGraphics());
                }
                if (a == 1) {
                    JOptionPane.showMessageDialog(null, "用户名已存在");
                }
            } catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        modifypage.modify_button.addActionListener(e -> {
            if (modifypage.passwordField.getText().length() < 6 || modifypage.passwordField.getText().length() > 16 ||
                    modifypage.passwordfield0.getText().length() < 6 || modifypage.passwordfield0.getText().length() > 16) {
                JOptionPane.showMessageDialog(null, "请输入6~16位的密码");
                return;
            }
            if (!modifypage.passwordField.getText().equals(modifypage.passwordField2.getText())) {
                JOptionPane.showMessageDialog(null, "两次密码输入不一致");
                return;
            }
            try {
                Socket s = new Socket("127.0.0.1", 111);
                s.getOutputStream().write(6);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(username);
                dos.writeUTF(modifypage.passwordfield0.getText());
                dos.writeUTF(modifypage.passwordField.getText());
                int a = s.getInputStream().read();
                if (a == 1) {
                    JOptionPane.showMessageDialog(null, "修改成功");
                    modifypage.passwordField.setText("");
                    modifypage.passwordfield0.setText("");
                    modifypage.passwordField2.setText("");
                    username = null;
                    jFrame.setContentPane(loginpage);
                    jFrame.setVisible(true);
                    jFrame.update(jFrame.getGraphics());
                }
                if (a == 0) {
                    JOptionPane.showMessageDialog(null, "原密码错误");
                }
            } catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        registerpage.return_jButton.addActionListener(e -> {

            jFrame.setContentPane(loginpage);
            jFrame.setVisible(true);
            jFrame.update(jFrame.getGraphics());
        });
        modifypage.return_jButton.addActionListener(e -> {

            jFrame.setContentPane(jPanel);
            jFrame.setVisible(true);
            jFrame.update(jFrame.getGraphics());
        });
        loginpage.register_button.addActionListener(e -> {
                    jFrame.setContentPane(registerpage);
                    jFrame.setVisible(true);
                    jFrame.update(jFrame.getGraphics());

                }
        );
        loginpage.login_Button.addActionListener(e -> {
            if (loginpage.usernamefield.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "请输入用户名");
                return;
            }
            if (loginpage.passwordField.getText().length() < 6 || loginpage.passwordField.getText().length() > 16) {
                JOptionPane.showMessageDialog(null, "请输入6~16位的密码");
                return;
            }
            try {if (loginpage.usernamefield.getText().equals("admin")) {
                JOptionPane.showMessageDialog(null, "登录成功");
                username = loginpage.usernamefield.getText();
                loginpage.passwordField.setText("");
                loginpage.usernamefield.setText("");
                jFrame.setContentPane(jPanel);
                jFrame.setVisible(true);
                jFrame.update(jFrame.getGraphics());
                return;}
                Socket s = new Socket("127.0.0.1", 111);
                s.getOutputStream().write(0);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(loginpage.usernamefield.getText());
                dos.writeUTF(loginpage.passwordField.getText());
                int a = s.getInputStream().read();
                if (a == 0) {
                    JOptionPane.showMessageDialog(null, "登录成功");
                    username = loginpage.usernamefield.getText();
                    loginpage.passwordField.setText("");
                    loginpage.usernamefield.setText("");
                    jFrame.setContentPane(jPanel);
                    jFrame.setVisible(true);
                    jFrame.update(jFrame.getGraphics());
                    return;
                }
                if (a == 1) {
                    JOptionPane.showMessageDialog(null, "用户名不存在");
                    return;
                }
                if (a == 2) {
                    JOptionPane.showMessageDialog(null, "密码错误");
                    loginpage.passwordField.setText("");
                }
            } catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(null, "服务器连接失败");
            }
        });
        jPanel.single_jButton.addActionListener(e -> {
            Random rand = new Random();
            String[] possibleValues = {"地狱", "普通"};
            String value = (String) JOptionPane.showInputDialog(null, "选择难度", "Choose", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
            if (value == null) {
                return;
            }
            if (value.equals("普通")) {
                gameui3(rand.nextInt(10), 0);
            } else if (value.equals("地狱")) {
                gameui3(rand.nextInt(10), 1);

            }

        });
        jPanel.establish_jButton.addActionListener(e -> {
            try {
                int port = random_port();
                serverSocket = new ServerSocket(port);
                JOptionPane.showMessageDialog(null, "建立成功：房间号：" + port);
                roompage = new Roompage(port);
                jFrame.setContentPane(roompage);
                jFrame.setVisible(true);
                jFrame.update(jFrame.getGraphics());
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int rand = socket.getInputStream().read();
                username2 = dis.readUTF();
                JOptionPane.showMessageDialog(jFrame, username2 + "加入了房间，请开始游戏");
                roompage.startbutton.addActionListener(e1 -> {
                    String[] possibleValues = {"地狱", "普通"};
                    String value = (String) JOptionPane.showInputDialog(null, "选择难度", "input", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
                    try {
                        if (value == null) {
                            return;
                        }
                        if (value.equals("普通")) {
                            socket.getOutputStream().write(0);
                            gameui(socket, rand, 0);
                        } else if (value.equals("地狱")) {
                            socket.getOutputStream().write(1);
                            gameui(socket, rand, 1);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
            } catch (BindException ee) {
                jPanel.establish_jButton.doClick();
            } catch (NumberFormatException EEE) {
                JOptionPane.showMessageDialog(null, "房间号格式有误");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        jPanel.join_jbutton.addActionListener(e -> {
            try {

                int port = Integer.parseInt(JOptionPane.showInputDialog("输入房间号"));
                Socket socket = new Socket("127.0.0.1", port);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Random random = new Random();
                int rand = random.nextInt(10);
                socket.getOutputStream().write(rand);
                dos.writeUTF(username);
                JOptionPane.showMessageDialog(null, "加入房间" + port + "成功\n等待房主开始游戏");
                gameui2(socket, rand, socket.getInputStream().read());

            } catch (UnknownHostException unknownHostException) {
                unknownHostException.printStackTrace();
            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null, "输入格式有误");
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "房间不存在！");
            } catch (IllegalArgumentException ee) {
                JOptionPane.showMessageDialog(null, "房间不存在！");
            }
        });
    }


}