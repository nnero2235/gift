package nnero.gift.parser;

import nnero.gift.util.CommonUtil;
import nnero.gift.util.NLog;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午4:58
 * <p>
 * Function: ini file parser. T return type
 * <p>
 * ************************************************
 */
public abstract class IniParser<T> {

    protected File mTargetFile;

    public IniParser(String targetFile){
        mTargetFile = new File(targetFile);
        if(!mTargetFile.exists()){
            throw new RuntimeException("ini file not exists.");
        }
    }

    public T parse(Class<T> clazz){
        BufferedReader reader = null;
        T bean = null;
        try {
            bean = clazz.newInstance();
            reader = new BufferedReader(new FileReader(mTargetFile));
            String line = null;
            while((line = reader.readLine()) != null){
                if(line.startsWith("#")){ //ignore comment
                    continue;
                }
                String[] keyValue = line.split("=");
                if(keyValue.length == 2) {
                    dealWithKeyAndValue(bean, keyValue[0],keyValue[1]);
                } else {
                    // abandon
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("ini file not exists.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bean;
    }

    protected static List<String> parseArr(String value){
        value = value.trim();
        if(!CommonUtil.isEmpty(value) && value.startsWith("[") &&
                value.endsWith("]")){
            String realValue = value.substring(1,value.length()-1);
            String[] elements = realValue.split(",");
            return Arrays.asList(elements);
        }
        return null;
    }

    /**
     * check key and value then save.
     * @param key
     * @param value
     */
    public abstract void dealWithKeyAndValue(T bean,String key,String value);
}
