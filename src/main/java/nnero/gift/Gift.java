package nnero.gift;

import nnero.gift.bean.Pic;
import nnero.gift.bean.Target;
import nnero.gift.core.DownloadTask;
import nnero.gift.core.Downloader;
import nnero.gift.provider.DataProvider;
import nnero.gift.util.NLog;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午2:14
 * <p/>
 * Function: main class for down. threads recommends 5 ~ 10
 * <p/>
 * ************************************************
 */
public class Gift {

    private boolean needDownExistResource; // true :if file exists also down and cover it.

    private DataProvider mProvider;

    private Downloader mDownloader;

    private ExecutorService mSubThread;

    private ExecutorService mFutureThread;

    private boolean isShutDown;

    private LinkedBlockingQueue<Future<Target>> mQueue;

    private volatile int mNullCount;//future null count if reach 10000 then shutdown

    public Gift(DataProvider provider,int threads){
        needDownExistResource = false;
        mDownloader = new Downloader(threads);
        mProvider = provider;
        mQueue = new LinkedBlockingQueue<>();
        mFutureThread = Executors.newSingleThreadExecutor();
    }

    public Gift needDownExist(boolean need){
        this.needDownExistResource = need;
        return this;
    }

    public Gift runOutOfMainThread(boolean need){
        if(need){
            mSubThread = Executors.newSingleThreadExecutor();
        }
        return this;
    }

    public static Gift create(DataProvider provider,int threads){
        Gift gift = new Gift(provider,threads);
        return gift;
    }

    public void startEngine(){
        if(mProvider == null){
            throw new RuntimeException("DataProvider is null.Please set it then start!");
        }

        mFutureThread.execute(this::startFutureDeal);

        if(mSubThread != null) {
            mSubThread.execute(this::startReal);
        } else {
            startReal();
        }
    }

    private void startReal(){
        while (!isShutDown){
            List<Pic> sources = mProvider.getPicList();
            if(sources != null){
                for(Pic pic : sources){
                    Future<Target> future = mDownloader.execute(new DownloadTask(pic));
                    try {
                        mQueue.put(future);
                    } catch (InterruptedException e) {
                        NLog.e("Queue interrupted");
                    }
                }
            } else {
                try {
                    NLog.d("await!");
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        NLog.d("engine exit!");
    }

    private void startFutureDeal(){
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!isShutDown){
            Future<Target> future = mQueue.poll(); //remove and return element

            if(future == null){
                mNullCount++;
                if(mNullCount >= 1000){
                    isShutDown = true;
                    synchronized (this){
                        this.notify();
                    }
                    NLog.d("shut down!");
                }
                continue;
            }
            try {
                Target target = future.get();
                if(target.getCode() == Target.CODE_OK) {
                    NLog.d(target.getTarget_id()+":"+target.getInfo());
                } else if(target.getCode() == Target.CODE_IO_EXCEPTION){
                    NLog.d(target.getTarget_id()+":"+target.getInfo()+":"+target.getFailure_url());
                } else {
                    //no resource
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
