import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class GlobalKeyConsumer implements NativeKeyListener {

    public static GlobalKeyConsumer instance;

        private Set<Integer> consume = new HashSet<Integer>();

    public GlobalKeyConsumer() {
        instance = this;
        GlobalScreen.setEventDispatcher(new VoidDispatchService());
        GlobalScreen.addNativeKeyListener(this);
    }

    public void dontConsumeKeyCode(int keycode) {
        // holy naming conventions..
        consume.remove(keycode);
    }

    public void removeAll() {
        // holy naming conventions..
        consume.removeAll(consume);
    }

    public void consumeKeyCode(int keycode) {
        consume.add(keycode);
    }

    public Set<Integer> getConsume() {
//        List<String> intString = new ArrayList<>();
//        for (Integer i : consume) {
//            intString.add(NativeKeyEvent.getKeyText(i));
//        }
//
//        String result = String.join(" ", intString);
//        System.out.println(result);

        return consume;
    }

    public void consume(NativeKeyEvent e) {
        int code = e.getKeyCode();
        if (consume.contains(code)) {
            reflectConsume(e);
        } else if (code == NativeKeyEvent.VC_ALT) {
            if (e.getKeyLocation() == 3) { // right alt
                // log.info("Attempting to consume right alt...\t");
                // reflectConsume(e);
            }
        }
    }

    @SneakyThrows
    public void reflectConsume(NativeKeyEvent e) {
        Field f = NativeInputEvent.class.getDeclaredField("reserved");
        f.setAccessible(true);
        f.setShort(e, (short) 0x01);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        consume(e);
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        consume(e);
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    private class VoidDispatchService extends AbstractExecutorService {
        private boolean running;

        private VoidDispatchService() {
            running = true;
        }

        public void shutdown() {
            running = false;
        }

        public List<Runnable> shutdownNow() {
            running = false;
            return new ArrayList<>(0);
        }

        public boolean isShutdown() {
            return !running;
        }

        public boolean isTerminated() {
            return !running;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }

        public void execute(Runnable r) {
            r.run();
        }
    }

}