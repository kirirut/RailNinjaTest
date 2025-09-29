package api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class SearchHistoryItem {
    public Passengers passengers;

    @JsonProperty("form-mode")
    public String formMode;

    public Map<String, Leg> legs;

    public static class Passengers {
        public int adults;
        public int children;

        @JsonProperty("children_age")
        public List<Integer> childrenAge;
    }

    public static class Leg {
        @JsonProperty("departure_station")
        public String departureStation;

        @JsonProperty("arrival_station")
        public String arrivalStation;

        @JsonProperty("departure_date")
        public String departureDate;
    }
}
