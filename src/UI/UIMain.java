package UI;

import Core.Cube;
import Core.CubeBuilder;
import Core.SolverProcess;
import Core.TwoByTwoCube;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static UI.RotationControlButton.*;
import static Utils.CubeImageUtil.createFrame;

public class UIMain extends JFrame implements ActionListener {

    private Canvas canvas;
    private ControlsPanel controlsPanel;
    private RotationButtonsPanel rotationButtonsPanel; // rotation buttons panel -- placed within the control

    private Queue<int[]> events = new LinkedList();

    Timer timer = new Timer(75, this);

    private Cube cube2x2 = null;
    private Cube cubeCopy = null;

    public UIMain() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setSize(640, 480);
        this.setSize(640, 820);
        this.setResizable(false);
        this.setLocation(300, 300);
        this.setTitle("GACubeSolver - UI");

        this.setLayout(new BorderLayout());

        // only allow a 2x2 for testing right now
        cube2x2 = CubeBuilder.makeCube(Cube.CubeType.TWO_BY_TWO);
        cubeCopy = CubeBuilder.makeCube(Cube.CubeType.TWO_BY_TWO);

        canvas = new Canvas();
        this.add(canvas, BorderLayout.CENTER);

        controlsPanel = new ControlsPanel();
        this.add(controlsPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    // Event types: refactor later
    private final int ROTATE_TOP = 0;
    private final int ROTATE_TOP_CC = 1;
    private final int ROTATE_BOTTOM = 2;
    private final int ROTATE_BOTTOM_CC = 3;
    private final int ROTATE_LEFT = 4;
    private final int ROTATE_LEFT_CC = 5;
    private final int ROTATE_RIGHT = 6;
    private final int ROTATE_RIGHT_CC = 7;
    private final int ROTATE_BACK = 8;
    private final int ROTATE_BACK_CC = 9;
    private final int ROTATE_FRONT = 10;
    private final int ROTATE_FRONT_CC = 11;

    private final int EVENT_WAIT_FOR_SOLVE = 100;

    private Map<Integer, List<CubeFrame>> framesByAction = new HashMap();

    public void addFrameForAction(CubeFrame frame, int actionType) {
        List<CubeFrame> actionFrames = null;
        if(!framesByAction.containsKey(actionType)) {
            framesByAction.put(actionType, new ArrayList<CubeFrame>());
        }
        actionFrames = framesByAction.get(actionType);
        actionFrames.add(frame);
    }

    public void queueEventsForAction(Integer actionType) {

        if(actionType == EVENT_WAIT_FOR_SOLVE) {
            events.add(new int[] {EVENT_WAIT_FOR_SOLVE, 0});
            return;
        }

        // otherwise, we're doing rotation animations

        int numFramesForAction = framesByAction.get(actionType).size();
        for(int frameIndex = 0; frameIndex < numFramesForAction; frameIndex++) {
            int[] event = {actionType, frameIndex};
            events.add(event);
        }
    }

    public void runQueuedEvents() {
        // disable all controls when there's an animation running so the user can't create any new events
        controlsPanel.disableAllControls();
        timer.start();
    }

    private void doSolverPostAction(Integer solverAction) {
        System.out.println("UIMain doSolverPostAction(), says: " + "SolverProcess has completed.");
        SolverProcess.getInstance().getLastSolveSolution().stream().forEach(this::queueEventsForAction);
        runQueuedEvents();
        //controlsPanel.enableAllControls();
    }

    private void doPostAction(Integer solverAction) {
        if(solverAction == -1) {
            controlsPanel.enableAllControls();
            canvas.updateStatus("");
            return;
        }
        // if we got here, then the last 'EVENT' that ran was from the solver process
        canvas.updateStatus("Solving");
        doSolverPostAction(solverAction);
    }

    // this is the action listener for the UIMain JFrame
    // only allow for the timer
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() != this.timer) return;

        SolverProcess solverProcess = SolverProcess.getInstance();

        int[] currentEvent = events.poll();

        // Event queue empty - stop timer and re-enable all controls
        if(currentEvent == null) {
            timer.stop();
            doPostAction(-1);
            return;
        }

        int actionType = currentEvent[0];

        if(actionType == EVENT_WAIT_FOR_SOLVE) {
            if(solverProcess.isRunning()) {
                /*Solver thread is still running,
                    so we'll insert the event back on to the queue until the thread is finished running
                    so we keep waiting until that thread is finished doing its work
                */
                events.add(currentEvent);
                //System.out.println("UIMain says: " + "Still waiting for SolverProcess to complete.");
            }
            else {
                // we're done - perform post solver process actions
                doPostAction(1);
            }
            return;
        }

