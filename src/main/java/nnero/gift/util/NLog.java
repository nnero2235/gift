package nnero.gift.util;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午6:47
 * <p/>
 * Function:log util
 * <p/>
 * ************************************************
 */
public class NLog {

    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_ERROR = 3;

    public static int logLevel = 1;

    public static void d(String msg){
        if(logLevel <= LEVEL_DEBUG) {
            System.out.println("[DEBUG: "+msg+" ]");
        }
    }

    public static void e(String msg){
        if(logLevel <= LEVEL_ERROR) {
            System.out.println("[ERROR: "+msg+" ]");
        }
    }
}
