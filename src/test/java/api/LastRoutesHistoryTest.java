package api;

import api.model.SearchHistoryItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LastRoutesHistoryTest {
    private static final String apiKey = "4ae3369b0952f1c1176deec94708f3a7";

    private final String requestBody = """
        {
          "passengers": { "adults": 1, "children": 0, "children_age": [] },
          "legs": {
            "1": {
              "departure_station": "23e9ca21-c51d-41be-b421-94e2da736ce3",
              "arrival_station": "8fbfe521-8d0c-4187-9076-ad1731b42ae9",
              "departure_date": "2025-11-05"
            }
          }
        }
        """;

    @Test
    public void lastRoutesHistoryWithModifiedRoute() throws Exception {

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("X-currency", "USD")
                .header("X-API-User-Key", apiKey)
                .body(requestBody)
                .when()
                .post("https://back.rail.ninja/api/v2/timetable")
                .then()
                .extract()
                .response();

        Map<String, String> cookies = response.getCookies();
        String searchHistoryEncoded = cookies.get("search_history");

        if (searchHistoryEncoded == null) {
            return;
        }

        String urlDecoded = java.net.URLDecoder.decode(searchHistoryEncoded, StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(urlDecoded);
        String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        List<SearchHistoryItem> historyList = mapper.readValue(decodedJson, new TypeReference<>() {});


        SearchHistoryItem first = historyList.get(0);
        first.legs.get("1").departureStation = "1036";
        first.legs.get("1").arrivalStation = "1037";

        String updatedJson = mapper.writeValueAsString(historyList);
        String updatedBase64 = Base64.getEncoder().encodeToString(updatedJson.getBytes(StandardCharsets.UTF_8));
        String urlEncodedCookie = java.net.URLEncoder.encode(updatedBase64, StandardCharsets.UTF_8);


        Response getResponse = given()
                .cookie("search_history", urlEncodedCookie)
                .when()
                .get("https://back.rail.ninja/api/v1/station/history")
                .then()
                .extract()
                .response();

        assertEquals(200, getResponse.getStatusCode());
    }

    @Test
    public void lastRoutesHistoryWithModifiedDate() throws Exception {

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("X-currency", "USD")
                .header("X-API-User-Key", apiKey)
                .body(requestBody)
                .when()
                .post("https://back.rail.ninja/api/v2/timetable")
                .then()
                .extract()
                .response();

        Map<String, String> cookies = response.getCookies();
        String searchHistoryEncoded = cookies.get("search_history");

        if (searchHistoryEncoded == null) {
            return;
        }

        String urlDecoded = java.net.URLDecoder.decode(searchHistoryEncoded, StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(urlDecoded);
        String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        List<SearchHistoryItem> historyList = mapper.readValue(decodedJson, new TypeReference<>() {});


        SearchHistoryItem first = historyList.get(0);
        first.legs.get("1").departureDate = "08.12.2025";


        String updatedJson = mapper.writeValueAsString(historyList);
        String updatedBase64 = Base64.getEncoder().encodeToString(updatedJson.getBytes(StandardCharsets.UTF_8));
        String urlEncodedCookie = java.net.URLEncoder.encode(updatedBase64, StandardCharsets.UTF_8);


        Response getResponse = given()
                .cookie("search_history", urlEncodedCookie)
                .when()
                .get("https://back.rail.ninja/api/v1/station/history")
                .then()
                .extract()
                .response();
        assertEquals(200, getResponse.getStatusCode());
    }



    @Test
    public void lastRoutesHistoryWithInvalidData() throws Exception {

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("X-currency", "USD")
                .header("X-API-User-Key", apiKey)
                .body(requestBody)
                .when()
                .post("https://back.rail.ninja/api/v2/timetable")
                .then()
                .extract()
                .response();

        Map<String, String> cookies = response.getCookies();
        String searchHistoryEncoded = cookies.get("search_history");

        if (searchHistoryEncoded == null) return;

        String urlDecoded = java.net.URLDecoder.decode(searchHistoryEncoded, StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(urlDecoded);
        String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        List<SearchHistoryItem> historyList = mapper.readValue(decodedJson, new TypeReference<>() {
        });

        SearchHistoryItem first = historyList.get(0);
        first.legs.get("1").departureStation = "null";
        first.legs.get("1").arrivalStation = "null";
        first.legs.get("1").departureDate = "08.12.2000";

        String updatedJson = mapper.writeValueAsString(historyList);
        String updatedBase64 = Base64.getEncoder().encodeToString(updatedJson.getBytes(StandardCharsets.UTF_8));
        String urlEncodedCookie = java.net.URLEncoder.encode(updatedBase64, StandardCharsets.UTF_8);

        Response getResponse = given()
                .cookie("search_history", urlEncodedCookie)
                .when()
                .get("https://back.rail.ninja/api/v1/station/history")
                .then()
                .extract()
                .response();

        System.out.println("Status code: " + getResponse.getStatusCode());
        String body = getResponse.asString();
        System.out.println("Response body: " + body);
        assertEquals(200, getResponse.getStatusCode());
        assertTrue(body.contains("[]") || body.contains("error") || body.contains("message"),
                "Ожидается пустой результат или сообщение об ошибке для некорректных данных");
    }
    @Test
    public void lastRoutesHistoryReadOnlyCookie() throws Exception {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("X-currency", "USD")
                .header("X-API-User-Key", apiKey)
                .body(requestBody)
                .when()
                .post("https://back.rail.ninja/api/v2/timetable")
                .then()
                .extract()
                .response();

        Map<String, String> cookies = response.getCookies();
        String searchHistoryEncoded = cookies.get("search_history");
        if (searchHistoryEncoded == null) return;

        String urlDecoded = java.net.URLDecoder.decode(searchHistoryEncoded, StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(urlDecoded);
        String decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        List<SearchHistoryItem> historyList = mapper.readValue(decodedJson, new TypeReference<>() {});

        SearchHistoryItem first = historyList.get(0);
        first.legs.get("1").departureStation = "1036";
        first.legs.get("1").arrivalStation = "1037";

        String updatedJson = mapper.writeValueAsString(historyList);
        String updatedBase64 = Base64.getEncoder().encodeToString(updatedJson.getBytes(StandardCharsets.UTF_8));
        String urlEncodedCookie = java.net.URLEncoder.encode(updatedBase64, StandardCharsets.UTF_8);

        Response getResponse = given()
                .cookie("search_history", urlEncodedCookie)
                .when()
                .get("https://back.rail.ninja/api/v1/station/history")
                .then()
                .extract()
                .response();

        assertEquals(200, getResponse.getStatusCode());

        String returnedJson = getResponse.asString();
        assertTrue(returnedJson.contains("1036") && returnedJson.contains("1037"),
                "Ожидается, что read-only endpoint вернёт данные без изменений");
    }

}
