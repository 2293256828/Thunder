package Client.HellDifficulty;

import Client.GeneralDifficulty.CommonPlayui;
import Client.Public.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

/**
 * 继承CommonPlayui，实现Runnable接口
 */
public class HellPlayui extends CommonPlayui implements Runnable {
    private ArrayList<HellEnemyplane> enemyplanes = new ArrayList<>();
    LinkedList<Bullet> hellbossbullet = new LinkedList<>();
    HellBoss boss;

    /**
     * 构造器，与父类相同
     *
     * @param rand
     * @param sound_effect
     */
    public HellPlayui(int rand, boolean sound_effect) {
        super(rand, sound_effect);

    }

    /**
     * 比起CommonBoss，多了一个子弹集合，因此需要重写判定方法，多判定一个集合
     *
     * @Override public void if_myplane_hitby_bullet() {
     * super.if_myplane_hitby_bullet();
     * Iterator it4 = hellbossbullet.iterator();
     * while (it4.hasNext()) {
     * Bullet bullet = (Bullet) it4.next();
     * if (myplane1.hitby(bullet)) {
     * it4.remove();
     * if (myplane1.life > 0) {
     * myplane1.life--;
     * }
     * continue;
     * }
     * if (myplane2.hitby(bullet)) {
     * it4.remove();
     * if (myplane2.life > 0) {
     * myplane2.life--;
     * }
     * }
     * }
     * }
     * <p>
     * /**
     * 比起CommonBoss，难度提高，因此需要重写绘画方法，迭代器类型改为HellEnemyplane
     */
    @Override
    public void draw_enemyplane(Graphics g, boolean sound_effect) {
        if (enemyplanes == null) {
            return;
        }
        Iterator<HellEnemyplane> it = enemyplanes.iterator();
        while (it.hasNext()) {
            HellEnemyplane enemyplane = it.next();
            if (enemyplane.outofbound()) {
                it.remove();
            } else if (enemyplane.shootby(myplane1)) {
                score1 += enemyplane.getScore();
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effect));
                it.remove();
            } else if (enemyplane.shootby(myplane2)) {
                score2 += enemyplane.getScore();
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effect));
                it.remove();
            } else if (enemyplane.hitby(myplane1)) {
                if (myplane1.life > 0) myplane1.life--;
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effect));
                it.remove();
            } else if (enemyplane.hitby(myplane2)) {
                if (myplane2.life > 0) myplane2.life--;
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effect));
                it.remove();
            }
            enemyplane.paint(g, bullets);
        }
    }

    /**
     * 比起CommonBoss，多了一个子弹集合，因此需要重写绘画方法，多移动并绘画一个集合
     *
     * @param g
     * @param sound_effect
     */
    @Override
    public void draw_boss(Graphics g, boolean sound_effect) {
        bossbulletrightmove();
        bossbulletleftmove();
        hellbossbulletmove();
        for (Bullet bullet : bossbulletsright) {
            bullet.bossbulletpaint(g);
        }
        for (Bullet bullet : bossbulletsleft) {
            bullet.bossbulletpaint(g);
        }
        for (Bullet bullet : hellbossbullet) {
            bullet.bossbulletpaint(g);
        }
        if (boss == null) return;
        if (boss.orient) {
            boss.paint(g, bossbulletsright, hellbossbullet);
        } else {
            boss.paint(g, bossbulletsleft, hellbossbullet);
        }
        if (boss.killedby(myplane1)) {
            score1 += 1000;
            booms.add(new Boom(boss.x, boss.y, sound_effect));
            boss = null;
            return;
        }
        if (boss.killedby(myplane2)) {
            score2 += 1000;
            booms.add(new Boom(boss.x, boss.y, sound_effect));
            boss = null;
            return;
        }
    }

    /**
     * 重写run方法，boss类改为HellBoss
     */
    @Override
    public void run() {

        int i = 0;
        while (true) try {

            if (i % 30 == 0) {
                {
                    if (i % 60 == 0) {
                        enemyplanes.add(new HellEnemyplane(array[i], Imageutil.image2, 20));
                    } else if (i % 90 == 0) enemyplanes.add(new HellEnemyplane(array[i], Imageutil.image4, 30));
                    else if (i % 150 == 0) enemyplanes.add(new HellEnemyplane(array[i], Imageutil.image1, 50));
                }
            }
            if (i % 7200 == 0 && boss == null) {
                boss = new HellBoss();
            }
            i++;
            if (i == 10241) {
                i = 0;
            }
            if_myplane_hitby_bullet();
            repaint();
            if (this.i == 3) {
                this.i++;
                break;
            }
            sleep(18);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ConcurrentModificationException ee) {

        }
    }

    /**
     * 地狱模式下Boss特有子弹的移动
     */
    public void hellbossbulletmove() {
        double xspeed = 3, yspeed = 0;
        if (hellbossbullet == null) return;
        Iterator<Bullet> it = hellbossbullet.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            bullet.x -= xspeed;
            bullet.y += yspeed;
            xspeed -= 0.15;
            yspeed = Math.sqrt(9 - Math.pow(xspeed, 2));
            if (bullet.outofbound()) {
                it.remove();
            }
        }
    }
}