package HttpServer;

public class InvalidPathException extends Exception {
    public InvalidPathException (String msg){
        super(msg);
    }
}
