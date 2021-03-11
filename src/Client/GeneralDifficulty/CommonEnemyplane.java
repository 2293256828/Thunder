
package Client.GeneralDifficulty;

import Client.Public.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 继承飞行类的普通敌机
 */
public class CommonEnemyplane extends Fly {
    public int count;
    private int score;

    /**
     * 初始化敌机
     * @param m 出现的水平坐标
     * @param image 图像
     * @param score 其被击杀的分数
     */
    public CommonEnemyplane(int m, BufferedImage image, int score) {
        this.image = image;
        this.score = score;
        y = 0;
        x = m;
    }

    /**
     * 敌机移动，增加子弹，绘画敌机，绘画敌机子弹集合
     *
     * @param g
     * @param bullets
     */
    public void paint(Graphics g, LinkedList<Bullet> bullets) {
        y += 4;
        count++;
        if (count % 22 == 0) {
            bullets.add(new Bullet(x, y, Imageutil.enemybulletimage));
        }
        g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
        draw_bullet(g, bullets);
    }

    /**
     * 判定出界
     * @return
     */
    public boolean outofbound() {
        return y > 1000;
    }

    /**
     * 绘画子弹List
     * @param g 画布
     * @param bullets 子弹List
     */
    public void draw_bullet(Graphics g, LinkedList<Bullet> bullets) {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (bullet.outofbound()) {
                it.remove();
            }
            bullet.enemybulletpaint(g);
        }
    }

    /**
     * 获得成绩
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * 被myplane击中判定
     * @param myplane
     * @return boolean
     */
    public boolean shootby(Myplane myplane) {
        Iterator<Bullet> it = myplane.bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (x < bullet.x && bullet.x < x + image.getWidth() && y < bullet.y && bullet.y < y + image.getHeight()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * 撞击判定
     * @param myplane
     * @return
     */
    public boolean hitby(Myplane myplane) {
        return x < myplane.x + 30 && myplane.x < x + image.getWidth() && y < myplane.y && myplane.y < y + image.getHeight() / 2;
    }
}
