package nnero.gift.core;

import nnero.gift.bean.Target;
import nnero.gift.util.NLog;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午2:33
 * <p/>
 * Function: pic downloader
 * <p/>
 * ************************************************
 */
public class Downloader {

    private ExecutorService mExecutor;
    private int mMaxThreads;

    public Downloader(int num){
        if(num <= 0){
            throw new RuntimeException("threads must be greater than 0");
        }

        if(num >= 128){
            throw new RuntimeException("threads must be less than 128");
        }

        this.mMaxThreads = num;
        this.mExecutor = Executors.newFixedThreadPool(num);
    }

    public int getMaxThreads(){
        return mMaxThreads;
    }

    public Future<Target> execute(final Callable<Target> c){
        Future<Target> future = mExecutor.submit(c);
        return future;
    }
}
