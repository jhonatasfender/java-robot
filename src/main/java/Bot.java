import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Bot {
    @SneakyThrows
    public static void main(String[] args) {

        File file = new File("./tabuada.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        Robot robot = new Robot();

        String readLine;
        while ((readLine = br.readLine()) != null) {
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
}

