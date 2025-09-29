package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableDataTest {

    private static final String API_KEY = "4ae3369b0952f1c1176deec94708f3a7";
    private static final String DEPARTURE = "Mecca";
    private static final String ARRIVAL = "Medina";
    private static final String DATE = "2025-11-05";

    @Test
    public void testTimetablePost() throws Exception {
        TimetableData data = new TimetableData(API_KEY);
        System.out.println("HTTP-код: " + data.getStatusCode());
        System.out.println("Content-Type: " + data.getContentType());
        assertEquals(200, data.getStatusCode(), "Статус код должен быть 200");
        assertTrue(data.getContentType().contains("application/json"), "Content-Type должен быть application/json");
        assertNotNull(data.getTrains(), "Список поездов не должен быть null");
        assertFalse(data.getTrains().isEmpty(), "Список поездов не должен быть пустым");
        data.getTrains().forEach(train -> {
            assertNotNull(train.getDepartureStation(), "DepartureStation не должен быть null");
            assertNotNull(train.getArrivalStation(), "ArrivalStation не должен быть null");
            assertNotNull(train.getDepartureDatetime(), "DepartureDatetime не должен быть null");
            System.out.println("Рейс:");
            System.out.println("  departure_station.single_name = " + train.getDepartureStation().getSingleName());
            System.out.println("  arrival_station.single_name   = " + train.getArrivalStation().getSingleName());
            System.out.println("  departure_date               = " + train.getDepartureDatetime().substring(0, 10));
            assertTrue(train.getDepartureStation().getSingleName().contains(DEPARTURE),
                    "Станция отправления не совпадает");
            assertTrue(train.getArrivalStation().getSingleName().contains(ARRIVAL),
                    "Станция прибытия не совпадает");
            assertTrue(train.getDepartureDatetime().startsWith(DATE),
                    "Дата отправления не совпадает");
        });
    }
}
