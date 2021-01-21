import com.netflix.astyanax.Keyspace
import com.netflix.astyanax.AstyanaxContext
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl
import com.netflix.astyanax.connectionpool.NodeDiscoveryType
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

/**
 * Created with IntelliJ IDEA.
 * User: rseshadri
 * Date: 5/9/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
class DatabaseManager {

    static Keyspace keyspace = null;


    static {
        AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forKeyspace("demo")
                .forCluster("clCSCluster")
                .withAstyanaxConfiguration(new AstyanaxConfigurationImpl().setDiscoveryType(NodeDiscoveryType.NONE))
                .withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl("MyConnectionPool").
                            setPort(9160).
                            setMaxConnsPerHost(20).
                            setConnectTimeout(15000).
                            setSocketTimeout(30000).
                            setSeeds("ec2-184-72-184-242.compute-1.amazonaws.com:9160")
                        )
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(ThriftFamilyFactory.getInstance());

        context.start();
        keyspace = context.getEntity();
        println "Got keyspace"
    }

    public static Keyspace getKeyspace() {
        return keyspace
    }

}
