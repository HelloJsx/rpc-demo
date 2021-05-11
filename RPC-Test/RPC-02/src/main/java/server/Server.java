package server;

import java.io.IOException;

public interface Server {

    void stop();

    void start() throws IOException;

    void register(Class serviceInterface,Class impl);

    boolean isRunning();

    int getPort();
}
