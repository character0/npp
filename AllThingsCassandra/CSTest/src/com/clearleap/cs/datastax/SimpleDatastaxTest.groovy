package com.clearleap.cs.datastax

import com.clearleap.cs.TestService

/**
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 6/4/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleDatastaxTest {

    public static void main(String[] args) {
        // Connect and Create keyspace and asset "table"
        DatastaxAssetDao dao = new DatastaxAssetDao()
        dao.client.connect("localhost")
        dao.client.createKeyspace("datastax")
        dao.client.createAssetTable("datastax")
        dao.client.close()

        // Execute test
        dao.client.connect("localhost", "datastax")
        //new TestService().executeTest(dao)
        // Clean up
        dao.client.dropKeyspace("datastax")
        dao.client.close()
    }

}
