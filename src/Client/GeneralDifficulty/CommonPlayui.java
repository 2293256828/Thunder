package Client.GeneralDifficulty;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

import Client.Public.*;

/**
 * 普通游戏界面类，继承Jpanel，实现Runnable接口
 */
public class CommonPlayui extends JPanel implements Runnable {
    public int[] array = new int[10240];
    public ArrayList<CommonEnemyplane> enemyplanes = new ArrayList<>();
    public int mapmove = 0;
    public Myplane myplane1 = new Myplane(Imageutil.image6);
    public Myplane myplane2 = new Myplane(Imageutil.image5);
    public LinkedList<Bullet> bullets = new LinkedList<>();
    public LinkedList<Bullet> bossbulletsright = new LinkedList<>();
    public LinkedList<Bullet> bossbulletsleft = new LinkedList<>();
    public LinkedList<Boom> booms = new LinkedList<>();
    CommonBoss boss;
    public int score1 = 0;
    public int score2 = 0;
    public int i = 0;
    public int j = 0;//i和j是计数器，确保程序流程的有序进行
    public boolean sound_effect;


    /**
     * 产生游戏界面
     *
     * @param rand         根据特定随机数产生特定敌机横坐标数组，实现每次游戏地图的随机与联机的一致
     * @param sound_effect 音效设置
     */
    public CommonPlayui(int rand, boolean sound_effect) {
        setSize(1200, 900);
        this.sound_effect = sound_effect;
        toarray(rand);
    }

    /**
     * 核心方法，结束的判定，地图移动，死亡判定，画boss，画爆炸，画敌机，画分数和生命，判定我方飞机是否被击中
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        try {
            gameover(g);
            if (i >= 3) return;
            mapmove(g);
            dead_jugde(g);
            draw_boss(g, sound_effect);
            draw_booms(g);
            draw_enemyplane(g, sound_effect);
            draw_string(g);
            if_myplane_hitby_bullet();
        } catch (ConcurrentModificationException e) {
        }

    }

    /**
     * 地图移动，本质上是两张图片轮流绘制。
     *
     * @param g
     */
    public void mapmove(Graphics g) {
        g.drawImage(Imageutil.background, 0, mapmove, null);
        g.drawImage(Imageutil.background2, 0, mapmove - 800, null);
        if (mapmove >= 800) mapmove = 0;
        else mapmove += 2;
    }

