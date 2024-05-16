package weather.app;

public class ExceededMaxSizeException extends Exception{
    public ExceededMaxSizeException(String e){
        super(e);
    }
}