        int frameIndex = currentEvent[1];
        canvas.currentFrame = framesByAction.get(actionType).get(frameIndex);
        canvas.paintImmediately(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.currentFrame.postAction(cube2x2);
        if(frameIndex == framesByAction.get(actionType).size()-1) {
            //cube2x2.rotateRight(1);
            canvas.currentFrame = framesByAction.get(actionType).get(0); // revert back to start frame
        }
    }

    class RotationButtonsPanel extends JPanel {

        // all buttons on this panel
        List<JButton> buttons = new ArrayList();

        public void setEnabled(boolean isEnabled) {
            for(JButton button : buttons) {
                button.setEnabled(isEnabled);
            }
        }

        private JButton track(JButton button) {
            buttons.add(button);
            return button;
        }

        public RotationButtonsPanel(ControlsPanel parent) {
            //setBorder(BorderFactory.createLineBorder(Color.BLACK));

            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints constraints = new GridBagConstraints();

            setLayout(layout);

            final class ButtonBuilderHelper {

                int MODE_COUNTER = 0;
                final int MODE_STACKED = MODE_COUNTER++;
                final int MODE_SIDE_BY_SIDE = MODE_COUNTER++;
                final int MODE_NONE = MODE_COUNTER++;

                public final JPanel makeButtonPair(String header, int styleMode, Object[] props) {
                    JPanel pairContainer = new JPanel(new BorderLayout());

                    //pairContainer.setBorder(BorderFactory.createLineBorder(Color.BLUE));

                    JLabel headerLbl = new JLabel(header);
                    headerLbl.setHorizontalAlignment(SwingConstants.CENTER);
                    pairContainer.add(headerLbl, BorderLayout.NORTH);

                    JPanel pair = new JPanel();

                    if(styleMode == MODE_STACKED) {
                        pair.setLayout(new GridLayout(2, 1));
                    }
                    else if(styleMode == MODE_SIDE_BY_SIDE) {
                        pair.setLayout(new GridLayout(1, 2));
                    }

                    if(props == null || props.length == 0) {
                        pair.add(track(new JButton("1")));
                        pair.add(track(new JButton("2")));
                    }
                    else {
                        int actionType0 = (int) props[0];
                        BufferedImage iconType0 = (BufferedImage) props[1];
                        RotationControlButton a = new RotationControlButton(iconType0, actionType0,"");
                        a.addActionListener(parent);
                        pair.add(track(a));

                        int actionType1 = (int) props[2];
                        BufferedImage iconType1 = (BufferedImage) props[3];
                        RotationControlButton b = new RotationControlButton(iconType1, actionType1,"");
                        b.addActionListener(parent);
                        pair.add(track(b));
                    }

                    pairContainer.add(pair, BorderLayout.CENTER);

                    return pairContainer;
                }

                public final JComponent makeNewButtonPair(String header, boolean isBlank, int styleMode, Object[] props) {
                    JComponent button;
                    if(isBlank) {
                        button = new JPanel();
                    }
                    else {
                        button = makeButtonPair(header, styleMode, props);
                    }
                    layout.setConstraints(button, constraints);
                    return button;
                }
            }

            ButtonBuilderHelper helper = new ButtonBuilderHelper();

            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            add(helper.makeNewButtonPair("", true, helper.MODE_NONE, null));
            constraints.gridwidth = 2;

            Object[] props = new Object[] {ROTATE_TOP_CC, TOP_BTM_ROT0, ROTATE_TOP, TOP_BTM_ROT1};
            add(helper.makeNewButtonPair("Top", false, helper.MODE_SIDE_BY_SIDE, props));

            constraints.gridwidth = GridBagConstraints.REMAINDER;
            add(helper.makeNewButtonPair("", true, helper.MODE_NONE, null));

            constraints.gridwidth = 1;

            props = new Object[] {ROTATE_LEFT_CC, LEFT_RIGHT_ROT0, ROTATE_LEFT, LEFT_RIGHT_ROT1};
            add(helper.makeNewButtonPair("Left", false, helper.MODE_SIDE_BY_SIDE, props));

            props = new Object[] {ROTATE_BACK_CC, BACK_FRONT_ROT0, ROTATE_BACK, BACK_FRONT_ROT1};
            add(helper.makeNewButtonPair("Back", false, helper.MODE_SIDE_BY_SIDE, props));

            props = new Object[] {ROTATE_FRONT, BACK_FRONT_ROT0, ROTATE_FRONT_CC, BACK_FRONT_ROT1};
            add(helper.makeNewButtonPair("Front", false, helper.MODE_SIDE_BY_SIDE, props));

            constraints.gridwidth = GridBagConstraints.REMAINDER;

            props = new Object[] {ROTATE_RIGHT, LEFT_RIGHT_ROT0, ROTATE_RIGHT_CC, LEFT_RIGHT_ROT1};
            add(helper.makeNewButtonPair("Right", false, helper.MODE_SIDE_BY_SIDE, props));

            constraints.gridwidth = 1;
            add(helper.makeNewButtonPair("", true, helper.MODE_NONE, null));
            constraints.gridwidth = 2;

            props = new Object[] {ROTATE_BOTTOM, TOP_BTM_ROT0, ROTATE_BOTTOM_CC, TOP_BTM_ROT1};
            add(helper.makeNewButtonPair("Bottom", false, helper.MODE_SIDE_BY_SIDE, props));

            constraints.gridwidth = GridBagConstraints.REMAINDER;
            add(helper.makeNewButtonPair("", true, helper.MODE_NONE, null));
        }
    }

