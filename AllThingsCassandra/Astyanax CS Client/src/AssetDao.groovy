import domain.Asset
import com.netflix.astyanax.model.ColumnFamily
import com.netflix.astyanax.serializers.StringSerializer
import com.netflix.astyanax.MutationBatch
import com.netflix.astyanax.connectionpool.OperationResult
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException
import com.netflix.astyanax.recipes.UniquenessConstraintWithPrefix
import com.netflix.astyanax.model.ColumnList
import com.netflix.astyanax.model.ConsistencyLevel
/**
 * Created with IntelliJ IDEA.
 * User: rseshadri
 * Date: 5/10/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
class AssetDao {

    private static ColumnFamily<String, String> Asset_CF = new ColumnFamily<String, String>(
            "asset",                                                            // Column Family Name
            StringSerializer.get(),                                             // Key Serializer
            StringSerializer.get());                                            // Column Name Serializer



    public static save(Asset asset) {
        MutationBatch m = DatabaseManager.keyspace.prepareMutationBatch();
        m.setConsistencyLevel(ConsistencyLevel.CL_ALL);

        /*UniquenessConstraintWithPrefix<String> unique = new UniquenessConstraintWithPrefix<String>(DatabaseManager.keyspace, Asset_CF)
                .setTtl(60);  // This is optional

        try {
            String column = unique.isUnique(asset.guid);
            if (column == null) {
                new RuntimeException("Asset Guid not unique")
            }
            else {*/
                def row = m.withRow(Asset_CF, asset.guid)
                row.putColumn("assetId", asset.assetId)
                row.putColumn("createDate",asset.createDate)
                row.putColumn("description",asset.description)
                row.putColumn("startDate",asset.startDate)
                row.putColumn("endDate", asset.endDate)

                OperationResult<Void> result = m.execute();
                //println "Save successful"
            /*}
        } catch (ConnectionException e) {
            println "Exception $e.message"
            e.printStackTrace()
        } */
    }

    public static delete(String assetGuid) {
        MutationBatch m = DatabaseManager.keyspace.prepareMutationBatch();
        m.withRow(Asset_CF, assetGuid).delete();
        try {
            OperationResult<Void>result=m.execute();
        } catch(ConnectionException ex) {
            println "Exception $ex.message"
            ex.printStackTrace();
        }
    }

    public static Asset get(String guid) {
        ColumnList<String> result = DatabaseManager.keyspace.prepareQuery(Asset_CF)
                .getKey(guid)
                .execute().getResult();
        Asset asset = null;
        if (!result.isEmpty()) {
            asset = new Asset()
            asset.guid = guid
            asset.assetId = result.getStringValue("assetId", null);
        } else {
            println "Row is empty -- guid $guid"
        }
        return asset
    }



}
