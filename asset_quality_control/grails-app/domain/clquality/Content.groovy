package clquality

class Content {


    Integer runtime
    Integer manzanitaRuntime

    Boolean original
    Boolean qcComplete

    Long size
    Long centralId

    String dataServiceXml
    String audioType
    String profile
    String name
    String uri
    String contentType
    String checksum
    String manzanitaReport
    String manzanitaResult
    String manzanitaVChip
    String manzanitaCGMS
    String contentState
    String qcResult

    Date qcDate
    Date stagedDate

    Asset asset

    static belongsTo = [Asset]

    static constraints = {
        audioType(nullable:true)
        dataServiceXml(nullable:true)
        manzanitaReport(nullable:true)
        manzanitaResult(nullable:true)
        manzanitaVChip(nullable:true)
        manzanitaCGMS(nullable:true)
        profile(nullable:true)
        name(nullable:true)
        uri(nullable:true)
        contentType(nullable:true)
        checksum(nullable:true)
        contentState(nullable:true)
        qcResult(nullable:true)

        centralId(nullable:true)
        runtime(nullable:true)
        manzanitaRuntime(nullable:true)

        qcDate(nullable:true)
        stagedDate(nullable:true)

        original(nullable:true)

        size(nullable:true)
    }

	static mapping = {
		version false;
	}


}