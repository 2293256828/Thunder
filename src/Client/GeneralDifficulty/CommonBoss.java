package Client.GeneralDifficulty;

import Client.Public.*;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 继承飞行类的CommonBoss类
 */
public class CommonBoss extends Fly {
    public int life = 20;//boss血量
    public int i = 0;//不同的i代表移动方式
    public int j = 0;//j逐渐增加，增加60次发射一次子弹
    public boolean orient = false;//子弹发射方向

    /**
     * 构造器，初始化boss位置和图片
     */
    public CommonBoss() {
        x = 500;
        y = 0;
        this.image = Imageutil.image12;
    }

    /**
     * 传入画板和子弹List，BOSS进行移动并向集合中添加子弹，绘画BOSS，最后对子弹list进行paint
     *
     * @param g
     * @param bossbullets
     */
    public void paint(Graphics g, LinkedList<Bullet> bossbullets) {
        move(bossbullets);
        g.drawImage(image, x, y, null);
        for (Bullet bullet : bossbullets) {
            bullet.bossbulletpaint(g);
        }
    }

    /**
     * 传入子弹List，向其中增加子弹对象
     *
     * @param bossbullets
     */
    public void shoot(LinkedList<Bullet> bossbullets) {
        for (int i = 0; i < 9; i++) bossbullets.add(new Bullet(x, y, Imageutil.bossbulletimage));

    }



    /**
     * 移动敌机，添加子弹
     * @param bossbullets 向该集合中添加子弹
     */
    public void move(LinkedList<Bullet> bossbullets) {
        if (i == 0) {
            x -= 5;
            if (x <= 10) {
                i = 1;
            }
        } else if (i == 1) {
            y += 3;
            x += 5;
            if (y >= 300) {
                i = 2;
            }
        } else if (i == 2) {
            x += 5;
            y -= 3;
            if (x > 900) {
                i = 3;
            }
        } else if (i == 3) {
            x -= 5;
            if (x < 500) {
                i = 0;
            }
        }
        j++;
        if (j % 60 == 0) {
            shoot(bossbullets);
            if (orient) {
                orient = false;
            } else {
                orient = true;
            }
        }

    }

    /**
     * 判定击中与击杀
     * @param myplane
     * @return
     */
    public boolean killedby(Myplane myplane) {
        Iterator<Bullet> it = myplane.bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (x < bullet.x && bullet.x < x + image.getWidth() && y < bullet.y && bullet.y < y + image.getHeight()) {
                it.remove();
                if (life == 1) {
                    life = 0;
                    return true;
                }
                if (life > 1) {
                    life -= 1;
                }
            }

        }
        return false;
    }

}