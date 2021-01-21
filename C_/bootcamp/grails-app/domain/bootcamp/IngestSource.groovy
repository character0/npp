package bootcamp

import groovy.constants.IngestSourceType
import groovy.state.IngestSourceState

public class IngestSource {

    Long id;
    String name;
    String description;
    String location;
    IngestSourceState state = IngestSourceState.OFF;
    int pollFrequencyInMinutes = 60;
    Date lastPolled;
    Date nextPoll = new Date();
    Integer retries = 0;
    IngestSourceType ingestSourceType

    String prefixesToIgnore = "in.*;";
    String suffixesToIgnore = "*.aspx;";

    int itemsToIngest = -1;

    /**
     * Central that is currently processing the Source. Used primarily for
     * resetting the state on restart.
     */
    String currentProcessor;

    static mapping = {
        version false
    }

    static constraints = {
        lastPolled (nullable:true)
        prefixesToIgnore (nullable:true)
        suffixesToIgnore (nullable:true)
    }

    /**
     * @return "name(id)".
     */
    public String toString() {
        return (name + "(id:" + id + "; state:" + state + ")");
    }
}

