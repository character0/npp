package clquality

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.clearleap.core.asset.ContentType
import states.AssetStates
import states.ManzanitaResultStates

import util.DateFormatter

class DashController {

    AssetService assetService
    def exportService
    def transferService

    def index = {

        def map = getDashData()

        render(view: "/index",
                model: [contentCount: map.contentCount,
                        assetCount: map.assetCount,
                        createdAssetsCount: map.createdAssetsCount,
                        processingAssetsCount: map.processingAssetsCount,
                        stagedAssetsCount: map.stagedAssetsCount,
                        synchedAssetsCount: map.synchedAssetsCount,
                        synchingAssetsCount: map.synchingAssetsCount,
                        synchErrorAssetsCount: map.synchErrorAssetsCount,
                        approvedAssetsCount: map.approvedAssetsCount,
                        rejectedAssetsCount: map.rejectedAssetsCount,
                        contentsWithManzanitaFailuresCount: map.contentsWithManzanitaFailuresCount,
                        contentWithoutNielsenXmlCount: map.contentWithoutNielsenXmlCount])
    }


    def exportStagedAssets = {

        def assetsToExport = assetService.exportStagedAssets()

        // Set up the export doc
        String docTitle = "QC Staged Asset Report"
        def format = ""
        def extension = ""

        List columnWidths = [0.3/**/, 0.3/**/, 0.3/**/, 0.3/**/, 0.3/**/,  0.3, 0.3]
        List fields = ["title", "centralId", "mxpState", "id", "createDate", "assetState", "startDate"]
        Map labels = ["centralId":"MXP ID", "title":"Asset Name", "id":"QC ID", "createDate":"QC Creation Date",
                "assetState":"QC state", "startDate":"Start Date", "mxpState":"MXP State"]
        Map parameters = [title: docTitle, "column.widths": columnWidths]

        def formatDate = { domain, value ->
            return DateFormatter.DATE_AND_TIME.format(value)
        }

        Map formatters = ["createDate":formatDate, "startDate":formatDate]

        def exportFormat = params.exportFormat
        if (exportFormat == "pdf") {
            format = "pdf"
            extension = "pdf"
        }
        else if (exportFormat == "csv") {
            format = "csv"
            extension = "csv"
            parameters.separator = ","
        }
        else if (exportFormat == "excel") {
            format = "excel"
            extension = "xls"
        }
        else {
            flash.message = "NO"
            redirect(controller: 'dash', action: 'index')
            return
        }

        String dateStr = DateFormatter.DEFAULT_NO_TIME.format(new Date())
        String filename = "QC_Staged_Asset_Report-${dateStr}.${extension}"

        // Set headers
        response.contentType = ConfigurationHolder.config.grails.mime.types[format]
        response.setHeader("Content-disposition", "attachment; filename=${filename}")

        /* By default, Tomcat will set headers on any SSL content to deny
          * caching. This will cause downloads to Internet Explorer to fail. So
          * we override Tomcat's default behavior here. */
        response.setHeader("Pragma", "")
        response.setHeader("Cache-Control", "private")
        Calendar cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE,5)
        response.setDateHeader("Expires", cal.getTimeInMillis())

        try {
            exportService.export(format, response.outputStream, assetsToExport, fields, labels, formatters, parameters)
        }
        catch (Exception e) {
            log.error "Exception while trying to export staged assets report", e
            flash.message = "NO"
            redirect(action:'list')
            return
        }

        return
    }


    def executeJob = {
        log.info "job to execute: $params.id"
        if (params.id == "createAssets") {
            flash.message = "assetService.findNewAsset() executed."
            log.info "Manually executing Asset create"
            assetService.findNewAsset()
        } else if (params.id == "stageAssets") {
            flash.message = "assetService.processAssets() executed."
            log.info "Manually executing Asset stage"
            assetService.processAssets()
        } else if (params.id == "deleteAssets") {
            flash.message = "assetService.cleanupQCedContent() executed."
            log.info "Manually executing Asset clean-up"
            assetService.cleanupQCedContent()
        } else if (params.id == "transferAssets") {
            flash.message = "transferService.processAssets() executed."
            log.info "Manually executing Asset transfer"
            transferService.processAssets()
        }
        redirect(view: "/index")

    }


    def jobs = {
        render(view: "jobs")
    }


    /**
     * Helper method that provides all of the Dash data for the admin login page.
     *
     * @return a Map of all of the Dash data
     */
    private Map getDashData() {

        def mapToRet = [:]

        def createdAssetsCount = Asset.findAllByAssetState(AssetStates.CREATED)
        log.info "Found ${createdAssetsCount.size()} for createdAssetsCount"

        def stagedAssetsCount = Asset.findAllByAssetState(AssetStates.STAGED)
        log.info "Found ${stagedAssetsCount.size()} for stagedAssetsCount"

        def approvedAssetsCount = Asset.findAllByAssetState(AssetStates.APPROVED)
        log.info "Found ${approvedAssetsCount.size()} for approvedAssetsCount"

        def rejectedAssetsCount = Asset.findAllByAssetState(AssetStates.REJECTED)
        log.info "Found ${rejectedAssetsCount.size()} for rejectedAssetsCount"

        def processingAssetsCount = Asset.findAllByAssetState(AssetStates.PROCESSING)
        log.info "Found ${processingAssetsCount.size()} for processingAssetsCount"

        def synchedAssetsCount = Asset.findAllByAssetState(AssetStates.SYNCHED)
        log.info "Found ${synchedAssetsCount.size()} for synchedAssetsCount"

        def synchingAssetsCount = Asset.findAllByAssetState(AssetStates.SYNCHING)
        log.info "Found ${synchingAssetsCount.size()} for synchingAssetsCount"

        def synchErrorAssetsCount = Asset.findAllByAssetState(AssetStates.SYNCH_ERROR)
        log.info "Found ${synchErrorAssetsCount.size()} for synchErrorAssetsCount"

        def contentsWithManzanitaFailuresCount = Content.createCriteria().list() {
            eq("manzanitaResult", ManzanitaResultStates.stringValue(2))
            eq("contentType", ContentType.PRIMARY.toString())
        }
        log.info "Found ${contentsWithManzanitaFailuresCount.size()} for contentsWithManzanitaFailuresCount"

        return [assetCount: 0,
                contentCount: 0,
                createdAssetsCount: createdAssetsCount.size(),
                processingAssetsCount: 1,
                stagedAssetsCount: stagedAssetsCount.size(),
                approvedAssetsCount: approvedAssetsCount.size(),
                rejectedAssetsCount: rejectedAssetsCount.size(),
                processingAssetsCount: processingAssetsCount.size(),
                synchedAssetsCount: synchedAssetsCount.size(),
                synchingAssetsCount: synchingAssetsCount.size(),
                synchErrorAssetsCount: synchErrorAssetsCount.size(),
                contentsWithManzanitaFailuresCount: contentsWithManzanitaFailuresCount.size(),
                contentWithoutNielsenXmlCount: 0]
    }
}
