import nnero.gift.util.NLog;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午4:13
 * <p>
 * Function:
 * <p>
 * ************************************************
 */
public class JedisTest {

    Jedis mRedis;

    @Before
    public void setUp(){
        mRedis = new Jedis("localhost",6379);
        mRedis.auth("nnero");
        mRedis.select(1);
    }

    @Test
    public void testJedis(){
//        mRedis.lpush("mylist","{\"code\":\"1000\",\"info\":\"ok\",\"pic\":{\"pic_url\":\"http://desk.fd.zol-img.com.cn/t_s960x600c5/g4/M00/0D/01/Cg-4y1ULoXCII6fEAAeQFx3fsKgAAXCmAPjugYAB5Av166.jpg\",\"platform\":\"unix\",\"target_dir\":\"/Users/nnero/test_gift\",\"target_name\":\"1.jpg\",\"target_id\":\"1\"}}");
//        mRedis.lpush("mylist","{\"code\":\"1000\",\"info\":\"ok\",\"pic\":{\"pic_url\":\"http://image101.360doc.com/DownloadImg/2016/11/0404/83709873_2.jpg\",\"platform\":\"unix\",\"target_dir\":\"/Users/nnero/test_gift\",\"target_name\":\"2.jpg\",\"target_id\":\"2\"}}");
        mRedis.lpush("mylist1", "{\"code\":\"1000\",\"info\":\"ok\",\"pic\":{\"pic_url\":\"http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg\",\"platform\":\"unix\",\"target_dir\":\"/Users/nnero/test_gift\",\"target_name\":\"3.jpg\",\"target_id\":\"3\"}}");
        mRedis.lpush("mylist1", "{\"code\":\"1000\",\"info\":\"ok\",\"pic\":{\"pic_url\":\"http://pic1.win4000.com/mobile/d/581c3bcb27471.jpg\",\"platform\":\"unix\",\"target_dir\":\"/Users/nnero/test_gift\",\"target_name\":\"4.jpg\",\"target_id\":\"4\"}}");
    }

    @Test
    public void testScan(){
        String cursor = "0";
        do{
            ScanResult<String> result = mRedis.scan("0");
            cursor = result.getStringCursor();
            List<String> keys = result.getResult();
            if(keys != null) {
                for (String key : keys) {
                    NLog.d(key);
                }
            }
        }while (!"0".equals(cursor));
        mRedis.close();
    }
}
