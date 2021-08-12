package DataStructures;

import java.io.IOException;

public interface URLHandler {
    public void connect() throws IOException;
    public void parse() throws InterruptedException;
}
