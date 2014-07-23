package groovy.constants;

/**
 * Date: Jun 1, 2009
 */
public enum IngestSourceType {
	FEED("RSS"), RECEIVER("FTP")
	
	private String display;

    IngestSourceType(String display) {
		this.display = display;	
	}
	
	public String getDisplayString() {
		return display;
	}
}
