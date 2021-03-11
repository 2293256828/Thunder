package Client.Public;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

/**
 * 爆炸类，动态爆炸图片使用一个图片数组绘制，爆炸音效通过线程绘制
 */
public class Boom {
    int x;
    int y;
    public int count = 0;
    BufferedImage[] images = {Imageutil.image8, Imageutil.image8, Imageutil.image9, Imageutil.image9, Imageutil.image10, Imageutil.image10, Imageutil.image10};

    /**
     * 构造方法
     *
     * @param x            横坐标
     * @param y            位置纵坐标
     * @param sound_effect 有无爆炸音效
     */
    public Boom(int x, int y, boolean sound_effect) {
        this.x = x;
        this.y = y;
        if (sound_effect) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Music.boommusic();
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 绘画图像
     *
     * @param g
     */
    public void paint(Graphics g) {
        g.drawImage(images[count], x, y, null);
        count++;
    }
}