    class ControlsPanel extends JPanel implements ActionListener {
        private JButton scramble = null;

        // solve button
        private JButton solve = null;

        private JLabel filler = null;

        public ControlsPanel() {
            setLayout(new FlowLayout());
            setMinimumSize(new Dimension(640, 340));
            setPreferredSize(new Dimension(640, 340));
            add(scramble = new JButton("Scramble"));

            // Rotation Buttons
            rotationButtonsPanel = new RotationButtonsPanel(this);
            add(rotationButtonsPanel);

            // Won't need this filler padding when we make the layout more sophisticated
            filler = new JLabel("");
            filler.setPreferredSize(new Dimension(getWidth(), 10));
            add(filler);

            add(solve = new JButton("Solve!"));

            for(Component component : getComponents()) {
                if(component instanceof JButton) {
                    ((JButton) component).addActionListener(this);
                }
            }
        }

        private void setAllButtonsEnabledState(boolean isEnabled) {
            for(Component component : getComponents()) {
                if(component instanceof JButton) {
                    component.setEnabled(isEnabled);
                }
            }
            rotationButtonsPanel.setEnabled(isEnabled);
        }

        public void disableAllControls() {
            setAllButtonsEnabledState(false);
        }

        public void enableAllControls() {
            setAllButtonsEnabledState(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            if(source == scramble) {
                //Arrays.asList(1,5,1,3,6,1,4,5,9,8,10,8,5,1,2,7,2,5,1).stream().forEach(UIMain.this::queueEventsForAction);
                Arrays.asList(5,10,5,5,5,2,2,0,0,9,4,4,2,11,0,5,10,8).stream().forEach(UIMain.this::queueEventsForAction);
                //Arrays.asList(2,4).stream().forEach(UIMain.this::queueEventsForAction);
                //Arrays.asList(5,9).stream().forEach(UIMain.this::queueEventsForAction);
                canvas.updateStatus("Scrambling");
                runQueuedEvents();
            }
            else if(source == solve) {
                queueEventsForAction(EVENT_WAIT_FOR_SOLVE);

                /* copy state of cube so we don't interfere with it and run into any race conditions on
                 main ui thread */
                cube2x2.copyTo(cubeCopy);

                canvas.updateStatus("Thinking...");

                SolverProcess.getInstance().runSolver(cubeCopy);

                runQueuedEvents();
            }
            else if(source instanceof RotationControlButton) {
                RotationControlButton rotationButton = (RotationControlButton) source;
                queueEventsForAction(rotationButton.getActionType());
                runQueuedEvents();
            }
        }
    }

    class Canvas extends JPanel {

        private CubeFrame currentFrame = null;

        // will be displayed along the bottom center of the canvas and tell the user if something is currently
        // happening (i.e: 'Solving', 'Solved', 'Scrambling', etc)
        private final Font STATUS_FONT = new Font("Arial", Font.BOLD, 32);
        private final int STATUS_Y_POS = 400; // hard coded for now, make dynamic later
        private final Color STATUS_COLOR = Color.BLACK;
        private String status = "";

        public Canvas() {
            loadAndInitCubeFrames();

            currentFrame = framesByAction.get(0).get(0);

            this.setPreferredSize(new Dimension(UIMain.this.getWidth(),
                    UIMain.this.getHeight()));
        }

