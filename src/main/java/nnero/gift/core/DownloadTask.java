package nnero.gift.core;

import nnero.gift.bean.Pic;
import nnero.gift.bean.Target;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午2:35
 * <p/>
 * Function:pic download task
 * <p/>
 * ************************************************
 */
public class DownloadTask implements Callable<Target> {

    private static final int CONNECT_TIMEOUT = 60000;

    private static final int READ_TIMEOUT = 60000;

    private Pic mPic;

    public DownloadTask(Pic pic) {
        this.mPic = pic;
    }

    @Override
    public Target call() throws Exception {

        Target target = new Target();

        if (mPic == null) {
            target.setCode(Target.CODE_NO_SOURCE);
            target.setInfo("no resource found:mPic NULL");
            return target;
        }

        File dir = new File(mPic.getTarget_dir());

        if(!dir.exists()){
            dir.mkdir();
        }

        File targetFile = new File(mPic.getTarget_dir() +
                File.separator + mPic.getTarget_name());

        if(targetFile.exists()){
            target.setEverExists(true);
            targetFile.delete();
        }

        HttpURLConnection conn = null;
        BufferedSource source = null;
        BufferedSink sink = null;
        try {
            conn = (HttpURLConnection) new URL(mPic.getPic_url()).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.connect();

            source = Okio.buffer(Okio.source(conn.getInputStream()));
            sink = Okio.buffer(Okio.sink(targetFile));
            sink.writeAll(source);
            sink.flush();

            target.setCode(Target.CODE_OK);
            target.setInfo("download success");
            target.setNeedReDown(false);
            target.setTarget_id(mPic.getTarget_id());
        } catch (IOException e) {
            target.setCode(Target.CODE_IO_EXCEPTION);
            target.setInfo("io exception:"+e.getMessage());
            target.setFailure_url(mPic.getPic_url());
            target.setNeedReDown(true);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if(sink != null){
                sink.close();
            }
            if(source != null){
                source.close();
            }
        }
        return target;
    }
}
