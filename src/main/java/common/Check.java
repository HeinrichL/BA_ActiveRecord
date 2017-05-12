package common;

/**
 * Created by Heinrich on 28.11.2015.
 */
public class Check {

    public static void Argument(boolean condition, String message){
        if (!condition){
            throw new IllegalArgumentException(message);
        }
    }
}
