package Bot;

import KeyBoard.RunKeyboardAsItIsFile;
import Recording.RecordingWhatIsBeingDone;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bot {
    public static Robot robot;
    public static String readLine;

    public static Set<String> timesThatShouldBeRecordedPoints = new HashSet<>(Arrays.asList(new String[]{
            "16:37",
            "16:40"
    }));

    @SneakyThrows
    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        Runnable runnable = new Runnable() {
            @SneakyThrows
            public void run() {
                // task to run goes here
                LocalDateTime dt = LocalDateTime.now();
                String time = dt.getHour() + ":" + dt.getMinute();
                if (timesThatShouldBeRecordedPoints.contains(time)) {
                    BufferedReader br = RecordingWhatIsBeingDone.read();
                    Bot.robot = new Robot();

                    ChromeBrowser chrome = new ChromeBrowser();

                    while ((Bot.readLine = br.readLine()) != null) {
                        Bot.row();
                    }
                    chrome.waitDriver();
                }

                System.out.println(time);
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    private static void row() throws InterruptedException {
        String line = readLine.replace(",", "");
        String[] split = line.split(" ");

        if (readLine.contains("Mouse Dragged")) {
            Integer x = Integer.valueOf(split[2]);
            Integer y = Integer.valueOf(split[3]);

            robot.mouseMove(x, y);
        }
        if (readLine.contains("Mouse Click")) {
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

