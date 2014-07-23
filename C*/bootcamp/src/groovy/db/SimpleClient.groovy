package groovy.db

import com.datastax.driver.core.*
import com.datastax.driver.core.exceptions.AlreadyExistsException
import org.apache.commons.logging.LogFactory

/**
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 6/4/13
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleClient {

    static def Cluster cluster
    static def Session session
    static def String schema;

    private static final log = LogFactory.getLog(this)

    /**
     * Connects to a C* cluster given the address as String.
     *
     * @param clusterNode the cluster's address as a String
     * @return
     */
    def static connect(clusterNode) {

        log.warn "connect -> Building cluster session @ $clusterNode"
        cluster = Cluster.builder().addContactPoint(clusterNode).build();



        def metadata = cluster.metadata; //.getMetadata()
        log.info "connect -> Connected to cluster: ${metadata.clusterName}" //.getClusterName()

        def allHosts =  metadata.allHosts //getAllHosts()
        allHosts.each { Host host ->
            log.info "connect -> Datacenter: ${host.datacenter}; Host: ${host.address}; Rack: ${host.rack}"
        }

        return session = cluster.connect()
    }

    /**
     * Creates the keyspace and all necessary tables for the example.
     */
    def static createKeyspace(String name, String strategy, String replication, def dataCenters = [:]) {
        try {
            // TODO if dataCenters passed in, create NetworkTopology etc.
            log.info "createKeyspace -> Creating keyspace '$name' with strategy $strategy and replication factor of $replication"
            session.execute( "CREATE KEYSPACE ${name} WITH REPLICATION = { 'class':'${strategy}', 'replication_factor':'${replication}' };" )
        } catch (AlreadyExistsException aee) {
            log.info "createKeyspace -> Keyspace $name already exist"
        }
    }

    /**
     * Helper to drop a keyspace
     *
     * @param schemaName
     * @return
     */
    def static dropKeyspace(String keyspace) {
        session.execute( "DROP KEYSPACE $keyspace;" )
    }

    /**
     * Helper method to shutdown connections to cluster.
     */
    def static close() {
        cluster.shutdown();
    }

}