        private int getStringWidth(Graphics2D g2d, String s, Font font) {
            FontMetrics fm = g2d.getFontMetrics(font);
            return fm.stringWidth(s);
        }

        private void updateStatus(String currentStatus) {
            this.status = currentStatus;
            repaint();
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g2d);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            if(currentFrame != null) {
                currentFrame.paintFrame(g2d, cube2x2);
            }

            if(status != null && !status.isBlank()) {
                System.out.println("Status: " + status);
                g2d.setFont(STATUS_FONT);
                double statusWidth = getStringWidth(g2d, status, STATUS_FONT);
                int statusXPos = (int)(((double)getWidth())/2 - statusWidth/2);
                g2d.setColor(STATUS_COLOR);
                g2d.drawString(status, statusXPos, STATUS_Y_POS);
            }
        }

        private void loadAndInitCubeFrames() {
            // right
            addFrameForAction(createFrame("rightRot0", "./res/with_colors/right/cube_forRightSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_RIGHT);
            addFrameForAction(createFrame("rightRot1", "./res/with_colors/right/cube_forRightSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_RIGHT);
            addFrameForAction(createFrame("rightRot2", "./res/with_colors/right/cube_forRightSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_RIGHT);
            addFrameForAction(createFrame("rightRot3", "./res/with_colors/right/cube_forRightSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated right");
                        ((TwoByTwoCube)params).rotateRight(1);
                    })
                    .buildInfoForAllPieces(), ROTATE_RIGHT);

            // right counter-clockwise
            addFrameForAction(createFrame("rightCCRot0", "./res/with_colors/right_cc/cube_forRightSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_RIGHT_CC);
            addFrameForAction(createFrame("rightCCRot1", "./res/with_colors/right_cc/cube_forRightSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_RIGHT_CC);
            addFrameForAction(createFrame("rightCCRot2", "./res/with_colors/right_cc/cube_forRightSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_RIGHT_CC);
            addFrameForAction(createFrame("rightCCRot3", "./res/with_colors/right_cc/cube_forRightSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated right counter-clockwise");
                        ((TwoByTwoCube)params).rotateRightCC(1);
                    })
                    .buildInfoForAllPieces(), ROTATE_RIGHT_CC);

            // top
            addFrameForAction(createFrame("topRot0", "./res/with_colors/top/cube_forTopSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_TOP);
            addFrameForAction(createFrame("topRot1", "./res/with_colors/top/cube_forTopSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_TOP);
            addFrameForAction(createFrame("topRot2", "./res/with_colors/top/cube_forTopSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_TOP);
            addFrameForAction(createFrame("topRot3", "./res/with_colors/top/cube_forTopSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated top clockwise");
                        ((TwoByTwoCube)params).rotateTop(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_TOP);

            // top counter-clockwise
            addFrameForAction(createFrame("topCCRot0", "./res/with_colors/top_cc/cube_forTopSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_TOP_CC);
            addFrameForAction(createFrame("topCCRot1", "./res/with_colors/top_cc/cube_forTopSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_TOP_CC);
            addFrameForAction(createFrame("topCCRot2", "./res/with_colors/top_cc/cube_forTopSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_TOP_CC);
            addFrameForAction(createFrame("topCCRot3", "./res/with_colors/top_cc/cube_forTopSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated top clockwise");
                        ((TwoByTwoCube)params).rotateTopCC(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_TOP_CC);

            // bottom counter-clockwise
            addFrameForAction(createFrame("bottomCCRot0", "./res/with_colors/bottom_cc/cube_forBottomSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_BOTTOM_CC);
            addFrameForAction(createFrame("bottomCCRot1", "./res/with_colors/bottom_cc/cube_forBottomSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_BOTTOM_CC);
            addFrameForAction(createFrame("bottomCCRot2", "./res/with_colors/bottom_cc/cube_forBottomSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_BOTTOM_CC);
            addFrameForAction(createFrame("bottomCCRot3", "./res/with_colors/bottom_cc/cube_forBottomSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated bottom counter - clockwise");
                        ((TwoByTwoCube)params).rotateBottomCC(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_BOTTOM_CC);

            // bottom
            addFrameForAction(createFrame("bottomRot0", "./res/with_colors/bottom/cube_forBottomSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_BOTTOM);
            addFrameForAction(createFrame("bottomRot1", "./res/with_colors/bottom/cube_forBottomSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_BOTTOM);
            addFrameForAction(createFrame("bottomRot2", "./res/with_colors/bottom/cube_forBottomSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_BOTTOM);
            addFrameForAction(createFrame("bottomRot3", "./res/with_colors/bottom/cube_forBottomSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated bottom");
                        ((TwoByTwoCube)params).rotateBottom(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_BOTTOM);

            // front
            addFrameForAction(createFrame("frontRot0", "./res/with_colors/front/cube_forFrontSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_FRONT);
            addFrameForAction(createFrame("frontRot1", "./res/with_colors/front/cube_forFrontSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_FRONT);
            addFrameForAction(createFrame("frontRot2", "./res/with_colors/front/cube_forFrontSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_FRONT);
            addFrameForAction(createFrame("frontRot3", "./res/with_colors/front/cube_forFrontSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated front");
                        ((TwoByTwoCube)params).rotateFront(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_FRONT);

            // front counter-clockwise
            addFrameForAction(createFrame("frontCCRot0", "./res/with_colors/front_cc/cube_forFrontSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_FRONT_CC);
            addFrameForAction(createFrame("frontCCRot1", "./res/with_colors/front_cc/cube_forFrontSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_FRONT_CC);
            addFrameForAction(createFrame("frontCCRot2", "./res/with_colors/front_cc/cube_forFrontSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_FRONT_CC);
            addFrameForAction(createFrame("frontCCRot3", "./res/with_colors/front_cc/cube_forFrontSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated front counter - clockwise");
                        ((TwoByTwoCube)params).rotateFrontCC(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_FRONT_CC);

            // left
            addFrameForAction(createFrame("leftCCRot0", "./res/with_colors/left_cc/cube_forLeftSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_LEFT_CC);
            addFrameForAction(createFrame("leftCCRot1", "./res/with_colors/left_cc/cube_forLeftSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_LEFT_CC);
            addFrameForAction(createFrame("leftCCRot2", "./res/with_colors/left_cc/cube_forLeftSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_LEFT_CC);
            addFrameForAction(createFrame("leftCCRot3", "./res/with_colors/left_cc/cube_forLeftSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated left counter - clockwise");
                        ((TwoByTwoCube)params).rotateLeftCC(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_LEFT_CC);

            // left
            addFrameForAction(createFrame("leftRot0", "./res/with_colors/left/cube_forLeftSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_LEFT);
            addFrameForAction(createFrame("leftRot1", "./res/with_colors/left/cube_forLeftSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_LEFT);
            addFrameForAction(createFrame("leftRot2", "./res/with_colors/left/cube_forLeftSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_LEFT);
            addFrameForAction(createFrame("leftRot3", "./res/with_colors/left/cube_forLeftSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated left");
                        ((TwoByTwoCube)params).rotateLeft(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_LEFT);

            // back
            addFrameForAction(createFrame("backRot0", "./res/with_colors/back/cube_forBackSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_BACK);
            addFrameForAction(createFrame("backRot1", "./res/with_colors/back/cube_forBackSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_BACK);
            addFrameForAction(createFrame("backRot2", "./res/with_colors/back/cube_forBackSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_BACK);
            addFrameForAction(createFrame("backRot3", "./res/with_colors/back/cube_forBackSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated back");
                        ((TwoByTwoCube)params).rotateBack(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_BACK);

            // back counter-clockwise
            addFrameForAction(createFrame("backCCRot0", "./res/with_colors/back_cc/cube_forBackSideRotation0.png", false, null).buildInfoForAllPieces(), ROTATE_BACK_CC);
            addFrameForAction(createFrame("backCCRot1", "./res/with_colors/back_cc/cube_forBackSideRotation1.png", false, null).buildInfoForAllPieces(), ROTATE_BACK_CC);
            addFrameForAction(createFrame("backCCRot2", "./res/with_colors/back_cc/cube_forBackSideRotation2.png", false, null).buildInfoForAllPieces(), ROTATE_BACK_CC);
            addFrameForAction(createFrame("backCCRot3", "./res/with_colors/back_cc/cube_forBackSideRotation3.png", true,
                    (params) -> {
                        System.out.println("*****rotated back counter - clockwise");
                        ((TwoByTwoCube)params).rotateBackCC(1);
                    }
            ).buildInfoForAllPieces(), ROTATE_BACK_CC);
        }

    }

    public static void main(String[] args) {
        RotationControlButton.loadIcons();

        new UIMain();
    }

}
