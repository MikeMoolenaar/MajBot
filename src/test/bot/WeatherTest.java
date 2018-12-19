package bot;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author Mike Moolenaar
 */
public class WeatherTest {

    /**
     * Condition names from the Yahoo weather API
     * Source: https://developer.yahoo.com/weather/documentation.html#codetable
     * Added 'rain' even though it isn't in the documentation but could be given by the API.
     */
    private String[] allowedResponses = {
            "tornado",
            "tropical storm",
            "hurricane",
            "severe thunderstorms",
            "thunderstorms",
            "mixed rain and snow",
            "rain",
            "mixed rain and sleet",
            "mixed snow and sleet",
            "freezing drizzle",
            "drizzle",
            "freezing rain",
            "showers",
            "showers",
            "snow flurries",
            "light snow showers",
            "blowing snow",
            "snow",
            "hail",
            "sleet",
            "dust",
            "foggy",
            "haze",
            "smoky",
            "blustery",
            "windy",
            "cold",
            "cloudy",
            "mostly cloudy",
            "partly cloudy",
            "clear",
            "sunny",
            "fair",
            "mixed rain and hail",
            "hot",
            "isolated thunderstorms",
            "scattered thunderstorms",
            "scattered showers",
            "heavy snow",
            "scattered snow showers",
            "heavy snow",
            "partly cloudy",
            "thundershowers",
            "snow showers",
            "isolated thundershowers",
    };

    private static Weather weather;

    @BeforeClass
    public static void setUpWeather() {
        weather = new Weather();
    }

    @Test
    public void getResponse() {
        String day = "today";
        String tommorow = "tomorrow";
        String dayaftertomorrow = "dayaftertomorrow";

        var responseToday = weather.getResponse(day);
        var responseTommorow = weather.getResponse(tommorow);
        var responseDayaftertommorow = weather.getResponse(dayaftertomorrow);

        assertThat(responseToday, containsString("I think today is "));
        assertThat(isInAllowedResponses(responseToday), is(true));
        assertThat(responseTommorow, containsString("I guess tomorrow will be "));
        assertThat(isInAllowedResponses(responseTommorow), is(true));
        assertThat(responseDayaftertommorow, containsString("Day after tomorrow should be "));
        assertThat(isInAllowedResponses(responseDayaftertommorow), is(true));
    }

    private boolean isInAllowedResponses(String element) {
        return Arrays.stream(allowedResponses)
                .anyMatch(allowedResponse -> element.toLowerCase().contains(allowedResponse.toLowerCase()));
    }
}