    /**
     * 被子弹击中的判定，子弹分为：敌机子弹，boss右射子弹，boss左射子弹
     */
    public void if_myplane_hitby_bullet() {
        Iterator it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = (Bullet) it.next();
            if (myplane1.hitby(bullet)) {
                it.remove();
                if (myplane1.life > 0) {
                    myplane1.life--;
                }
                continue;
            }
            if (myplane2.hitby(bullet)) {
                it.remove();
                if (myplane2.life > 0) {
                    myplane2.life--;
                }
            }
        }
        Iterator it2 = bossbulletsright.iterator();
        while (it2.hasNext()) {
            Bullet bullet = (Bullet) it2.next();
            if (myplane1.hitby(bullet)) {
                it2.remove();
                if (myplane1.life > 0) {
                    myplane1.life--;
                }
                continue;
            }
            if (myplane2.hitby(bullet)) {
                it2.remove();
                if (myplane2.life > 0) {
                    myplane2.life--;
                }
            }

        }
        Iterator it3 = bossbulletsleft.iterator();
        while (it3.hasNext()) {
            Bullet bullet = (Bullet) it3.next();
            if (myplane1.hitby(bullet)) {
                it3.remove();
                if (myplane1.life > 0) {
                    myplane1.life--;
                }
                continue;
            }
            if (myplane2.hitby(bullet)) {
                it3.remove();
                if (myplane2.life > 0) {
                    myplane2.life--;
                }
            }
        }
    }

    /**
     * 死亡判定，若死亡则增加爆炸，飞机移出界面外
     *
     * @param g
     */
    public void dead_jugde(Graphics g) {
        if (i == 0 && myplane1.isdead()) {
            booms.add(new Boom(myplane1.x, myplane1.y, false));
            myplane1.x = 1300;
            myplane1.y = 1500;
            i++;
        }
        if (i == 0) {
            myplane1.paint(g);
        }
        if (i == 1) {
            myplane1.paint(g);
            i++;
        }//这里i的作用是：节省资源，仅需要在死亡时paint一次，以后就不会再执行paint了
        if (j == 0 && myplane2.isdead()) {
            booms.add(new Boom(myplane1.x, myplane1.y, false));
            myplane2.x = 1300;
            myplane2.y = 1500;
            j++;
        }//这里j的作用是：节省资源，仅需要在死亡时paint一次，以后就不会再执行paint了
        if (j == 0) {
            myplane2.paint(g);
        }
        if (j == 1) {
            myplane2.paint(g);
            j++;
        }
    }

    /**
     * 两架飞机全部死亡时，绘制游戏结束图片，绘制分数
     *
     * @param g
     * @return 游戏结束返回true
     */
    public boolean gameover(Graphics g) {
        if (i == 2 && j == 2) {
            g.drawImage(Imageutil.image7, 0, 0, null);
            g.setColor(Color.cyan);
            g.setFont(new Font(null, 0, 25));
            if (score1 != 0) {
                g.drawString("p1的分数为：" + score1, 500, 500);
            }
            if (score2 != 0) {
                g.drawString("p2的分数为：" + score2, 500, 500);
            }
            g.drawString("按Enter键继续", 525, 450);
            i++;
            return true;
        }
        return false;
    }

    /**
     * 工具方法，将i的范围缩小到0-1000，用于产生敌机横坐标
     *
     * @param i
     * @return
     */
    public int decrease_field(int i) {
        while (i > 1000) {
            i = i - 1000;
        }
        return i;
    }

    /**
     * 传入一个特定的数，生成特定的数组，通过decrease_field方法将数组中的数范围限制在0-1000；
     *
     * @param rand
     */
    public void toarray(int rand) {
        for (int i = 0; i < array.length; i++) {
            int number = Math.abs(130 * rand - 73 * (i - rand));
            array[i] = decrease_field(number);
        }
    }

    /**
     * 先进行了敌机的出界判定，击杀判定。然后绘画敌机，用synchronized同步避免在遍历时被另一个线程修改
     *
     * @param g
     * @param sound_effct
     */
    public synchronized void draw_enemyplane(Graphics g, boolean sound_effct) {
        if (enemyplanes == null) {
            return;
        }
        Iterator<CommonEnemyplane> it = enemyplanes.iterator();
        while (it.hasNext()) {
            CommonEnemyplane enemyplane = it.next();
            if (enemyplane.outofbound()) {
                it.remove();
            } else if (enemyplane.shootby(myplane1)) {
                score1 += enemyplane.getScore();
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effct));
                it.remove();
            } else if (enemyplane.shootby(myplane2)) {
                score2 += enemyplane.getScore();
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effct));
                it.remove();
            } else if (enemyplane.hitby(myplane1)) {
                if (myplane1.life > 0) myplane1.life--;
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effct));
                it.remove();
            } else if (enemyplane.hitby(myplane2)) {
                if (myplane2.life > 0) myplane2.life--;
                booms.add(new Boom(enemyplane.x, enemyplane.y, sound_effct));
                it.remove();
            }
            enemyplane.paint(g, bullets);
        }
    }

    /**
     * Boss的子弹移动，子弹绘画，
     *
     * @param g
     * @param sound_effect
     */
    public void draw_boss(Graphics g, boolean sound_effect) {
        bossbulletrightmove();
        bossbulletleftmove();
        for (Bullet bullet : bossbulletsright) {
            bullet.bossbulletpaint(g);
        }
        for (Bullet bullet : bossbulletsleft) {
            bullet.bossbulletpaint(g);
        }
        if (boss == null) return;
        if (boss.orient) {
            boss.paint(g, bossbulletsright);
        } else {
            boss.paint(g, bossbulletsleft);
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
     * 绘画爆炸效果
     *
     * @param g
     */
    public void draw_booms(Graphics g) {
        for (Boom boom : booms) {
            boom.paint(g);
            if (boom.count == 7) {
                booms.remove(boom);
            }
        }
    }

    /**
     * 画生命与战绩
     *
     * @param g
     */
    public void draw_string(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font(null, 2, 20));
        g.drawString("Score:" + score1, 20, 20);
        g.drawString("Score:" + score2, 1100, 20);
        g.drawString("Life:" + myplane1.life, 20, 40);
        g.drawString("Life:" + myplane2.life, 1100, 40);
    }

    /**
     * run方法，调用数组增加敌机，增加boss，repaint方法进行重绘
     */
    public void run() {
        int i = 0;
        while (true) try {

            if (i % 30 == 0) {
                {
                    if (i % 60 == 0) {
                        enemyplanes.add(new CommonEnemyplane(array[i], Imageutil.image2, 20));
                    } else if (i % 90 == 0) enemyplanes.add(new CommonEnemyplane(array[i], Imageutil.image4, 30));
                    else if (i % 150 == 0) enemyplanes.add(new CommonEnemyplane(array[i], Imageutil.image1, 50));
                }
            }
            if (i % 7200 == 0 && boss == null) {
                boss = new CommonBoss();
            }
            i++;
            if (i == 10241) {
                i = 0;
            }
            repaint();
            if (this.i == 3) {
                this.i++;
                return;
            }
            sleep(18);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 右射子弹的移动方式
     */
    public void bossbulletrightmove() {
        int xspeed = 3;
        int yspeed = 0;
        if (bossbulletsright == null) return;
        Iterator<Bullet> it = bossbulletsright.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            bullet.x -= xspeed;
            bullet.y += yspeed;
            xspeed -= 2;
            yspeed += 2;
            if (bullet.outofbound()) {
                it.remove();
            }
        }
    }

    /**
     * 左射子弹的移动方式
     */
    public void bossbulletleftmove() {
        int xspeed = 2;
        int yspeed = 0;
        if (bossbulletsleft == null) return;
        Iterator<Bullet> it = bossbulletsleft.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            bullet.x -= xspeed;
            bullet.y += yspeed;
            xspeed += 1;
            yspeed += 2;
            if (bullet.outofbound()) {
                it.remove();
            }
        }
    }
}