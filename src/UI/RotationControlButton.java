package UI;

import Utils.CubeImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RotationControlButton extends JButton {

    protected static BufferedImage TOP_BTM_ROT0 = null;
    protected static BufferedImage TOP_BTM_ROT1 = null;
    protected static BufferedImage LEFT_RIGHT_ROT0 = null;
    protected static BufferedImage LEFT_RIGHT_ROT1 = null;
    protected static BufferedImage BACK_FRONT_ROT0 = null;
    protected static BufferedImage BACK_FRONT_ROT1 = null;

    private static final double NORMAL_SIZE_PERCENT = 0.35D;
    private static final double SCALED_SIZE_PERCENT = 0.45D;

    private Icon thisIcon = null;
    private Icon pressedIcon = null;

    private int actionType;

    public RotationControlButton(BufferedImage type, int actionType, String toolTipText) {
        if(type != null) {
            thisIcon = new ImageIcon(CubeImageUtil.getScaledBufferedImage(type, NORMAL_SIZE_PERCENT));
            //pressedIcon = new ImageIcon(CubeImageUtil.getScaledBufferedImage(type, SCALED_SIZE_PERCENT));
            this.setIcon(thisIcon);
            //this.setPressedIcon(pressedIcon);
        }
        setToolTipText(toolTipText);
        this.actionType = actionType;
    }

    public RotationControlButton(BufferedImage type, int actionType) {
        this(type, actionType, "");
    }

    public int getActionType() {
        return this.actionType;
    }

    public static void loadIcons() {
        TOP_BTM_ROT0 = CubeImageUtil.loadImage("./res/rotation_buttons/top_btm_rot0.png");
        TOP_BTM_ROT1 = CubeImageUtil.loadImage("./res/rotation_buttons/top_btm_rot1.png");
        LEFT_RIGHT_ROT0 = CubeImageUtil.loadImage("./res/rotation_buttons/left_right_rot0.png");
        LEFT_RIGHT_ROT1 = CubeImageUtil.loadImage("./res/rotation_buttons/left_right_rot1.png");
        BACK_FRONT_ROT0 = LEFT_RIGHT_ROT0;
        BACK_FRONT_ROT1 = LEFT_RIGHT_ROT1;
    }

}
