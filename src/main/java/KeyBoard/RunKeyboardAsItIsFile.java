package KeyBoard;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RunKeyboardAsItIsFile {
    public static final String VK_SPACE = "Space";
    public static final String VK_ENTER = "Enter";
    public static final String VK_SHIFT = "Shift";
    public static final String VK_BACK_SPACE = "Backspace";
    public static final String VK_PERIOD = "Period";

    private static Boolean isShift = false;

    private static Robot robot;

    public static void setRobot(Robot robot) {
        RunKeyboardAsItIsFile.robot = robot;
    }

    public static Robot getRobot() {
        return robot;
    }

    public static void execute(String readLine, String[] split) {
        if (readLine.contains("Key Pressed")) {
            if (VK_SPACE.equals(split[2])) {
                space();
            } else if (VK_ENTER.equals(split[2])) {
                enter();
            } else {
                byte[] bytes;
                if (split[2].contains(VK_SHIFT)) {
                    String change = split[2].trim().replace(VK_SHIFT + "[", "").replace("]", "");
                    bytes = change.getBytes();
                    isShift = true;
                } else {
                    bytes = split[2].getBytes();
                }

                if (isShift) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.delay(100);
                }

                if (split[2].contains(VK_BACK_SPACE)) {
                    robot.delay(100);
                    getRobot().keyPress(KeyEvent.VK_BACK_SPACE);
                    getRobot().keyRelease(KeyEvent.VK_BACK_SPACE);
                    robot.delay(100);
                } else if (split[2].contains(VK_PERIOD)) {
                    robot.delay(100);
                    getRobot().keyPress(KeyEvent.VK_PERIOD);
                    getRobot().keyRelease(KeyEvent.VK_PERIOD);
                } else if (split[2].contains(GlobalKeyListener.VC_SLASH)) {
                    robot.delay(100);
                    getRobot().keyPress(KeyEvent.VK_SLASH);
                    getRobot().keyRelease(KeyEvent.VK_SLASH);
                    robot.delay(100);
                } else if (split[2].contains(GlobalKeyListener.VC_BACK_SLASH)) {
                    robot.delay(100);
                    getRobot().keyPress(KeyEvent.VK_BACK_SLASH);
                    getRobot().keyRelease(KeyEvent.VK_BACK_SLASH);
                    robot.delay(100);
                } else {
                    for (byte b : bytes) {
                        int code = b;
                        // keycode only handles [A-Z] (which is ASCII decimal [65-90])
                        if (code > 96 && code < 123) code = code - 32;

                        getRobot().keyPress(code);
                        getRobot().keyRelease(code);
                    }
                }

                if (isShift) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay(100);
                    isShift = false;
                }
            }
        }
    }

    private static void space() {
        getRobot().keyPress(KeyEvent.VK_SPACE);
        getRobot().delay(40);
        getRobot().keyRelease(KeyEvent.VK_SPACE);
    }

    private static void enter() {
        getRobot().keyPress(KeyEvent.VK_ENTER);
        getRobot().delay(40);
        getRobot().keyRelease(KeyEvent.VK_ENTER);
    }

}
