package groovy.state;

/**
 * User: jtanner
 * Date: Apr 19, 2009
 */
public enum IngestSourceState
{
  OFF("Off"), ON("On"), PROCESSING("Processing"), ERROR("Error"), DELETED("Deleted");

  private String display;
	
  IngestSourceState(String display) {
	  this.display = display;	
  }
	
  public String getDisplayString() {
	  return display;
  }
  
  public static IngestSourceState fromString(String state) {
    if(state.equalsIgnoreCase("On")) return ON;
    if(state.equalsIgnoreCase("Off")) return OFF;
    if(state.equalsIgnoreCase("Processing")) return PROCESSING;
    if(state.equalsIgnoreCase("Deleted")) return DELETED;

    return ERROR;
  }
  
  

}
