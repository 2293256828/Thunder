package Client.Public;

import javax.swing.*;
import java.awt.*;


/**
 * 注册页，继承Jpanel，重写paintComponent方法
 */
public class Registerpage extends JPanel {
       JTextField usernamefield=new JTextField(12);
       JPasswordField passwordField=new JPasswordField(12);
       JPasswordField passwordField2=new JPasswordField(12);
       JButton register_button2=new JButton("注册");
       JButton return_jButton=new JButton("返回");
       JLabel jLabel=new JLabel("输入用户名:");
       JLabel jLabel2=new JLabel("输入密码:");
       JLabel jLabel3=new JLabel("确认密码:");

    /**
     * 构造器，设计了UI
     */
    public Registerpage(){
            setLayout(null);
            Font font=new Font(null,2,15);
            jLabel.setFont(font);
            jLabel2.setFont(font);
            jLabel3.setFont(font);
            jLabel2.setForeground(Color.CYAN);
            jLabel.setForeground(Color.CYAN);
            jLabel3.setForeground(Color.CYAN);
            usernamefield.setBounds(525,500,100,30);
            jLabel.setBounds(440,500,100,30);
            passwordField.setBounds(525,550,100,30);
            jLabel2.setBounds(440,550,100,30);
            passwordField2.setBounds(525,600,100,30);
            jLabel3.setBounds(440,600,100,30);
            register_button2.setBounds(525,650,100,30);
            return_jButton.setBounds(20,20,100,30);
            add(usernamefield);
            add(passwordField);
            add(passwordField2);
            add(register_button2);
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
        public void paintComponent(Graphics g){
            super.paintComponent(g);
          g.drawImage(Imageutil.image11,0,0,null);
        }
    }


