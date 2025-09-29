package api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {
    @JsonProperty("single_name")
    private String singleName;

    public String getSingleName() { return singleName; }
}
