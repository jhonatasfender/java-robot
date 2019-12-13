package Bot;

import Recording.RecordingWhatIsBeingDone;
import KeyBoard.RunKeyboardAsItIsFile;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;

public class Bot {
    private static Robot robot;
    private static String readLine;

    @SneakyThrows
    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        BufferedReader br = RecordingWhatIsBeingDone.read();
        robot = new Robot();

        while ((readLine = br.readLine()) != null) {
            row();
        }
    }

    private static void row() throws InterruptedException {
        String line = readLine.replace(",", "");
        String[] split = line.split(" ");

        if (readLine.contains("Mouse Dragged")) {
            Integer x = Integer.valueOf(split[2]);
            Integer y = Integer.valueOf(split[3]);

            robot.mouseMove(x, y);
        }
        if (readLine.contains("Mouse Pressed")) {
            Integer x = Integer.valueOf(split[2]);
            Integer y = Integer.valueOf(split[3]);

            robot.mouseMove(x, y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }

        RunKeyboardAsItIsFile.setRobot(robot);
        RunKeyboardAsItIsFile.execute(readLine, split);


        Thread.sleep(20);
    }
}

