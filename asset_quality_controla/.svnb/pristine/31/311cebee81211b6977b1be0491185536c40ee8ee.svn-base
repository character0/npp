package clquality
class TransferManager {

	String processor
	Integer activeThreadCount = 0
	Integer maxThreadCount = 1
	String state

    static constraints = {
        processor(unique:true, nullable:false)
        activeThreadCount(nullable:false)
        maxThreadCount(nullable:false)
        state(nullable:false)
    }

    static mapping = {
		version false;
	}
	
	// call as TransferManager.ALL
	// will return null if this hasn't been created yet
	static getALL() {
		def p = "ALL"
		return TransferManager.findByProcessor(p)
	}
	
	// call as TransferManager.CURRENT
	// will return null if this hasn't been created yet
	static getCURRENT() {
		// work around to get equivalent of grailsApplication.config in a service
		def config = org.codehaus.groovy.grails.commons.ConfigurationHolder.config
		def p = config?.qc?.id
		if (!p) return null
		
		return TransferManager.findByProcessor(p)
	}
}