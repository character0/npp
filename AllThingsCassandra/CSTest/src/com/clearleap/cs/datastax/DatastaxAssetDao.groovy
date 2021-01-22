package com.clearleap.cs.datastax

import com.datastax.driver.core.PreparedStatement
import com.datastax.driver.core.BoundStatement
import com.datastax.driver.core.ResultSet
import com.clearleap.cs.domain.Asset
import org.apache.log4j.Logger
import org.apache.cassandra.db.Row
import com.clearleap.cs.AssetDao

/**
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 6/4/13
 * Time: 11:33 PM
 * To change this template use File | Settings | File Templates.
 */
class DatastaxAssetDao implements AssetDao {

    private static Logger log = Logger.getLogger(DatastaxAssetDao.getClass().getName())
    private static SimpleDatastaxClient client

    SimpleDatastaxClient getClient() {
        return client
    }

    DatastaxAssetDao() {
        client = new SimpleDatastaxClient()

    }

    /**
     *
     * @param asset
     * @return
     */
    def boolean save(Asset asset) {
        log.debug "Saving Asset ${asset.assetId}"
        PreparedStatement statement = client.session.prepare(
                """INSERT INTO datastax_asset
                        (guid, assetId, createDate, description, startDate, endDate)
                        VALUES (?, ?, ?, ?, ?, ?);""");

        BoundStatement boundStatement = new BoundStatement(statement);

        client.session.execute(
                boundStatement.bind(UUID.fromString(asset.guid), asset.assetId, asset.createDate,
                        asset.description, asset.startDate, asset.endDate)
        );
        log.info "Asset ${asset.assetId} saved."
    }

    /**
     *
     * @param guid
     * @return
     */
    def Asset get(String assetId) {

        PreparedStatement statement = client.session.prepare("SELECT * FROM datastax_asset WHERE assetId = ?;")
        BoundStatement boundStatement = new BoundStatement(statement)

        log.debug "Retrieving Asset $assetId"
        ResultSet results = client.session.execute(
                boundStatement.bind(assetId)
        );

        for (Row row : results) {
          log.info "Retrieved ${row.getString('assetId')}"
        }
    }

    /**
     *
     * @param guid
     * @return
     */
    def boolean delete(String assetId) {

        PreparedStatement statement = client.session.prepare("DELETE FROM datastax_asset WHERE assetId = ?;")
        BoundStatement boundStatement = new BoundStatement()

        log.info "Deleting asset $assetId"
        ResultSet results = client.session.execute(
                boundStatement.bind(assetId)
        );
    }

}