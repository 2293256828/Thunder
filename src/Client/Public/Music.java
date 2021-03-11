package Client.Public;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 音乐类，AudioClip类需要调用Applet，而其已经过时无效,无法正常使用。后来查询找到替代类Clip及其配合使用的AudioSystem。
 * 即便如此，替代类Clip仍有缺陷，比如当一个线程在使用一个Clip时，另一个线程调用音乐播放是无效的。因此对音效采取了轮流播放，足以满足需求。
 */
public class Music {
    public static Clip backgroundmusic;
    public static Clip shootmusic;
    public static Clip shootmusic2;
    public static Clip shootmusic3;
    public static Clip boommusic;
    public static Clip boommusic2;
    public static Clip boommusic3;
    static int i = 1;
    static int j = 1;

    static {
        try {

            backgroundmusic = AudioSystem.getClip();
            shootmusic = AudioSystem.getClip();
            shootmusic2 = AudioSystem.getClip();
            shootmusic3 = AudioSystem.getClip();
            boommusic = AudioSystem.getClip();
            boommusic2 = AudioSystem.getClip();
            boommusic3 = AudioSystem.getClip();
            backgroundmusic.open(AudioSystem.getAudioInputStream(new File("D:\\background.wav")));
            shootmusic.open(AudioSystem.getAudioInputStream(new File("D:\\shoot.wav")));
            shootmusic2.open(AudioSystem.getAudioInputStream(new File("D:\\shoot.wav")));
            shootmusic3.open(AudioSystem.getAudioInputStream(new File("D:\\shoot.wav")));
            boommusic.open(AudioSystem.getAudioInputStream(new File("D:\\boom.wav")));
            boommusic2.open(AudioSystem.getAudioInputStream(new File("D:\\boom.wav")));
            boommusic3.open(AudioSystem.getAudioInputStream(new File("D:\\boom.wav")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 爆炸音效的绘制，不能用start，start播放完毕后再start时，从上次结束的地方开始播放，相当于一个Clip只能用一遍。用loop(1)代替
     */
    public static void boommusic() {
        if (i == 1) {
            i = 2;
            boommusic.loop(1);
        } else if (i == 2) {
            i = 3;
            boommusic2.loop(1);
        } else if (i == 3) {
            i = 1;
            boommusic3.loop(1);
        }
    }

    /**
     * 射击音效的绘制，不能用start，start播放完毕后再start时，从上次结束的地方开始播放，相当于一个Clip只能用一遍。用loop(1)代替
     */
    public static void shootmusic() {
        if (j == 1) {
            j = 2;
            shootmusic.loop(1);
        } else if (j == 2) {
            j = 3;
            shootmusic2.loop(1);
        } else if (j == 3) {
            j = 1;
            shootmusic3.loop(1);
        }
    }
}
