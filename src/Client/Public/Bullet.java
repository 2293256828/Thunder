package Client.Public;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹飞行类
 */
public class Bullet extends Fly {
    public Bullet(int x, int y, BufferedImage image) {
        this.image = image;
        this.x = x + image.getWidth() / 2;
        this.y = y + image.getHeight() / 2;
    }

    /**
     * 友军子弹移动及其绘制方法
     * @param g
     */
    public void mybulletpaint(Graphics g) {
        y -= image.getHeight() / 2;
        g.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
    }
    /**
     * 敌军子弹移动及其绘制方法
     * @param g
     */
    public void enemybulletpaint(Graphics g) {
        y += image.getHeight() / 30;
        g.drawImage(image, x, y, image.getWidth() / 2, image.getHeight() / 2, null);
    }
    /**
     * BOSS子弹绘制方法
     * @param g
     */
    public void bossbulletpaint(Graphics g) {
        g.drawImage(image, x, y, image.getWidth() / 2, image.getHeight() / 2, null);
    }

    /**
     * 出界判定
     * @return
     */
    public boolean outofbound() {
        return y > 1000 || y < 0 || x > 1200 || x < -10;
    }
}
