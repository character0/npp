package com.clearleap.cs.datastax.example

import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.Query
import com.datastax.driver.core.Row
import com.datastax.driver.core.ResultSetFuture
import com.clearleap.cs.datastax.example.SimpleClient

class AsynchronousSelect extends SimpleClient {

    def ResultSetFuture getSongs() {
        Query query = QueryBuilder.select().all().from("simplex", "songs");
        return session.executeAsync(query);
    }

    public static void main(String[] args) {
        def client = new AsynchronousSelect();
        client.connect("127.0.0.1");
        client.createSchema();
        client.loadData();

        ResultSetFuture results = client.getSongs();
        println "All Data:"
        results.getUninterruptibly().each() { Row row ->
            println "${row.getString("artist")} - ${row.getString("title")} - ${row.getString("album")}"
        }
        client.dropSchema("simplex");
        client.close();
    }


}