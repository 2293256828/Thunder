package Client.Public;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 图像工具类，使用ImageIO和BufferedImage初始化了所有的图片资源，BufferedImage是Image的实现类能够将图片加载到内存中，进而获得图片的属性。
 */
public class Imageutil {
    public static BufferedImage background;
    public static BufferedImage background2;
    public static BufferedImage mybulletimage;
    public static BufferedImage image1;
    public static BufferedImage image2;
    public static BufferedImage image3;
    public static BufferedImage image4;
    public static BufferedImage image5;
    public static BufferedImage image6;
    public static BufferedImage image7;
    public static BufferedImage image8;
    public static BufferedImage image9;
    public static BufferedImage image10;
    public static BufferedImage image11;
    public static BufferedImage image12;
    public static BufferedImage image13;
    public static BufferedImage enemybulletimage;
    public static BufferedImage bossbulletimage;
    public static BufferedImage hellbossbullet;
    static {
        try {
            mybulletimage = ImageIO.read(new FileInputStream("D:\\mybullet.png"));
            enemybulletimage=ImageIO.read(new FileInputStream("D:\\enemybullet.png"));
            bossbulletimage=ImageIO.read(new FileInputStream("D:\\bossbullet.png"));
            hellbossbullet=ImageIO.read(new FileInputStream("D:\\enemybullet2.png"));
            image1 = ImageIO.read(new FileInputStream("D:\\plane1.png"));
            image2 = ImageIO.read(new FileInputStream("D:\\plane2.png"));
            image3 = ImageIO.read(new FileInputStream("D:\\plane3.png"));
            image4 = ImageIO.read(new FileInputStream("D:\\plane4.png"));
            image5 = ImageIO.read(new FileInputStream("D:\\plane5.png"));
            image6 = ImageIO.read(new FileInputStream("D:\\myplane.png"));
            image7 = ImageIO.read(new FileInputStream("D:\\gameover.jpg"));
            background = ImageIO.read(new FileInputStream("D:\\background.jpg"));
            background2 = ImageIO.read(new FileInputStream("D:\\background.jpg"));
            image8=ImageIO.read(new FileInputStream("D:\\boom1.png"));
            image9=ImageIO.read(new FileInputStream("D:\\boom2.png"));
            image10=ImageIO.read(new FileInputStream("D:\\boom3.png"));
            image11=ImageIO.read(new FileInputStream("D:\\雷电.jpg"));
            image12=ImageIO.read(new FileInputStream("D:\\boss.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

