package KVServer;

import java.io.IOException;

public class KVServerStarter {
    public KVServerStarter() throws IOException {
        new KVServer().start();
    }
}
