package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class RailNinjaTimetableApiTest {

    private static final Logger log = LoggerFactory.getLogger(RailNinjaTimetableApiTest.class);
    private static final String BASE_URL = "https://back.rail.ninja/api/v2/timetable";
    private static final String API_KEY = "4ae3369b0952f1c1176deec94708f3a7";

    private String requestBody = """
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

    @Test
    void testTimetableApiReturnsCorrectTrains() {
        Response response=given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("X-currency", "USD")
                .header("X-API-User-Key",API_KEY)
                .body(requestBody)
                .when()
                .post(BASE_URL);

        int statusCode = response.getStatusCode();
        System.out.println("HTTP статус: " + statusCode);

        String contentType = response.getHeader("Content-Type");
        System.out.println("Заголовок Content-Type: " + contentType);






    }
}