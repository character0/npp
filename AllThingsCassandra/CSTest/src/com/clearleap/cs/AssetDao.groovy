package com.clearleap.cs

import com.clearleap.cs.domain.Asset

/**
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 6/7/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AssetDao {

    def boolean save(Asset asset);

    def Asset get(String id);

    def boolean delete(String id);

}
