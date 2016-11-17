package nnero.gift.bean.ini;

import java.util.List;
import java.util.Map;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午4:55
 * <p>
 * Function: ini config for redis
 * <p>
 * ************************************************
 */
public class RedisIni {

    private String host;

    private int port;

    private String auth;

    private Map<Integer,List<String>> listKeyMap;

    private List<String> dbNumList;

    public RedisIni(){}

    public Map<Integer, List<String>> getListKeyMap() {
        return listKeyMap;
    }

    public void setListKeyMap(Map<Integer, List<String>> listKeyMap) {
        this.listKeyMap = listKeyMap;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public List<String> getDbNumList() {
        return dbNumList;
    }

    public void setDbNumList(List<String> dbNumList) {
        this.dbNumList = dbNumList;
    }
}
