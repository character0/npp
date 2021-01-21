package groovy.http

public class RestResponse {
	int responseCode = 0
	String responseText = null
	
	public RestResponse() { }
	
	public RestResponse(int responseCode, String responseText) {
		this.responseCode = responseCode
		this.responseText = responseText
	}
	
	public String toString() {
		return "RestResponse(responseCode: ${responseCode}; responseText: ${responseText})"
	}
}