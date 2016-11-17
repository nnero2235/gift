import nnero.gift.bean.ini.RedisIni;
import nnero.gift.parser.RedisIniParser;
import nnero.gift.util.NLog;
import org.junit.Test;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/16 下午2:42
 * <p>
 * Function:
 * <p>
 * ************************************************
 */
public class ParserTest {

    @Test
    public void redisParserTest(){
        RedisIni redisIni = new RedisIniParser("/Users/nnero/redis.ini")
                .parse(RedisIni.class);
        NLog.d("host:"+redisIni.getHost());
        NLog.d("port:"+redisIni.getPort());
        NLog.d("auth:"+redisIni.getAuth());

        if(redisIni.getDbNumList() != null) {
            for (String num : redisIni.getDbNumList()){
                NLog.d("num:"+num);
            }
        }

        if(redisIni.getListKeyMap() != null){
            for(Integer dbNum:redisIni.getListKeyMap().keySet()){
                for(String key : redisIni.getListKeyMap().get(dbNum)){
                    NLog.d("dbNUM="+dbNum+"--->"+"key:"+key);
                }
            }
        }
    }
}
