package clquality

import clquality.Asset
import clquality.AssetService

class AssetController {

    static scaffold = true

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    AssetService assetService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = params.max ? params.int('max') : 100
        [assetInstanceList: Asset.list(params), assetInstanceTotal: Asset.count()]
    }

    def create = {
        def assetInstance = new Asset()
        assetInstance.properties = params
        return [assetInstance: assetInstance]
    }

    def save = {
        def assetInstance = new Asset(params)
        if (assetInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
            redirect(action: "show", id: assetInstance.id)
        }
        else {
            render(view: "create", model: [assetInstance: assetInstance])
        }
    }

    def show = {
        def assetInstance = Asset.get(params.id)
        if (!assetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        }
        else {
            [assetInstance: assetInstance, sortedContentsByType: assetInstance.contents.sort { it.contentType } ]
        }
    }

    def edit = {
        def assetInstance = Asset.get(params.id)
        if (!assetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [assetInstance: assetInstance]
        }
    }

    def update = {
        def assetInstance = Asset.get(params.id)
        if (assetInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (assetInstance.version > version) {

                    assetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'asset.label', default: 'Asset')] as Object[], "Another user has updated this Asset while you were editing")
                    render(view: "edit", model: [assetInstance: assetInstance])
                    return
                }
            }
            assetInstance.properties = params
            if (!assetInstance.hasErrors() && assetInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
                redirect(action: "show", id: assetInstance.id)
            }
            else {
                render(view: "edit", model: [assetInstance: assetInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def assetInstance = Asset.get(params.id)
        if (assetInstance) {
            try {
                assetInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        }
    }

    def approve = {
        def assetInstance = Asset.get(params.id)
        if (!assetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        }
        else {
            assetService.approveAsset(assetInstance)
            render(view: "show", model: [assetInstance: assetInstance, sortedContentsByType: assetInstance.contents.sort { it.contentType } ])
        }

    }

    def reject = {
        def assetInstance = Asset.get(params.id)
        if (!assetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        }
        else {
            assetService.rejectAsset(assetInstance)
            render(view: "show", model: [assetInstance: assetInstance, sortedContentsByType: assetInstance.contents.sort { it.contentType } ])
        }

    }

    /**
     * Re pulls all contents for the Asset to be re-QCed.
     */
    def reQC = {
        def assetInstance = Asset.get(params.id)
        if (!assetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        } else {
            assetService.qcAllContent(assetInstance)
            render(view: "show", model: [assetInstance: assetInstance, sortedContentsByType: assetInstance.contents.sort { it.contentType } ])
        }

    }

    /**
     * For any new contents that does not already exist on the asset, pull it in for QC.
     */
    def qcNew = {
        def assetInstance = Asset.get(params.id)
        if (!assetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
            redirect(action: "list")
        } else {
            assetService.qcNewContents(assetInstance)
            render(view: "show", model: [assetInstance: assetInstance, sortedContentsByType: assetInstance.contents.sort { it.contentType } ])
        }

    }

    /**
     * VOD display as scheduled XML
     */
    def showAdiXml = {

        //Hack: IE wants to read the ADI.DTD while rendering the ADI.xml as a raw XML file. No security needed for this use case.
        if (params?.id == "ADI.DTD") {
            log.debug("Receiving request for ADI.DTD. Should be from IE")
            redirect(url: resource(dir: 'dtd', file: 'ADI.DTD'))
            return
        }

        def asset = Asset.get(params.id)
        def adiText = asset.adixml
        if (adiText != "") {
            response.setCharacterEncoding("UTF-8")
            response.setContentType("text/xml")
            render adiText
        } else {
            log.info "There was a problem getting the ADI.XML to display.  Displaying a message to inform the user."
            render 'There was a problem retrieving this file.'
        }
    }

    def asset_find_asset_id = {

        def assetInstance = Asset.findByCentralId(params.assetid)
        if (!assetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.assetid])}"
            redirect(action: "list")
        }
        else {
            render(view: "show", model: [assetInstance: assetInstance, sortedContentsByType: assetInstance.contents.sort { it.contentType } ])
        }

    }

}
