import lombok.SneakyThrows;
import org.jnativehook.GlobalScreen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RecordingWhatIsBeingDone {
    private static FileWriter arq;
    private static PrintWriter writer;

    public static RecordingWhatIsBeingDone instance;

    public RecordingWhatIsBeingDone() throws IOException {
        arq = new FileWriter("./tabuada.txt");
        writer = new PrintWriter(arq);
        instance = this;

        writer.print("");
    }

    public void writer(String anyThing) {
        writer.println(anyThing);
    }

    public void close() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @SneakyThrows
            public void run() {
                arq.close();
            }
        }));
    }

}
