import nnero.gift.Gift;
import nnero.gift.bean.Pic;
import nnero.gift.core.DownloadTask;
import nnero.gift.provider.DataProvider;
import nnero.gift.provider.RedisDataProvider;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午3:15
 * <p>
 * Function: gift test
 * <p>
 * ************************************************
 */
public class GiftTest {

    class FixedProvider implements DataProvider{

        List<Pic> sources = new ArrayList<>();

        boolean isProvide;

        public FixedProvider(){
            Pic pic1 = DownloaderTest.createPic("1", "/Users/nnero/test_gift", "1.jpg", "http://desk.fd.zol-img.com.cn/t_s960x600c5/g4/M00/0D/01/Cg-4y1ULoXCII6fEAAeQFx3fsKgAAXCmAPjugYAB5Av166.jpg");
            Pic pic2 = DownloaderTest.createPic("2", "/Users/nnero/test_gift", "2.jpg", "http://image101.360doc.com/DownloadImg/2016/11/0404/83709873_2.jpg");
            Pic pic3 = DownloaderTest.createPic("3", "/Users/nnero/test_gift", "3.jpg", "http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg");
            Pic pic4 = DownloaderTest.createPic("4", "/Users/nnero/test_gift", "4.jpg", "http://pic1.win4000.com/mobile/d/581c3bcb27471.jpg");
            Pic pic5 = DownloaderTest.createPic("5", "/Users/nnero/test_gift", "5.jpg", "http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/03/ChMkJlbKxtqIF93BABJ066MJkLcAALHrQL_qNkAEnUD253.jpg");
            sources.add(pic1);
            sources.add(pic2);
            sources.add(pic3);
            sources.add(pic4);
            sources.add(pic5);
        }

        @Override
        public List<Pic> getPicList() {
            if(!isProvide) {
                isProvide = true;
                return sources;
            }
            return null;
        }
    }

    @Test
    public void giftTest(){
        Gift.create(new FixedProvider(),5)
                .needDownExist(true)
                .runOutOfMainThread(false)
                .startEngine();
    }

    @Test
    public void giftRedisProviderTest(){
        Gift.create(new RedisDataProvider("/Users/nnero/redis.ini"),5)
                .needDownExist(true)
                .runOutOfMainThread(false)
                .startEngine();
    }
}
