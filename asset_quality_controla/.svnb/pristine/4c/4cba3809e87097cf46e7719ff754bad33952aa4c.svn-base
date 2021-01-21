package clquality

import com.clearleap.core.util.UpdateUtil
import clquality.Content

class ContentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [contentInstanceList: Content.list(params), contentInstanceTotal: Content.count()]
    }

    def create = {
        def contentInstance = new Content()
        contentInstance.properties = params
        return [contentInstance: contentInstance]
    }

    def save = {
        def contentInstance = new Content(params)
        if (contentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'content.label', default: 'Content'), contentInstance.id])}"
            redirect(action: "show", id: contentInstance.id)
        }
        else {
            render(view: "create", model: [contentInstance: contentInstance])
        }
    }

    def show = {
        def contentInstance = Content.get(params.id)
        if (!contentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
        else {
            [contentInstance: contentInstance]
        }
    }

    def edit = {
        def contentInstance = Content.get(params.id)
        if (!contentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [contentInstance: contentInstance]
        }
    }

    def update = {
        def contentInstance = Content.get(params.id)
        if (contentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (contentInstance.version > version) {
                    
                    contentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'content.label', default: 'Content')] as Object[], "Another user has updated this Content while you were editing")
                    render(view: "edit", model: [contentInstance: contentInstance])
                    return
                }
            }
            contentInstance.properties = params
            if (!contentInstance.hasErrors() && contentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'content.label', default: 'Content'), contentInstance.id])}"
                redirect(action: "show", id: contentInstance.id)
            }
            else {
                render(view: "edit", model: [contentInstance: contentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def contentInstance = Content.get(params.id)
        if (contentInstance) {
            try {
                contentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
    }

    def pass = {
        def contentInstance = Content.get(params.id)
        if (!contentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
        else {
            UpdateUtil.safeUpdateObject(contentInstance) {
                contentInstance.qcDate = new Date()
                contentInstance.qcResult = QCResultStates.PASSED
            }
            render(view: "show", model: [contentInstance: contentInstance])
        }

    }

    def fail = {
        def contentInstance = Content.get(params.id)
        if (!contentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'content.label', default: 'Content'), params.id])}"
            redirect(action: "list")
        }
        else {
            UpdateUtil.safeUpdateObject(contentInstance) {
                contentInstance.qcDate = new Date()
                contentInstance.qcResult = QCResultStates.FAILED
            }
            render(view: "show", model: [contentInstance: contentInstance])
        }

    }

    def showManzanitaXml = {

        def content = Content.get(params.id)
        def manzanitaText = content.manzanitaReport
        if (manzanitaText != "") {
            response.setCharacterEncoding("UTF-8")
            response.setContentType("text/xml")
            render manzanitaText
        } else {
            log.info "There was a problem getting the manzanitaText to display.  Displaying a message to inform the user."
            render 'There was a problem retrieving this file.'
        }
    }

    def showDataServiceXml = {

        def content = Content.get(params.id)
        def dataServiceText = content.dataServiceXml
        if (dataServiceText != "") {
            response.setCharacterEncoding("UTF-8")
            response.setContentType("text/xml")
            render dataServiceText
        } else {
            log.info "There was a problem getting the dataServiceText to display.  Displaying a message to inform the user."
            render 'There was a problem retrieving this file.'
        }
    }
}
