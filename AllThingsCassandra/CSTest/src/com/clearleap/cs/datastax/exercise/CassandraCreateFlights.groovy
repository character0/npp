package com.clearleap.cs.datastax.exercise

import com.datastax.driver.core.*
import com.datastax.driver.core.exceptions.AlreadyExistsException

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created with IntelliJ IDEA.
 * User: npp
 * Date: 6/4/13
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
class CassandraCreateFlights {

    def Cluster cluster
    def Session session

    def connect(String clusterNode) {

        cluster = Cluster.builder()
                .addContactPoint(clusterNode).build();

        Metadata metadata = cluster.getMetadata();
        println "Connected to cluster: ${metadata.getClusterName()}"

        def allHosts =  metadata.getAllHosts()
        allHosts.each { Host host ->
            println "Datacenter: ${host.getDatacenter()}; Host: ${host.getAddress()}; Rack: ${host.getRack()}"
        }

        return session = cluster.connect()
    }

    def createSchema() {
        try {

            println "Creating KEYSPACE csflight"
            session.execute( "CREATE KEYSPACE csflight WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};" )

        } catch (AlreadyExistsException aee) {
            println "KEYSPACE csflight, COLUMN FAMILY flights already exist"
        }

        try {

            println "Creating column family flights"
            session.execute( """CREATE TABLE csflight.flights (ID int PRIMARY KEY, YEAR int, DAY_OF_MONTH int,
                                FL_DATE timestamp, AIRLINE_ID int, CARRIER varchar, FL_NUM int,ORIGIN_AIRPORT_ID int,
                                ORIGIN varchar, ORIGIN_CITY_NAME varchar, ORIGIN_STATE_ABR varchar, DEST varchar,
                                DEST_CITY_NAME varchar, DEST_STATE_ABR varchar, DEP_TIME timestamp, ARR_TIME timestamp,
                                ACTUAL_ELAPSED_TIME timestamp, AIR_TIME timestamp, DISTANCE int);""" );

        } catch (AlreadyExistsException aee) {
            println "KEYSPACE csflight, COLUMN FAMILY flights already exist"
        }
    }

    def dropSchema(schemaName) {
        session.execute( "DROP KEYSPACE $schemaName;" )
    }

    def loadDataUsingBoundStatements() {

        def flightDataFile = new File("ClearleapCSTest/src/com/clearleap/cs/datastax/exercise/config/flights_from_pg.csv")
        println "File path: ${flightDataFile.absolutePath}"

        String[] lines = flightDataFile.text.split('\n')
        List<String[]> rows = lines.collect {it.split(',')}
         //def row = rows[0]
        rows.each { row ->

            println "Parsed Row: $row"

            def flightDataSet = [:]
            flightDataSet.id = Integer.parseInt(row[0].trim())
            flightDataSet.year = Integer.parseInt(row[1].trim())
            flightDataSet.dayOfMonth = Integer.parseInt(row[2].trim())

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd")
            //flightDataSet.flDate =  sdf.parse(row[3].trim())

            flightDataSet.airlineId = Integer.parseInt(row[4].trim())
            flightDataSet.carrier = row[5].trim()
            flightDataSet.flNum = Integer.parseInt(row[6].trim())
            flightDataSet.originAirportId = Integer.parseInt(row[7].trim())
            flightDataSet.origin = row[8].trim()
            flightDataSet.originCityName = row[9].trim()
            flightDataSet.originStateAbr = row[10].trim()
            flightDataSet.dest = row[11].trim()
            flightDataSet.destCityName = row[12].trim()
            flightDataSet.destStateAbr = row[13].trim()

            try {
                //flightDataSet.depTime = new SimpleDateFormat("mmss").parse(row[14].trim()) //TODO
            } catch (ParseException pe) { /* ignore */}

            try {
                //flightDataSet.arrTime = new SimpleDateFormat("mmss").parse(row[15].trim()) //TODO
            } catch (ParseException pe) { /* ignore */}

            try {
                //flightDataSet.actualElapsedTime = new SimpleDateFormat("mmss").parse(row[16].trim()) //TODO
            } catch (ParseException pe) { /* ignore */}

            try {
                //flightDataSet.airTime = new SimpleDateFormat("mmss").parse(row[17].trim()) //TODO
            } catch (ParseException pe) { /* ignore */}

            flightDataSet.distance = Integer.parseInt(row[18].trim())

            def query = "INSERT INTO csflight.flights (id, year, day_of_month, fl_date, airline_id, carrier, " +
                    "fl_num, origin_airport_id, origin, origin_city_name, origin_state_abr, dest, dest_city_name, " +
                    "dest_state_abr, dep_time, arr_time, actual_elapsed_time, air_time, distance) VALUES " +
                    "(${flightDataSet.id}, ${flightDataSet.year}, ${flightDataSet.dayOfMonth}, ${flightDataSet.flDate}, " +
                    "${flightDataSet.airlineId}, '${flightDataSet.carrier}', ${flightDataSet.flNum}, " +
                    "${flightDataSet.originAirportId}, '${flightDataSet.origin}', '${flightDataSet.originCityName}', " +
                    "'${flightDataSet.originStateAbr}', '${flightDataSet.dest}', '${flightDataSet.destCityName}', " +
                    "'${flightDataSet.destStateAbr}', ${flightDataSet.depTime}, ${flightDataSet.arrTime}, ${flightDataSet.actualElapsedTime}, " +
                    "${flightDataSet.airTime}, ${flightDataSet.distance});"

            println "Excuting query: $query"
            session.execute(query)


            /*PreparedStatement statement = session.prepare(
                    "INSERT INTO cassandra_flight.flights (id, year, day_of_month, fl_date, airline_id, carrier, fl_num, " +
                            "origin_airport_id, origin, origin_city_name, origin_state_abr, dest, dest_city_name, " +
                            "dest_state_abr, dep_time, arr_time, actual_elapsed_time, air_time, distance) VALUES " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            BoundStatement boundStatement = new BoundStatement(statement);

            println "Inserting data row $flightDataSet"

            session.execute( boundStatement.bind(
                    flightDataSet.id, flightDataSet.year, flightDataSet.dayOfMonth, flightDataSet.flDate, flightDataSet.airlineId,
                    flightDataSet.carrier, flightDataSet.flNumber, flightDataSet.originAirportId, flightDataSet.origin,
                    flightDataSet.originCityName, flightDataSet.originStateAbr, flightDataSet.dest, flightDataSet.destCityName,
                    flightDataSet.destStateAbr, flightDataSet.depTime, flightDataSet.arrTime, flightDataSet.actualElapsedTime,
                    flightDataSet.airTime, flightDataSet.distance)
            );*/
        }
    }

    def close() {
        cluster.shutdown();
    }

    public static void main(String[] args) {
        CassandraCreateFlights client = new CassandraCreateFlights();
        client.connect("127.0.0.1")
        client.createSchema();
        client.loadDataUsingBoundStatements()
        //cs.updateSchema();
        //client.dropSchema("csflight");
        client.close();
    }

}
