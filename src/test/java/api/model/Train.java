package api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Train {
    @JsonProperty("departure_station")
    private Station departureStation;

    @JsonProperty("arrival_station")
    private Station arrivalStation;

    @JsonProperty("departure_datetime")
    private String departureDatetime;

    public Station getDepartureStation() { return departureStation; }
    public Station getArrivalStation() { return arrivalStation; }
    public String getDepartureDatetime() { return departureDatetime; }
}
