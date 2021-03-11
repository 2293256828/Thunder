package Client.HellDifficulty;
import java.awt.*;
import java.util.LinkedList;

import Client.GeneralDifficulty.CommonBoss;
import Client.Public.*;

/**
 * HellBoss类继承CommonBoss，实现代码复用
 */
public class HellBoss extends CommonBoss {
    /**
     * 初始化
     */
    public HellBoss() {
        super();
        super.life=30;
    }

    /**
     * 相比普通BOSS而言，多了一个参数另一种子弹集合hellbossbullet，因二者移动方式不同不能放在同一个集合
     * @param g
     * @param bossbullets
     * @param hellbossbullet
     */
    public void paint(Graphics g, LinkedList<Bullet> bossbullets, LinkedList<Bullet> hellbossbullet) {
        move(bossbullets, hellbossbullet);
        g.drawImage(image, x, y, null);
        for (Bullet bullet : bossbullets) {
            bullet.bossbulletpaint(g);
        }
    }

    /**
     * 添加20发子弹
     * @param bossbullets
     */
    public void hellshoot(LinkedList<Bullet> bossbullets) {
        for (int i = 0; i < 20; i++) bossbullets.add(new Bullet(x, y, Imageutil.hellbossbullet));
    }

    /**
     * 移动BOSS以及添加子弹
     * @param bossbullets
     * @param hellbullets
     */
    public void move(LinkedList<Bullet> bossbullets, LinkedList<Bullet> hellbullets) {
        super.move(bossbullets);
        if (j % 320 == 0) {
            hellshoot(hellbullets);
        }
    }
}