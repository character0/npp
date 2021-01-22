package com.clearleap.cs.astyanax

import com.netflix.astyanax.AstyanaxContext
import com.netflix.astyanax.Keyspace
import com.netflix.astyanax.connectionpool.NodeDiscoveryType
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl

import com.netflix.astyanax.thrift.ThriftFamilyFactory
import org.apache.log4j.Logger
import com.google.common.collect.ImmutableMap
import com.netflix.astyanax.model.ColumnFamily
import com.netflix.astyanax.serializers.StringSerializer
import com.netflix.astyanax.model.CqlResult
import com.netflix.astyanax.connectionpool.OperationResult

/**
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 6/4/13
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleAstyanaxClient {

    private static Logger log = Logger.getLogger(com.clearleap.cs.astyanax.SimpleAstyanaxClient.getClass().getName())
    private static Keyspace keyspace = null;
    private static ColumnFamily<String, String> Asset_CF = new ColumnFamily<String, String>(
            "astyanax_asset",                                                   // Column Family Name
            StringSerializer.get(),                                             // Key Serializer
            StringSerializer.get());                                            // Column Name Serializer

    public static Keyspace getKeyspace() {
        return keyspace
    }

    static ColumnFamily<String, String> getAsset_CF() {
        return Asset_CF
    }

    def Keyspace connect(keyspaceName) {
        def aci = new AstyanaxConfigurationImpl().setDiscoveryType(NodeDiscoveryType.NONE)
        aci.setTargetCassandraVersion("1.2.5")

        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forKeyspace(keyspaceName)
                .forCluster("Test Cluster")
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl()
                .setCqlVersion("3.0.0")
                .setTargetCassandraVersion("1.2"))
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool").
                setPort(9160).
                setMaxConnsPerHost(20).
                setConnectTimeout(15000).
                setSocketTimeout(30000).
                setSeeds("127.0.0.1:9160")
        )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
        keyspace = context.getEntity();
        log.info "Connected to Cassandra Cluster Keyspace ${keyspace.getKeyspaceName()}"
    }

    /**
     * Creates CS KeySpace
     * @return
     */
    def createKeyspace() {
        log.info "Creating KEYSPACE"
        // Using simple topology
        keyspace.createKeyspace(ImmutableMap.builder()
                .put("strategy_options", ImmutableMap.builder()
                .put("replication_factor", "1")
                .build())
                .put("strategy_class",     "SimpleStrategy")
                .build()
        )
    }

    def dropKeyspace() {
        log.info "Dropping KEYSPACE"
        keyspace.dropKeyspace();
    }

    /**
     * Creates CS ColumnFamily that models an Asset domain object.
     * @return
     */
    def createAssetColumnFamily() {
        log.debug "Creating asset COLUMN_FAMILY"
        keyspace.createColumnFamily(Asset_CF, ImmutableMap.builder()
                .put("assetId", "UTF8Type")
                .put("guid", "UUIDType")
                .put("startDate", "DateType")
                .put("endDate", "DateType")
                .put("createDate", "DateType")
                /*.put("name", "UTF8Type")
                .put("shortTitle", "UTF8Type")
                .put("episodeName", "UTF8Type")
                .put("description", "UTF8Type")
                .put("ratingSchema", "UTF8Type")
                .put("rating", "UTF8Type")
                .put("state", "UTF8Type")
                .put("priceType", "UTF8Type")
                .put("priceInformation", "UTF8Type")
                .put("priceCurrency", "UTF8Type")
                .put("dateFormat", "UTF8Type")
                .put("product", "UTF8Type")
                .put("provider", "UTF8Type")
                .put("providerId", "UTF8Type")
                .put("duration", "LongType")
                .put("newDays", "LongType")
                .put("lastChanceDays", "LongType")
                .put("rentalLength", "LongType")
                .put("price", "FloatType")*/
                .build());
        log.info "Creating asset COLUMN_FAMILY"
    }

    def createAssetTable() {
        OperationResult result;
        result = keyspace.prepareQuery(Asset_CF)
                .withCql("""CREATE TABLE asset_astyanax (guid varchar, name varchar, shortTitle varchar, episodeName varchar, description varchar, ratingSchema varchar, rating varchar,
                state varchar, priceType varchar, priceInformation varchar, priceCurrency varchar, dateFormat varchar, product varchar,
                provider varchar, providerId varchar, assetId varchar, duration int, newDays int, lastChanceDays int,
                rentalLength int, price float, startDate timestamp, endDate timestamp, createDate timestamp,
                            PRIMARY KEY (assetId));""")
                .execute();
    }

}
