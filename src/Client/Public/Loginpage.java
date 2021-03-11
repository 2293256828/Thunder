package Client.Public;

import javax.swing.*;
import java.awt.*;

/**
 * 登录页，继承Jpanel，重写paintComponent方法
 */
public class Loginpage extends JPanel {
    JTextField usernamefield = new JTextField(16);
    JPasswordField passwordField = new JPasswordField(16);
    JButton login_Button = new JButton("登录");
    JButton register_button = new JButton("注册");
    JLabel jLabel1 = new JLabel("用户名：");
    JLabel jLabel2 = new JLabel("密码： ");

    /**
     * 构造器，UI设计
     */
    public Loginpage() {
        setLayout(null);
        usernamefield.setBounds(525, 500, 100, 30);
        jLabel1.setBounds(460, 500, 110, 30);
        passwordField.setBounds(525, 550, 100, 30);
        jLabel2.setBounds(460, 550, 110, 30);
        login_Button.setBounds(525, 600, 100, 30);
        register_button.setBounds(525, 670, 100, 30);
        Font font = new Font(null, 2, 15);
        jLabel1.setFont(font);
        jLabel2.setFont(font);
        jLabel2.setForeground(Color.CYAN);
        jLabel1.setForeground(Color.CYAN);
        add(usernamefield);
        add(passwordField);
        add(login_Button);
        add(register_button);
        add(jLabel1);
        add(jLabel2);
    }

    /**
     * 添加了背景图片
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Imageutil.image11, 0, 0, null);
    }
}