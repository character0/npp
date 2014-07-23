import com.datastax.driver.core.Session
import com.datastax.driver.core.exceptions.AlreadyExistsException
import groovy.constants.SettingNames
import groovy.db.CassandraQueries
import groovy.db.SimpleClient

class BootStrap {

    def grailsApplication

    def cassandraService
    def settingService
    def ingestService
    def solrService
    def feedService
    def consumerService
    def publisherService

    def init = { servletContext ->

        log.info "********************* Initializing Application... *********************"

        // App setup
        settingService.addBooleanSetting(SettingNames.PUBLISH_TO_MQ, true)
        settingService.addBooleanSetting(SettingNames.INSERT_INTO_CASSANDRA, false)

        settingService.addStringSetting(SettingNames.RECEIVER_DROP_FOLDER_BASE_DIR, ".")
        //settingService.addStringSetting(SettingNames.FILE_ARCHIVE_EXTRACT_DIR, ".")

        settingService.getIntegerSystemSetting(SettingNames.MAX_RETRIES_PER_POLL, 5)
        settingService.getIntegerSystemSetting(SettingNames.MAX_FAILED_POLLS_BEFORE_ERROR_STATE, 4)
        settingService.getIntegerSystemSetting(SettingNames.RETRY_SLEEP_INTERVAL_IN_MINUTES, 1)

        ingestService.createIngestSource("CSV Slurper", "data_drop", "CSV stock data ingest", null, null, 1)
        /*feedService.createIngestSource("Stock News Feed",
                        "http://www.prnewswire.com/rss/financial-services/all-financial-services-news.rss",
                        "Financial Stock News Feed", null, null, 1)*/

        environments {
            development {

                log.info "********************* Initializing Cassandra *********************"
                // C* setup
                Session session = SimpleClient.connect(grailsApplication.config.db.host)
                if (!session)
                    return false

                try {
                    // Cassandra
                    SimpleClient.createKeyspace(grailsApplication.config.db.keyspace.name, grailsApplication.config.db.keyspace.strategy,
                            grailsApplication.config.db.keyspace.replication)
                } catch (AlreadyExistsException aee) {
                    log.info "Keyspace already exists."
                }

                //cassandraService.executeQuery("use ${grailsApplication.config.db.keyspace.name}")

                try {
                    cassandraService.executeQuery(CassandraQueries.TRADES_BY_PRICE_CF)
                } catch (AlreadyExistsException aee) {
                    log.info "${CassandraQueries.TRADES_BY_PRICE_CF_NAME} already exists."
                }

                /* TODO
                    CassandraQueries.All_TABLE_DEFINITIONS.each { table_definition ->
                }*/

                def reload = false
                try {
                    cassandraService.executeQuery(CassandraQueries.STOCK_NEWS_CF)
                } catch (AlreadyExistsException aee) {
                    log.info "${CassandraQueries.STOCK_NEWS_CF_NAME} already exists."
                    reload = true
                } finally {
                    solrService.manageSolrCore(new File("./src/groovy/search/solrconfig.xml"),
                            new File("./src/groovy/search/bootcamp.xml"),
                            CassandraQueries.STOCK_NEWS_CF_NAME, reload)
                    def stopWordsFile = new File("./src/groovy/search/stopwords.txt")
                    solrService.solrPostHelper(stopWordsFile.name, CassandraQueries.STOCK_NEWS_CF_NAME, stopWordsFile.text)
                    def synonymsFile = new File("./src/groovy/search/synonyms.txt")
                    solrService.solrPostHelper(synonymsFile.name, CassandraQueries.STOCK_NEWS_CF_NAME, synonymsFile.text)
                }

                try {
                    cassandraService.executeQuery(CassandraQueries.REVIEWS_DATA_CF)
                } catch (AlreadyExistsException aee) {
                    log.info "${CassandraQueries.REVIEWS_DATA_CF_NAME} already exists."
                    reload = true
                } finally {
                    solrService.manageSolrCore(new File("./src/groovy/search/solrconfig.xml"),
                            new File("./src/groovy/search/lab.xml"),
                            CassandraQueries.REVIEWS_DATA_CF_NAME, reload)
                }
                    //curl http://localhost:8983/solr/resource/bootcamp.stock_news/solrconfig.xml --data-binary @solrconfig.xml -H 'Content-type:text/xml; charset=utf-8'
                    //curl http://localhost:8983/solr/resource/bootcamp.stock_news/schema.xml --data-binary @schema.xml -H 'Content-type:text/xml; charset=utf-8'
                    //curl "http://localhost:8983/solr/admin/cores?action=CREATE&name=bootcamp.stock_news"
                    //curl http://localhost:8983/solr/resource/bootcamp.stock_news/synonyms.txt  --data-binary @synonyms.txt -H 'Content-type:text/plain'
                    //curl http://localhost:8983/solr/resource/bootcamp.stock_news/stopwords.txt --data-binary @stopwords.txt -H 'Content-type:text/plain'
                    //curl "http://localhost:8983/solr/admin/cores?action=RELOAD&name=bootcamp.stock_news&reindex=true&deleteAll=true"
            }
        }

        log.info "********************* Finished Initializing Application *********************"
    }

    def destroy = {
        log.info "********************* Destroying Application *********************"
        SimpleClient.dropKeyspace(grailsApplication.config.db.keyspace.name)
        SimpleClient?.close()
    }


}
