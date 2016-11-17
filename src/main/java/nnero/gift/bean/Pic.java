package nnero.gift.bean;

/**
 * **********************************************
 * <p/>
 * Author NNERO
 * <p/>
 * Time : 16/11/14 下午2:23
 * <p/>
 * Function: picture bean packs need info.
 * <p/>
 * ************************************************
 */
public class Pic {

    private String pic_url;//download url

    private String platform; //platform for download os

    private String target_dir; //file dir

    private String target_name;//file name

    private String target_id; //unique id

    public Pic(){}

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTarget_dir() {
        return target_dir;
    }

    public void setTarget_dir(String target_dir) {
        this.target_dir = target_dir;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    @Override
    public String toString() {
        return "[ target_id:"+target_id+
                " target_file:"+target_dir+"/"+target_name+
                " platform:"+platform+"\n"+
                " pic_url:"+pic_url+
                " ]";
    }
}
