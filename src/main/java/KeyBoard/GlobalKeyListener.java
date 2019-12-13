package KeyBoard;

import Recording.RecordingWhatIsBeingDone;
import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.*;

public class GlobalKeyListener implements NativeKeyListener {

    public static final String VC_SLASH = "/";
    public static final String VC_BACK_SLASH = "\\";

    public static GlobalKeyListener instance;
    public static boolean shift = false;

    private static final Set<Integer> KEY_THAT_ARE_NOT_ALLOWED = new HashSet<>(Arrays.asList(NativeKeyEvent.NATIVE_KEY_FIRST,
            NativeKeyEvent.NATIVE_KEY_LAST, NativeKeyEvent.NATIVE_KEY_TYPED, NativeKeyEvent.NATIVE_KEY_PRESSED, NativeKeyEvent.NATIVE_KEY_RELEASED,
            NativeKeyEvent.KEY_LOCATION_UNKNOWN, NativeKeyEvent.KEY_LOCATION_STANDARD, NativeKeyEvent.VC_ESCAPE, NativeKeyEvent.VC_F1, NativeKeyEvent.VC_F2, NativeKeyEvent.VC_F3, NativeKeyEvent.VC_F4,
            NativeKeyEvent.VC_F5, NativeKeyEvent.VC_F6, NativeKeyEvent.VC_F7, NativeKeyEvent.VC_F8, NativeKeyEvent.VC_F9, NativeKeyEvent.VC_F10, NativeKeyEvent.VC_F11,
            NativeKeyEvent.VC_F12, NativeKeyEvent.VC_F13, NativeKeyEvent.VC_F14, NativeKeyEvent.VC_F15, NativeKeyEvent.VC_F16, NativeKeyEvent.VC_F17, NativeKeyEvent.VC_F18, NativeKeyEvent.VC_F19,
            NativeKeyEvent.VC_F20, NativeKeyEvent.VC_F21, NativeKeyEvent.VC_F22, NativeKeyEvent.VC_F23, NativeKeyEvent.VC_F24, NativeKeyEvent.VC_BACKQUOTE, NativeKeyEvent.VC_MINUS,
            NativeKeyEvent.VC_EQUALS, NativeKeyEvent.VC_BACKSPACE, NativeKeyEvent.VC_TAB, NativeKeyEvent.VC_CAPS_LOCK, NativeKeyEvent.VC_OPEN_BRACKET, NativeKeyEvent.VC_CLOSE_BRACKET,
            NativeKeyEvent.VC_QUOTE, NativeKeyEvent.VC_ENTER, NativeKeyEvent.VC_COMMA,
            NativeKeyEvent.VC_SPACE, NativeKeyEvent.VC_PRINTSCREEN, NativeKeyEvent.VC_SCROLL_LOCK, NativeKeyEvent.VC_PAUSE, NativeKeyEvent.VC_INSERT, NativeKeyEvent.VC_DELETE, NativeKeyEvent.VC_HOME,
            NativeKeyEvent.VC_END, NativeKeyEvent.VC_PAGE_UP, NativeKeyEvent.VC_PAGE_DOWN, NativeKeyEvent.VC_UP, NativeKeyEvent.VC_LEFT, NativeKeyEvent.VC_CLEAR, NativeKeyEvent.VC_RIGHT, NativeKeyEvent.VC_DOWN,
            NativeKeyEvent.VC_NUM_LOCK, NativeKeyEvent.VC_SEPARATOR, NativeKeyEvent.VC_SHIFT, NativeKeyEvent.VC_CONTROL, NativeKeyEvent.VC_ALT, NativeKeyEvent.VC_META, NativeKeyEvent.VC_CONTEXT_MENU, NativeKeyEvent.VC_POWER,
            NativeKeyEvent.VC_SLEEP, NativeKeyEvent.VC_WAKE, NativeKeyEvent.VC_MEDIA_PLAY, NativeKeyEvent.VC_MEDIA_STOP, NativeKeyEvent.VC_MEDIA_PREVIOUS, NativeKeyEvent.VC_MEDIA_NEXT, NativeKeyEvent.VC_MEDIA_SELECT,
            NativeKeyEvent.VC_MEDIA_EJECT, NativeKeyEvent.VC_VOLUME_MUTE, NativeKeyEvent.VC_VOLUME_UP, NativeKeyEvent.VC_VOLUME_DOWN, NativeKeyEvent.VC_APP_MAIL, NativeKeyEvent.VC_APP_CALCULATOR, NativeKeyEvent.VC_APP_MUSIC,
            NativeKeyEvent.VC_APP_PICTURES, NativeKeyEvent.VC_BROWSER_SEARCH, NativeKeyEvent.VC_BROWSER_HOME, NativeKeyEvent.VC_BROWSER_BACK, NativeKeyEvent.VC_BROWSER_FORWARD, NativeKeyEvent.VC_BROWSER_STOP, NativeKeyEvent.VC_BROWSER_REFRESH,
            NativeKeyEvent.VC_BROWSER_FAVORITES, NativeKeyEvent.VC_KATAKANA, NativeKeyEvent.VC_UNDERSCORE, NativeKeyEvent.VC_FURIGANA, NativeKeyEvent.VC_KANJI, NativeKeyEvent.VC_HIRAGANA, NativeKeyEvent.VC_YEN, NativeKeyEvent.VC_SUN_HELP,
            NativeKeyEvent.VC_SUN_STOP, NativeKeyEvent.VC_SUN_PROPS, NativeKeyEvent.VC_SUN_FRONT, NativeKeyEvent.VC_SUN_OPEN, NativeKeyEvent.VC_SUN_FIND, NativeKeyEvent.VC_SUN_AGAIN, NativeKeyEvent.VC_SUN_UNDO, NativeKeyEvent.VC_SUN_COPY,
            NativeKeyEvent.VC_SUN_INSERT, NativeKeyEvent.VC_SUN_CUT, NativeKeyEvent.VC_UNDEFINED));

