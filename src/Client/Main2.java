package Client;

import Client.Public.Client;



/**
 * 入口2
 */
public class Main2 {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            new Client();
        }  catch (Exception e) {
            e.printStackTrace();
        } //try catch块设置了Swing皮肤

    }
}