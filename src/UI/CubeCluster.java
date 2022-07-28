package UI;

import java.util.HashSet;

public class CubeCluster {

    public static int count = 0;
    public int id;

    private String hash = "";

    private int xPos;
    private int yPos;

    public long avgColor = 0;

    HashSet<String> members = new HashSet();

    public CubeCluster(int x, int y, String hash) {
        id = count++;
        this.xPos = x;
        this.yPos = y;
        this.hash = hash;
    }

    public int getCount() {
        return members.size();
    }

    public String getHash() {
        return this.hash;
    }

    private String xyToKey(int x, int y) {
        return x+","+y;
    }

    public void latchOnTo(int x, int y) {
        members.add(xyToKey(x, y));
        // update hash (todo: optimize later):
        //this.hash = calcHash();
    }

    public String toString() {
        long a = (this.avgColor >> 24) & 0xFF;
        long r = (this.avgColor >> 16) & 0xFF;
        long g = (this.avgColor >> 8) & 0xFF;
        long b = (this.avgColor >> 0) & 0xFF;
        return "/*x start: " + this.xPos + ", y start: " + this.yPos + " hash: " + this.hash + " color id:*/ " + "new Color(" + r + "," + g + "," + b + "," + a + ");";
    }
}
