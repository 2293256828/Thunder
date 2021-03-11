package Client.Public;

import javax.swing.*;
import java.awt.*;

/**
 * 主页，继承Jpanel，重写paintComponent方法
 */
public class Homepage extends JPanel {
    JButton single_jButton = new JButton("单人模式");
    JButton establish_jButton = new JButton("创建房间");
    JButton join_jbutton = new JButton("加入房间");
    JButton rank_jButton = new JButton("查看排行榜");
    JButton set_jButton = new JButton("设置");
    JButton logoutButton = new JButton("注销");
    JButton modifyButton = new JButton("改密");

    /**
     * UI的设计
     */
    public Homepage() {
        setLayout(null);
        establish_jButton.setBounds(525, 500, 100, 30);
        join_jbutton.setBounds(525, 540, 100, 30);
        single_jButton.setBounds(525, 580, 100, 30);
        rank_jButton.setBounds(525, 620, 100, 30);
        set_jButton.setBounds(525, 660, 100, 30);
        modifyButton.setBounds(525, 700, 100, 30);
        logoutButton.setBounds(525, 740, 100, 30);

        add(establish_jButton);
        add(join_jbutton);
        add(single_jButton);
        add(rank_jButton);
        add(set_jButton);
        add(logoutButton);
        add(modifyButton);
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
