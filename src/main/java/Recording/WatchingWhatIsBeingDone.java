package Recording;

import KeyBoard.GlobalKeyListener;
import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WatchingWhatIsBeingDone implements NativeMouseInputListener {

    public void nativeMouseClicked(NativeMouseEvent e) {
        // System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        RecordingWhatIsBeingDone.instance.writer("Mouse Pressed: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        System.out.println("Mouse Released: " + e.getButton());
    }

    public void nativeMouseWheelMoved(NativeMouseEvent e) {
        System.out.println("Mouse Wheel: " + e.getButton());
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        System.out.println("Mouse Moved: " + Math.abs(e.getX()) + ", " + Math.abs(e.getY()));
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        RecordingWhatIsBeingDone.instance.writer("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }

    @SneakyThrows
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        logger.setUseParentHandlers(false);

        new RecordingWhatIsBeingDone();

        RecordingWhatIsBeingDone.instance.close();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.exit(1);
        }

        WatchingWhatIsBeingDone whatIsBeingDone = new WatchingWhatIsBeingDone();

        GlobalScreen.addNativeMouseListener(whatIsBeingDone);
        GlobalScreen.addNativeMouseMotionListener(whatIsBeingDone);

        new GlobalKeyListener();
    }
}
