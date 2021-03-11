package Client.Public;

import javax.swing.*;
import java.awt.*;

/**
 * 改密页面，继承Jpanel，重写paintComponent方法
 */
public class Modifypage extends JPanel {
    JPasswordField passwordfield0 = new JPasswordField(16);
    JPasswordField passwordField = new JPasswordField(16);
    JPasswordField passwordField2 = new JPasswordField(16);
    JButton modify_button = new JButton("修改密码");
    JButton return_jButton = new JButton("返回");
    JLabel jLabel = new JLabel("输入旧密码:");
    JLabel jLabel2 = new JLabel("输入新密码:");
    JLabel jLabel3 = new JLabel("确认新密码:");

    /**
     * 构造器，设计UI
     */
    public Modifypage() {
        setLayout(null);
        Font font = new Font(null, 2, 15);
        jLabel.setFont(font);
        jLabel2.setFont(font);
        jLabel3.setFont(font);
        jLabel2.setForeground(Color.CYAN);
        jLabel.setForeground(Color.CYAN);
        jLabel3.setForeground(Color.CYAN);
        passwordfield0.setBounds(525, 500, 100, 30);
        jLabel.setBounds(440, 500, 100, 30);
        passwordField.setBounds(525, 550, 100, 30);
        jLabel2.setBounds(440, 550, 100, 30);
        passwordField2.setBounds(525, 600, 100, 30);
        jLabel3.setBounds(440, 600, 100, 30);
        modify_button.setBounds(525, 650, 100, 30);
        return_jButton.setBounds(20, 20, 100, 30);
        add(passwordfield0);
        add(passwordField);
        add(passwordField2);
        add(modify_button);
        add(return_jButton);
        add(jLabel);
        add(jLabel2);
        add(jLabel3);
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
