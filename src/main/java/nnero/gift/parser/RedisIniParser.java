package nnero.gift.parser;

import nnero.gift.bean.ini.RedisIni;
import nnero.gift.util.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午4:57
 * <p>
 * Function:parse ini file
 * <p>
 * ************************************************
 */
public class RedisIniParser extends IniParser<RedisIni> {


    public RedisIniParser(String targetFile) {
        super(targetFile);
    }

    @Override
    public void dealWithKeyAndValue(RedisIni bean, String key, String value) {
        switch (key){
        case "host":
            bean.setHost(value);
            break;
        case "auth":
            bean.setAuth(value);
            break;
        case "port":
            bean.setPort(Integer.parseInt(value));
            break;
        case "dbNumList":
            bean.setDbNumList(IniParser.parseArr(value));
            break;
        }
        if(key.startsWith("list")){
            if(bean.getListKeyMap() == null){
                bean.setListKeyMap(new HashMap<>());
            }
            String numStr = key.substring(4);
            try {
                int num = Integer.parseInt(numStr);
                if(num < 0 || num > 16){
                    throw new RuntimeException("Error:list num is out of range. "+numStr);
                }
                List<String> keys = IniParser.parseArr(value);
                bean.getListKeyMap().put(num,keys);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error:not num. numStr is :"+numStr);
            }
        }
    }
}
