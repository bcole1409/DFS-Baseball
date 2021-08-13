package DataStructures;

import java.io.IOException;

public interface URLHandler {
    void connect() throws IOException;
    void parse() throws InterruptedException, IOException;
}
