package nnero.gift.provider;

import nnero.gift.bean.Pic;
import nnero.gift.bean.ini.RedisIni;
import nnero.gift.parser.RedisIniParser;
import nnero.gift.util.CommonUtil;
import nnero.gift.util.NLog;
import org.json.JSONException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.List;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午4:40
 * <p>
 * Function:default data provider implements
 * <p>
 * ************************************************
 */
public class RedisDataProvider implements DataProvider {

    private Jedis mRedis;

    private RedisIni mRedisBean;

    private int mDBNumIndex;

    private int mCurrentListKeyIndex;

    private boolean mNeedScan;

    private int mCurrentDbNum;

    public RedisDataProvider(String iniFile) {
        RedisIniParser parser = new RedisIniParser(iniFile);
        mRedisBean = parser.parse(RedisIni.class);

        if (mRedisBean.getDbNumList() == null || mRedisBean.getDbNumList().size() == 0) {
            throw new RuntimeException("dbNumList null");
        }

        NLog.d("connecting to DB:"+mRedisBean.getHost()+":"+mRedisBean.getPort());
        mRedis = new Jedis(mRedisBean.getHost(), mRedisBean.getPort());
        String code = mRedis.auth(mRedisBean.getAuth());
        NLog.d("auth:"+code);

        mNeedScan = mRedisBean.getListKeyMap() == null ||
                mRedisBean.getListKeyMap().isEmpty();

        selectDB();

        if(mNeedScan){
            scanListKeys();
        }
    }

    private boolean selectDB(){
        if(mDBNumIndex < mRedisBean.getDbNumList().size()){
            String dbNum = mRedisBean.getDbNumList().get(mDBNumIndex);
            mCurrentDbNum = Integer.parseInt(dbNum.trim());
            mRedis.select(mCurrentDbNum);
            NLog.d("DB select:"+mCurrentDbNum);
            return true;
        } else {
            NLog.d("no DB can selected");
            return false;
        }
    }

    private void scanListKeys(){
        ScanResult<String> result = mRedis.scan("0");
        mRedisBean.setListKeyMap(new HashMap<>());
        mRedisBean.getListKeyMap().put(mCurrentDbNum,result.getResult());
        while(!"0".equals(result.getStringCursor())){
            result = mRedis.scan(result.getStringCursor());
            mRedisBean.getListKeyMap().get(mCurrentDbNum).addAll(result.getResult());
        }
    }

    private String getCurrentListKey(){
        List<String> keyList = mRedisBean.getListKeyMap().get(mCurrentDbNum);
        if(keyList == null || mCurrentListKeyIndex >= keyList.size()){
            NLog.d("keyList not available checkout new!");
            mDBNumIndex++;
            boolean success = selectDB();
            if(success) {
                if(mNeedScan) {
                    scanListKeys();
                }
                keyList = mRedisBean.getListKeyMap().get(mCurrentDbNum);
                mCurrentListKeyIndex = 0;
            } else {
                NLog.d("there is no key. finished!");
                mRedis.close();
                return null;
            }
        }
        return keyList.get(mCurrentListKeyIndex++);
    }

    @Override
    public List<Pic> getPicList() {
        String key = getCurrentListKey();
        NLog.d("get key:"+key);
        if(key != null) {
            List<String> picJsonList = mRedis.lrange(key, 0, -1);
            try {
                return CommonUtil.jsonListToPicList(picJsonList);
            } catch (JSONException e) {
                NLog.e("json exception:can't convert json to Pic");
                return null;
            }
        }
        return null;
    }
}
