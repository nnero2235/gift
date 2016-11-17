package nnero.gift.bean;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午2:38
 * <p/>
 * Function: download success or failure will return this for download info
 * <p/>
 * ************************************************
 */
public class Target {

    public static final int CODE_NO_SOURCE = -1;

    public static final int CODE_IO_EXCEPTION = -2;

    public static final int CODE_OK = 1000;

    private int code;

    private String info;

    private String target_id;

    private String failure_url;

    private boolean needReDown;//re download if needed

    private boolean everExists; //之前是否存在该文件

    private int reDownloadTimes; //needtimes

    public Target(){
    }

    public boolean isEverExists() {
        return everExists;
    }

    public void setEverExists(boolean everExists) {
        this.everExists = everExists;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public String getFailure_url() {
        return failure_url;
    }

    public void setFailure_url(String failure_url) {
        this.failure_url = failure_url;
    }

    public boolean isNeedReDown() {
        return needReDown;
    }

    public void setNeedReDown(boolean needReDown) {
        this.needReDown = needReDown;
    }

    public int getReDownloadTimes() {
        return reDownloadTimes;
    }

    public void setReDownloadTimes(int reDownloadTimes) {
        this.reDownloadTimes = reDownloadTimes;
    }
}
