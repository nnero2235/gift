package nnero.gift.bean;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午2:31
 * <p/>
 * Function:result for pic
 * <p/>
 * ************************************************
 */
public class Result {

    private String code;

    private String info;

    private Pic pic;

    public Result(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Pic getPic() {
        return pic;
    }

    public void setPic(Pic pic) {
        this.pic = pic;
    }
}