    private static Set<Integer> KEY_THAT_ARE_NOT_ALLOWED_ALONG_WITH_THE_SHIFT = null;

    private Map<Integer, String> consume = new HashMap<>();

    public GlobalKeyListener() {
        KEY_THAT_ARE_NOT_ALLOWED_ALONG_WITH_THE_SHIFT = KEY_THAT_ARE_NOT_ALLOWED;
        KEY_THAT_ARE_NOT_ALLOWED_ALONG_WITH_THE_SHIFT.remove(NativeKeyEvent.VC_SHIFT);
        instance = this;
        GlobalScreen.addNativeKeyListener(this);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        int code = e.getKeyCode();
        if (!consume.containsKey(code))
            consume.put(code, NativeKeyEvent.getKeyText(code));
    }

    @SneakyThrows
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (consume.containsKey(NativeKeyEvent.VC_SHIFT) && !KEY_THAT_ARE_NOT_ALLOWED_ALONG_WITH_THE_SHIFT.contains(e.getKeyCode())) {
            consume.forEach((key, value) -> {
                if (key != NativeKeyEvent.VC_SHIFT && !KEY_THAT_ARE_NOT_ALLOWED_ALONG_WITH_THE_SHIFT.contains(key)) {
                    RecordingWhatIsBeingDone.instance.writer("Key Pressed " + RunKeyboardAsItIsFile.VK_SHIFT + "[" + value + "]");
                }
            });
        } else if (e.getKeyCode() == NativeKeyEvent.VC_SPACE) {
            RecordingWhatIsBeingDone.instance.writer("Key Pressed " + NativeKeyEvent.getKeyText(NativeKeyEvent.VC_SPACE));
        } else if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
            RecordingWhatIsBeingDone.instance.writer("Key Pressed " + NativeKeyEvent.getKeyText(NativeKeyEvent.VC_ENTER));
        } else if (e.getKeyCode() == NativeKeyEvent.VC_BACKSPACE) {
            RecordingWhatIsBeingDone.instance.writer("Key Pressed " + RunKeyboardAsItIsFile.VK_BACK_SPACE);
        } else if (consume.containsKey(e.getKeyCode()) && !KEY_THAT_ARE_NOT_ALLOWED.contains(e.getKeyCode())) {
            RecordingWhatIsBeingDone.instance.writer("Key Pressed " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        } else if (e.getKeyCode() == NativeKeyEvent.VC_UNDEFINED) {
            if (VC_SLASH.toCharArray()[0] == e.getRawCode()) {
                RecordingWhatIsBeingDone.instance.writer("Key Pressed " + GlobalKeyListener.VC_SLASH);
            } else if (VC_BACK_SLASH.toCharArray()[0] == e.getRawCode()) {
                RecordingWhatIsBeingDone.instance.writer("Key Pressed " + GlobalKeyListener.VC_BACK_SLASH);
            }
        }
        consume.remove(e.getKeyCode());
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

}