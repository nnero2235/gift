package nnero.gift.provider;

import nnero.gift.bean.Pic;

import java.util.List;

/**
 * **********************************************
 * <p>
 * Author NNERO
 * <p>
 * Time : 16/11/15 下午2:29
 * <p>
 * Function: provider pic bean to engine
 * <p>
 * ************************************************
 */
public interface DataProvider {

    List<Pic> getPicList();
}
