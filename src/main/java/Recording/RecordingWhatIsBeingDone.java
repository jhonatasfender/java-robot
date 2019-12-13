package Recording;

import lombok.SneakyThrows;

import java.io.*;

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

    public static BufferedReader read() throws FileNotFoundException {
        File file = new File("./tabuada.txt");

        return new BufferedReader(new FileReader(file));
    }

    public void close() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @SneakyThrows
            public void run() {
                try {
                    arq.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

}
