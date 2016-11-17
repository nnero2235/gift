import nnero.gift.bean.Pic;
import nnero.gift.bean.Target;
import nnero.gift.core.DownloadTask;
import nnero.gift.core.Downloader;
import nnero.gift.util.NLog;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午6:54
 * <p/>
 * Function:test downloader
 * <p/>
 * ************************************************
 */
public class DownloaderTest {

    public static Pic createPic(String id,String dir,String name,String url){
        Pic pic = new Pic();
        pic.setTarget_id(id);
        pic.setPlatform("unix");
        pic.setTarget_dir(dir);
        pic.setTarget_name(name);
        pic.setPic_url(url);
        return pic;
    }

    @Test
    public void testDownloader(){
        Downloader downloader = new Downloader(5);
        List<Future<Target>> futures = new ArrayList<>();
        Pic pic1 = createPic("1", "/Users/nnero/test_gift", "1.jpg", "http://desk.fd.zol-img.com.cn/t_s960x600c5/g4/M00/0D/01/Cg-4y1ULoXCII6fEAAeQFx3fsKgAAXCmAPjugYAB5Av166.jpg");
        futures.add(downloader.execute(new DownloadTask(pic1)));
        Pic pic2 = createPic("2","/Users/nnero/test_gift","2.jpg","http://image101.360doc.com/DownloadImg/2016/11/0404/83709873_2.jpg");
        futures.add(downloader.execute(new DownloadTask(pic2)));
        Pic pic3 = createPic("3","/Users/nnero/test_gift","3.jpg","http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg");
        futures.add(downloader.execute(new DownloadTask(pic3)));
        Pic pic4 = createPic("4","/Users/nnero/test_gift","4.jpg","http://pic1.win4000.com/mobile/d/581c3bcb27471.jpg");
        futures.add(downloader.execute(new DownloadTask(pic4)));
        Pic pic5 = createPic("5","/Users/nnero/test_gift","5.jpg","http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/03/ChMkJlbKxtqIF93BABJ066MJkLcAALHrQL_qNkAEnUD253.jpg");
        futures.add(downloader.execute(new DownloadTask(pic5)));

        for(Future<Target> future : futures){
            try {
                Target target = future.get();
                NLog.d(target.getInfo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
