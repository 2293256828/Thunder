package Client.HellDifficulty;

import Client.GeneralDifficulty.CommonEnemyplane;
import Client.Public.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class HellEnemyplane extends CommonEnemyplane {
    /**
     * 初始化
     *
     * @param m
     * @param image
     * @param score
     */
    public HellEnemyplane(int m, BufferedImage image, int score) {
        super(m, image, score);
    }

    /**
     * 敌机移动，能够左右摇摆，每移动20次向集合中添加一发子弹，然后绘画该敌机及发射的子弹
     *
     * @param g
     * @param bullets
     */
    @Override
    public void paint(Graphics g, LinkedList<Bullet> bullets) {
        y += 4;
        if (x % 2 == 0) x = x + y / 100;
        else x = x - y / 100;
        count++;
        if (count % 20 == 0) {
            bullets.add(new Bullet(x, y, Imageutil.enemybulletimage));
        }
        g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
        draw_bullet(g, bullets);
    }
}
