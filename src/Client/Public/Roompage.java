package Client.Public;

import javax.swing.*;
import java.awt.*;

/**
 * 房间页，继承Jpanel，重写paintComponent方法
 */
public class Roompage extends JPanel {
    JButton startbutton = new JButton("开始游戏");
    JLabel jLabel = new JLabel();

    /**
     * 构造器，传入的port是房间号。UI设计
     * @param port
     */
    public Roompage(int port) {
        setLayout(null);
        jLabel.setFont(new Font(null, 6, 45));
        jLabel.setForeground(Color.BLACK);
        jLabel.setSize(320, 300);
        jLabel.setLocation(20, 20);
        startbutton.setBounds(500, 630, 120, 40);
        jLabel.setText("房间号:" + port);
        add(startbutton);
        add(jLabel);
    }

    /**
     * 添加了背景图片
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Imageutil.image11, 0, 0, null);
    }


}
