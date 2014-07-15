package com.datastax.cs

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session
import com.datastax.driver.core.Host
import com.datastax.driver.core.exceptions.AlreadyExistsException
import com.datastax.driver.core.PreparedStatement
import com.datastax.driver.core.BoundStatement
import com.datastax.driver.core.ResultSet

/**
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 6/4/13
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleClient {

    def Cluster cluster
    def Session session

    /**
     * Connects to a C* cluster given the address as String.
     *
     * @param clusterNode the cluster's address as a String
     * @return
     */
    def connect(clusterNode) {

        cluster = Cluster.builder()
                .addContactPoint(clusterNode).build();

        def metadata = cluster.metadata; //.getMetadata()
        println "Connected to cluster: ${metadata.clusterName}" //.getClusterName()

        def allHosts =  metadata.allHosts //getAllHosts()
        allHosts.each { Host host ->
            println "Datacenter: ${host.datacenter}; Host: ${host.address}; Rack: ${host.rack}"
        }

        return session = cluster.connect()
    }

    /**
     * Creates the keyspace and all necessary tables for the example.
     */
    def createSchema() {
        try {
            println "Creating simplex KEYSPACE"
            session.execute( "CREATE KEYSPACE simplex WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};" )

            println "Creating TABLE songs"
            session.execute( "CREATE TABLE simplex.songs (id uuid PRIMARY KEY, title text, album text, artist text, tags set<text>, data blob );" );

            println "Creating TABLE playlists"
            session.execute(
                    "CREATE TABLE simplex.playlists (" +
                            "id uuid," +
                            "title text," +
                            "album text, " +
                            "artist text," +
                            "song_id uuid," +
                            "PRIMARY KEY (id, title, album, artist)" +
                            ");");

        } catch (AlreadyExistsException aee) {
            println "KEYSPACE simplex, TABLE song, TABLE playlists already exist"
        }
    }

    /**
     * Helper to drop a keyspace
     *
     * @param schemaName
     * @return
     */
    def dropSchema(schemaName) {
        session.execute( "DROP KEYSPACE $schemaName;" )
    }

    /**
     * Loads all data necessary for the example exercise as CQL queries to be executed.
     */
    def loadData() {
        println "Loading DATA"

        session.execute(
                "INSERT INTO simplex.songs (id, title, album, artist, tags) VALUES (756716f7-2e54-4715-9f00-91dcbea6cf50, 'La Petite Tonkinoise', 'Bye Bye Blackbird', 'Joséphine Baker', {'jazz', '2013'});");

        session.execute(
                "INSERT INTO simplex.playlists (id, song_id, title, album, artist) " +
                        "VALUES (" +
                        "2cc9ccb7-6221-4ccb-8387-f22b6a1b354d," +
                        "756716f7-2e54-4715-9f00-91dcbea6cf50," +
                        "'La Petite Tonkinoise'," +
                        "'Bye Bye Blackbird'," +
                        "'Joséphine Baker'" +
                        ");");
    }

    /**
     * Alternative method to load data using prepared statements.
     */
    def loadDataUsingBoundStatements() {

        println "Loading DATA using bound statements."

        PreparedStatement statement = session.prepare(
                "INSERT INTO simplex.songs " +
                        "(id, title, album, artist, tags) " +
                        "VALUES (?, ?, ?, ?, ?);");

        BoundStatement boundStatement = new BoundStatement(statement);
        Set<String> tags = new HashSet<String>();
        tags.add("jazz");
        tags.add("2013");

        session.execute( boundStatement.bind(
                UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf50"), "La Petite Tonkinoise'", "Bye Bye Blackbird'", "Joséphine Baker", tags)
        );

        statement = session.prepare(
                "INSERT INTO simplex.playlists " +
                        "(id, song_id, title, album, artist) " +
                        "VALUES (?, ?, ?, ?, ?);");
        boundStatement = new BoundStatement(statement);
        session.execute(boundStatement.bind(
                UUID.fromString("2cc9ccb7-6221-4ccb-8387-f22b6a1b354d"),
                UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf50"),
                "La Petite Tonkinoise",
                "Bye Bye Blackbird",
                "Joséphine Baker") );
    }

    /**
     * Queries the keyspace for playlist data
     */
    def querySchema() {
        println "Querying DATA"
        ResultSet results = session.execute("SELECT * FROM simplex.playlists WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");
        println """
        -----------------------------
            title - album - artist
        -----------------------------
"""
        results.each { row ->
            println "${row.getString("title")} - ${row.getString("album")} - ${row.getString("artist")}"
        }
    }

    /**
     * Helper method to shutdown connections to cluster.
     */
    def close() {
        cluster.shutdown();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SimpleClient client = new SimpleClient();
        client.connect("127.0.0.1")
        client.createSchema();
        //cs.loadData()
        client.loadDataUsingBoundStatements()
        client.querySchema();
        //cs.updateSchema();
        //client.dropSchema("simplex");
        client.close();
    }

}
