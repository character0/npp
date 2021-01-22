package com.clearleap.cs.datastax

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Metadata
import com.datastax.driver.core.Host
import com.datastax.driver.core.Session
import com.datastax.driver.core.exceptions.AlreadyExistsException
import org.apache.log4j.Logger

class SimpleDatastaxClient {

    private static Logger log = Logger.getLogger(SimpleDatastaxClient.getClass().getName())
    Cluster cluster
    Session session

    def connect(String clusterNode, String keyspace = null) {

        cluster = Cluster.builder()
                .addContactPoint(clusterNode).build();

        Metadata metadata = cluster.getMetadata();
        log.info "Connected to cluster: ${metadata.getClusterName()}"

        def allHosts = metadata.getAllHosts()
        allHosts.each { Host host ->
            log.info "Datacenter: ${host.getDatacenter()}; Host: ${host.getAddress()}; Rack: ${host.getRack()}"
        }

        session = keyspace ? cluster.connect(keyspace) : cluster.connect()
    }

    def close() {
        cluster.shutdown();
    }

    def createKeyspace(String schemaName) {
        try {
            log.info "Creating KEYSPACE $schemaName"
            session.execute("CREATE KEYSPACE $schemaName WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};")
        } catch (AlreadyExistsException aee) {
            log.info "#${aee.message}# - KEYSPACE $schemaName already exists"
        }
    }

    def dropKeyspace(schemaName) {
        log.info "Dropping keyspace $schemaName"
        session.execute("DROP KEYSPACE $schemaName;")
    }

    /**
     * @return
     */
    def createAssetTable(keyspace) {
        try {
            log.info "Creating Asset TABLE"
            session.execute("""CREATE TABLE ${keyspace}.datastax_asset
                (guid uuid, name text, shortTitle text, episodeName text, description text, ratingSchema text, rating text,
                state text, priceType text, priceInformation text, priceCurrency text, dateFormat text, product text,
                provider text, providerId text, assetId text PRIMARY KEY, duration int, newDays int, lastChanceDays int,
                rentalLength int, price float, startDate timestamp, endDate timestamp, createDate timestamp);""");
        } catch (AlreadyExistsException aee) {
            log.warn "#${aee.message}# - TABLE datastax_asset already exists in keyspace ${keyspace}"
        }
    }

}