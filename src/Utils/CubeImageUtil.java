package Utils;

import UI.CubeCluster;
import UI.CubeFrame;
import UI.FramePostAction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CubeImageUtil {

    private static final int ALPHA_VALUE_50_PERCENT_TRANSLUCENT = 128;

    public static BufferedImage loadImage(String filePath) {
        BufferedImage img = null;
        try {
            //InputStream stream = ImageLoader.class.getClassLoader().getResourceAsStream("/resources/" + );
            img = ImageIO.read(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("The file " + filePath + " could not be opened.");
            System.out.println("The required resource " + filePath + " could not be opened. Terminating program.");
        } catch (IOException e) {
            System.out.println("The required resource " + filePath + " could not be opened. Terminating program.");
            System.out.println(e.getMessage());
        }
        return img;
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D g2d = bImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return bImage;
    }

    public static BufferedImage getScaledBufferedImage(BufferedImage bImage, double percOfSize) {
        int[] scaledDimensions = getScaledDimensions(percOfSize, bImage.getWidth(), bImage.getHeight());
        return toBufferedImage(bImage.getScaledInstance(scaledDimensions[0], scaledDimensions[1], Image.SCALE_SMOOTH));
    }

    public static int[] getScaledDimensions(double percOfSize, int width, int height) {
        return new int[] {(int)(width*percOfSize), (int)(height*percOfSize)};
    }

    private static int[] getImageRaster(BufferedImage bi) {
        int imgWidth = bi.getWidth();
        int imgHeight = bi.getHeight();
        int[] pixels = new int[imgWidth * imgHeight];
        bi.getRGB(0, 0, imgWidth, imgHeight, pixels, 0, imgWidth);
        return pixels;
    }

    public static CubeFrame createFrame(String frameName, String skinFilePath, boolean isMaxFrame, FramePostAction framePostAction) {
        BufferedImage cubeFrameSkin = loadImage(skinFilePath);
        int imgWidth = cubeFrameSkin.getWidth();
        int imgHeight = cubeFrameSkin.getHeight();

        CubeFrame cubeFrame = new CubeFrame(cubeFrameSkin, frameName);

        int[] pixels = getImageRaster(cubeFrameSkin);

        for(int x = 0; x < imgWidth; x++) {
            for(int y = 0; y < imgHeight; y++) {
                int index = x + imgWidth*y;
                int pixel = pixels[index];
                int a = (pixel >> 24) & 0xFF;

                if(a == ALPHA_VALUE_50_PERCENT_TRANSLUCENT) { // 50% translucent
                    // we hit a translucent pixel

                    String hash = ""+pixel;

                    if(!cubeFrame.clusters.containsKey(hash)) {
                        cubeFrame.clusters.put(hash, new CubeCluster(x, y, hash));
                    }

                    CubeCluster cluster = cubeFrame.clusters.get(hash);

                    cluster.latchOnTo(x, y);
                }
            }
        }

        cubeFrame.isMaxFrame = isMaxFrame;
        cubeFrame.postAction = framePostAction;

        return cubeFrame;
    }

}
