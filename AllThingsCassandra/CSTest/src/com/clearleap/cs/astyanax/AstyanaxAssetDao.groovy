package com.clearleap.cs.astyanax

import com.clearleap.cs.domain.Asset
import com.netflix.astyanax.model.ColumnFamily
import com.netflix.astyanax.serializers.StringSerializer
import com.netflix.astyanax.MutationBatch
import com.netflix.astyanax.connectionpool.OperationResult
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException

import com.netflix.astyanax.model.ColumnList
import com.netflix.astyanax.model.ConsistencyLevel
import com.clearleap.cs.AssetDao
import com.clearleap.cs.datastax.SimpleDatastaxClient
import org.apache.log4j.Logger
import com.netflix.astyanax.model.Row
/**
 * Created with IntelliJ IDEA.
 * User: rseshadri
 * Date: 5/10/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
class AstyanaxAssetDao implements AssetDao {

    private static Logger log = Logger.getLogger(AstyanaxAssetDao.getClass().getName())
    private static SimpleAstyanaxClient client = new SimpleAstyanaxClient()

    SimpleAstyanaxClient getClient() {
        return client
    }

    /**
     *
     * @param asset
     */
    public boolean save(Asset asset) throws ConnectionException{
        MutationBatch m = SimpleAstyanaxClient.keyspace.prepareMutationBatch();
        m.setConsistencyLevel(ConsistencyLevel.CL_ALL);

        log.debug "Saving Asset ${asset.assetId}"
        def row = m.withRow(client.Asset_CF, asset.guid)
        row.putColumn("assetId", asset.assetId)
        row.putColumn("createDate", asset.createDate)
        row.putColumn("description", asset.description)
        row.putColumn("startDate", asset.startDate)
        row.putColumn("endDate", asset.endDate)

        OperationResult<Void> result = m.execute();
        log.info "Asset ${asset.assetId} saved."


    }

    /**
     *
     * @param assetGuid
     */
    public boolean delete(String assetId) {
        /*MutationBatch m = SimpleAstyanaxClient.keyspace.prepareMutationBatch();
        m.withRow(client.Asset_CF, assetId).delete();
        try {
            log.info "Deleting asset $assetId"
            OperationResult<Void> result = m.execute();
        } catch (ConnectionException ex) {
            println "Exception $ex.message"
            ex.printStackTrace();
        }*/
    }

    /**
     *
     * @param guid
     * @return
     */
    public Asset get(String assetId) {
        /*ColumnList<String> result = SimpleAstyanaxClient.keyspace.prepareQuery(client.Asset_CF)
                .getKey(assetId)
                .execute().getResult();
        Asset asset = null;
        if (!result.isEmpty()) {
            asset = new Asset()
            asset.assetId = result.getStringValue("assetId", null);
        } else {
            log.info "Did not find asset $assetId to retrieve"
        }*/

        def result = SimpleAstyanaxClient.keyspace
                .prepareQuery(client.Asset_CF)
                .withCql("SELECT * FROM astyanax_asset WHERE assetId='$assetId';")
                .execute();

        for (Row<Integer, String> row : result.getResult().getRows()) {
            log.info("CQL Key: " + row.getKey());

            ColumnList<String> columns = row.getColumns();

            log.info("   assetId      : " + columns.getIntegerValue("assetId",      null));

        }

        return new Asset()
    }

}
