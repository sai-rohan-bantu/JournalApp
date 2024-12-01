package com.bantu.springDemo.Services;

import com.bantu.springDemo.ApiResponse.WeatherResponse;
import com.bantu.springDemo.Cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String apiTemplate = appCache.AppCachee.get("weather_app");

        if (apiTemplate == null) {
            throw new IllegalStateException("The API template is missing in the cache for key: weather_app");
        }
        String finalApi = apiTemplate.replace("<city>", city).replace("<apikey>", apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                finalApi,
                HttpMethod.GET,
                null,
                WeatherResponse.class
        );

        return response.getBody();
    }


//    public WeatherResponse getWeather(String city){
//        String finalApi=appCache.AppCachee.get("weather_api").replace("<city>",city).replace("<apikey>",apiKey);
//        System.out.println(finalApi);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
//         WeatherResponse body= response.getBody();
//         return body;
//    }

}
