package Server;

public class Userdata {
    private String username;
    private int doublemaxscore;
    private int alonemaxscore;
    private String bestcompanion;

    public Userdata(String username, int doublemaxscore, int alonemaxscore, String bestcompanion) {
        this.username = username;
        this.doublemaxscore = doublemaxscore;
        this.alonemaxscore = alonemaxscore;
        this.bestcompanion = bestcompanion;
    }
    public String toString(){return username+"-"+doublemaxscore+"-"+alonemaxscore+"-"+bestcompanion;}

    public int getDoublemaxscore() {
        return doublemaxscore;
    }

    public int getAlonemaxscore() {
        return alonemaxscore;
    }

    public String getBestcompanion() {
        return bestcompanion;
    }

    public String getusername() {
        return username;
    }
}
