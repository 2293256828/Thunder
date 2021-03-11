package Client.Public;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.Thread.sleep;

/**
 * 玩家的飞机类
 */
public class Myplane extends Fly {
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public int life = 15;

    public Myplane(BufferedImage image) {
        this.image = image;

        x = 200;
        y = 700;
    }

    /**左移
     *
     */
    public void moveleft() {
        if (x >= 20)
            x = x - image.getWidth() / 2;
    }

    /**
     * 右移
     */
    public void moveright() {
        if (x <= 1000)
            x = x + image.getWidth() / 2;
    }

    /**
     * 下移
     */
    public void movedown() {
        if (y <= 666) y += image.getHeight() / 2;
    }

    /**
     * 上移
     */
    public void moveup() {
        if (y >= 30) y -= image.getHeight() / 2;
    }

    /**
     * 绘制玩家飞机及其子弹
     * @param g
     */
    public void paint(Graphics g) {
        g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
        loop_draw_bullet(g);

    }

    /**
     * 死亡判定
     * @return
     */
    public boolean isdead() {
        return life == 0;
    }

    /**
     * 射击操作
     * @param sound_effect
     */
    public void shoot(boolean sound_effect) {
        bullets.add(new Bullet(x, y, Imageutil.mybulletimage));
       if(sound_effect) {new Thread(new Runnable() {
            @Override
            public void run() {
                Music.shootmusic();
                try {
                    sleep(50);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }).start();}
    }

    /**
     * 绘画子弹
     * @param g
     */
    public void loop_draw_bullet(Graphics g) {
        if (bullets == null) {
            return;
        }
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (bullet.outofbound()) {
                it.remove();
            }
            bullet.mybulletpaint(g);
        }
    }

    /**
     * 被击中判定
     * @param bullet
     * @return
     */
    public boolean hitby(Bullet bullet) {
        return x < bullet.x && bullet.x < x + image.getWidth() && y < bullet.y && bullet.y < y + image.getHeight();
    }
}