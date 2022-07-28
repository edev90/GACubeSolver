package UI;

import Core.Cube;
import Core.TwoByTwoCube;
import Utils.RGBEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class CubeFrame {

    private BufferedImage skin = null;
    private String frameName;
    public boolean isMaxFrame = false;

    public FramePostAction postAction;

    //public ArrayList<CubeCluster> clusters = new ArrayList();
    public HashMap<String, CubeCluster> clusters = new HashMap();

    private HashMap<String, Object[]> painters = new HashMap();

    public CubeFrame(BufferedImage skin, String frameName) {
        this.skin = skin;
        this.frameName = frameName;
    }

    public BufferedImage getSkin() {
        return this.skin;
    }

//    public void calcAndSetAllClusterHashes() {
//        System.out.println("Clusters:");
//        for(CubeCluster cluster : clusters) {
//            cluster.calcAndUpdateHash();
//            cluster.finalizeComputeAvgColor();
//            System.out.println(cluster);
//        }
//    }

    public void dumpClusterHashes() {
        for(CubeCluster cluster : clusters.values()) {
            System.out.println(cluster.getHash());
        }
    }

    public void dumpClusterHashesAsColorStrings() {
        for(CubeCluster cluster : clusters.values()) {
            System.out.println("new Color(" + cluster.getHash() + ");");
        }
    }

    public CubeFrame buildInfoForAllPieces() {
        for(String hash : this.clusters.keySet()) {
            this.addPieceInfo(hash, RGBEncoder.decode(hash));
        }
        return this;
    }

    public void addPieceInfo(String hash, Object[] pInfo) {
        painters.put(hash, pInfo);
    }

    public void paintFrame(Graphics gfxContext, Cube cube) {

        gfxContext.drawImage(skin, 0, 0, null);

        Object[] colorInfo = null;
        for(CubeCluster cluster : clusters.values()) {
            colorInfo = painters.get(cluster.getHash());
            //System.out.println("Found color info: " + colorInfo + " for hash: " + cluster.getHash());
            if(colorInfo != null) {
                int pieceYPos = (int)(Integer)colorInfo[0];
                int pieceXPos = (int)(Integer)colorInfo[1];
                int pieceZPos = (int)(Integer)colorInfo[2];
                String pieceSide = ""+colorInfo[3];

                gfxContext.setColor(cube.getPiece(pieceYPos, pieceXPos, pieceZPos).getCurrentColor(pieceSide));

                for(String coord : cluster.members) {
                    String[] coordData = coord.split(",");
                    int xPos = Integer.parseInt(coordData[0]);
                    int yPos = Integer.parseInt(coordData[1]);
                    gfxContext.fillRect(xPos, yPos, 1, 1);
                }
            }
        }
    }

    // do something after this frame has been painted - like completing the rotation of a cube's side on the cube
    // object itself
    public void postAction(Object params) {
        if(postAction != null) {
            postAction.doAction(params);
        }
    }

}
