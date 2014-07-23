package clquality
class Asset {

    String name
    String mediaId
    String assetId
    String assetState
    String stagedDirectoryName
    String rating
    String advisory
    String qcType
    String qcLevel
    String title
    String shortTitle
    String episode
    String adixml
    String description

    Date createDate
    Date startDate
    Date endDate
    Date cleanupDate
    Date stagedDate
	Date transferStartDate //date the first content transfer for SFTP was started

    Integer runtime
    Integer ingestSourceId

    Long centralId

    Boolean hasPreviews

    Set contents = [] as Set
	
	// QC processor id
	String processor

	static hasMany = [contents:Content]

    static constraints = {
        mediaId(unique:true)
        qcType(nullable:true)
        qcLevel(nullable:true)
        name(nullable:true)
        assetId(nullable:true)
        assetState(nullable:true)
        stagedDirectoryName(nullable:true)
        rating(nullable:true)
        advisory(nullable:true)
        qcType(nullable:true)
        title(nullable:true)
        shortTitle(nullable:true)
        episode(nullable:true)
        adixml(nullable:true)
        description(nullable:true)

        createDate(nullable:true)
        cleanupDate(nullable:true)
        stagedDate(nullable:true)
        transferStartDate(nullable:true)

        runtime(nullable:true)
        ingestSourceId(nullable:true)

        hasPreviews(nullable: true)
        processor(nullable: true)
    }

    static mapping = {
		version false;
	}
}