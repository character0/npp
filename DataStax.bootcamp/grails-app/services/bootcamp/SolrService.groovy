package bootcamp

import grails.transaction.Transactional

import groovy.http.RestResponse
import org.apache.http.HttpStatus

@Transactional
class SolrService {

    static transactional = false

    def grailsApplication

    /**
     *
     * @param solrConfigurationFile
     * @param schemaFile
     * @param columnFamily
     * @param reload
     * @return
     * @throws Exception
     */
    def manageSolrCore(File solrConfigurationFile, File schemaFile, String columnFamily, boolean reload = false) throws Exception {

        def keyspace_columnfamily = "${grailsApplication.config.db.keyspace.name}.${columnFamily}"
        def baseURI = "${grailsApplication.config.solr.protocol}://" +
                "${grailsApplication.config.solr.host}:${grailsApplication.config.solr.port}/" +
                "${grailsApplication.config.solr.app_name}"

        def successfulCall = false
        // Post the solr configuration first.

        if (solrConfigurationFile.exists()) {
            log.info "manageSolrCore -> Found solr configuration xml $solrConfigurationFile.name"
            successfulCall = solrPostHelper("solrconfig.xml", columnFamily, solrConfigurationFile.text)
        }
        // Next the schema
        if (successfulCall && schemaFile.exists()) {
            log.info "manageSolrCore -> Found schema configuration xml $schemaFile.name"
            // http://localhost:8983/solr/resource/${keyspace_columnfamily}/schema.xml --data-binary @schema.xml -H 'Content-type:text/xml; charset=utf-8'
            successfulCall = solrPostHelper("schema.xml", columnFamily, schemaFile.text)
        } else {
            successfulCall = false
        }
        // Finally create the core
        if (successfulCall) {
            // curl "http://localhost:8983/solr/admin/cores?action=CREATE&name=news_solr_ks.solr_text"
            def uri = "$baseURI/admin/cores?action=${reload ? "RELOAD" : "CREATE"}&name=${keyspace_columnfamily}"
            log.info "manageSolrCore -> Making core creation post to Solr @ ${uri}"
            RestResponse restResponse = RestUtil.doPost(uri)
            int responseCode = restResponse.responseCode
            switch (responseCode) {
                case HttpStatus.SC_BAD_REQUEST:
                case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                    def errorMsg = "Solr server responded with code $responseCode"
                    log.warn "manageSolrCore -> $errorMsg"
                    throw new Exception(errorMsg)
                    break
                case HttpStatus.SC_OK:
                default:
                    log.info "Solr server responded with code $responseCode"
                    return successfulCall
                    break
            }
        }

        return successfulCall
    }

    /**
     *
     * @param destinationUri
     * @param columnFamily
     * @param xml
     * @return
     * @throws Exception
     */
    def solrPostHelper(destinationUri, columnFamily, xml) throws Exception {

        def baseURI = "${grailsApplication.config.solr.protocol}://" +
                "${grailsApplication.config.solr.host}:${grailsApplication.config.solr.port}/" +
                "${grailsApplication.config.solr.app_name}"

        def uri = "${baseURI}/resource/${grailsApplication.config.db.keyspace.name}.${columnFamily}/${destinationUri}"
        log.info "solrPostHelper -> Making post to Solr @ ${uri}"
        RestResponse restResponse = RestUtil.doPost(uri, "application/xml", "UTF-8", xml)
        log.info "solrPostHelper -> post returned status code: ${restResponse.responseCode}"
        return (restResponse.responseCode == HttpStatus.SC_OK)
    }
}
