package Recording;

import KeyBoard.GlobalKeyListener;
import Mouse.GlobalMouseEventListener;
import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WatchingWhatIsBeingDone {

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

        new GlobalKeyListener();
        new GlobalMouseEventListener();
    }
}
