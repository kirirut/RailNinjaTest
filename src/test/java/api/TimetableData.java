package api;

import api.Utils.Train;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class TimetableData {

    private List<Train> trains;
    private int statusCode;
    private String contentType;

    private final String requestBody = """
        {
          "passengers": { "adults": 1, "children": 0, "children_age": [] },
          "legs": {
            "1": {
              "departure_station": "23e9ca21-c51d-41be-b421-94e2da736ce3",
              "arrival_station": "8fbfe521-8d0c-4187-9076-ad1731b42ae9",
              "departure_date": "05.11.2025"
            }
          }
        }
        """;

    public TimetableData(String apiKey) throws Exception {
        String endpoint = "https://back.rail.ninja/api/v2/timetable";

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("X-currency", "USD")
                .header("X-API-User-Key", apiKey)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        this.statusCode = response.getStatusCode();
        this.contentType = response.getContentType();

        // Логируем для отладки
        System.out.println("Status code: " + statusCode);
        System.out.println("Content type: " + contentType);
        System.out.println("Body: " + response.asString());

        // Проверяем, что ответ JSON и содержит "trains"
        if (statusCode != 200 || !contentType.contains("application/json")) {
            throw new RuntimeException("Ответ не JSON! Status: " + statusCode);
        }

        Map<String, Object> trainsMap = response.jsonPath().getMap("trains");
        if (trainsMap == null) {
            throw new RuntimeException("В ответе нет ключа 'trains'. Тело ответа: " + response.asString());
        }

        ObjectMapper mapper = new ObjectMapper();
        this.trains = trainsMap.values().stream()
                .map(obj -> mapper.convertValue(obj, Train.class))
                .collect(Collectors.toList());
    }

    public List<Train> getTrains() { return trains; }
    public int getStatusCode() { return statusCode; }
    public String getContentType() { return contentType; }

    // Вспомогательный метод для проверки маршрута
    public boolean validateRoute(String departure, String arrival, String date) {
        return trains.stream().anyMatch(train ->
                train.getDepartureStation().getSingleName().equals(departure) &&
                        train.getArrivalStation().getSingleName().equals(arrival) &&
                        train.getDepartureDatetime().startsWith(date)
        );
    }
}
