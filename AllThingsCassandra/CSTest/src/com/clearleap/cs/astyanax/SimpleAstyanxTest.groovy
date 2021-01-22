package com.clearleap.cs.astyanax

import com.clearleap.cs.TestService

class SimpleAstyanxTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        AstyanaxAssetDao dao = new AstyanaxAssetDao()
        dao.client.connect("clearleap")
        dao.client.createKeyspace()
        //dao.client.createAssetColumnFamily()
        dao.client.createAssetTable()
        new TestService().executeTest(dao)
        dao.client.dropKeyspace()
    }

}
