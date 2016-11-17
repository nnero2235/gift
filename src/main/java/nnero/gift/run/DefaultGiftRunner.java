package nnero.gift.run;

import nnero.gift.Gift;
import nnero.gift.provider.RedisDataProvider;
import nnero.gift.util.NLog;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/17 上午11:55
 * <p>
 * Function: the default main class for running alone
 * <p>
 * ************************************************
 */
public class DefaultGiftRunner {


    public static void main(String[] args) {
        if(args != null && (args.length == 1 || args.length == 2)){

            int threads = 5;

            if(args.length == 2){
                NLog.d("file:"+args[0]);
                NLog.d("threads:"+args[1]);
                try {
                    threads = Integer.parseInt(args[1]);
                    threads = threads < 1 ? 1 : threads;
                    threads = threads > 10 ? 10 : threads;
                } catch (NumberFormatException e) {
                    NLog.e("third arg must be number with 1 ~ 10");
                }
            }

            Gift.create(new RedisDataProvider(args[0]),threads)
                    .runOutOfMainThread(false)
                    .needDownExist(false)
                    .startEngine();
        } else {
            NLog.e("usage:java gift.jar 'redis.ini file location' 'threadNumber'!");
        }
    }
}
