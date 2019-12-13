package Mouse;

import Recording.RecordingWhatIsBeingDone;
import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseEventListener implements NativeMouseInputListener {
    public static GlobalMouseEventListener instance;

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

    public GlobalMouseEventListener() {
        instance = this;
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
    }
}
