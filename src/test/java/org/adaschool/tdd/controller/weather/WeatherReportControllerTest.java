package org.adaschool.tdd.controller.weather;

import org.adaschool.tdd.controller.weather.dto.NearByWeatherReportsQueryDto;
import org.adaschool.tdd.controller.weather.dto.WeatherReportDto;
import org.adaschool.tdd.mongoweatherservice.ConstantesData;
import org.adaschool.tdd.repository.document.GeoLocation;
import org.adaschool.tdd.repository.document.WeatherReport;
import org.adaschool.tdd.service.WeatherService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherReportControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private WeatherService weatherService;

    @Test
    void create_Test_Returns_Equals_Expected() {
        String uri = "http://localhost:" + port + "/v1/weather";
        WeatherReportDto request = ConstantesData.getRequestWeatherDto();
        WeatherReport expected = new WeatherReport(
                request.getGeoLocation(),
                request.getTemperature(),
                request.getHumidity(),
                request.getReporter(),
                request.getCreated()
        );
        HttpEntity<WeatherReportDto> body = new HttpEntity<>(request);

        ResponseEntity<WeatherReport> responseEntity = this.restTemplate.postForEntity(uri, body, WeatherReport.class);
        WeatherReport response = responseEntity.getBody();

        assertEquals(expected, response);

    }


    @Test
    void findById_Test_Returns_Equals_Expected() {
        String uri = "http://localhost:" + port +"/v1/weather/8fgsffsghghd5g5461g6fg5";
        WeatherReport expected = ConstantesData.getWeatherReport();
        when(weatherService.findById(anyString())).thenReturn(expected);
        WeatherReport response = this.restTemplate.getForObject(uri,WeatherReport.class);

        assertEquals(expected,response);
    }


    @Test
    void findNearByReports_Test_Returns_Data(){
        String uri = "http://localhost:" + port + "/v1/weather/8fgsffsghghd5g5461g6fg5";
        WeatherReport expected = ConstantesData.getWeatherReport();
        when(weatherService.findById(anyString())).thenReturn(expected);
        WeatherReport response = this.restTemplate.getForObject(uri,WeatherReport.class);

        System.out.println("Esperado: " + expected);
        System.out.println("Actual: " + response);

        assertEquals(expected, response);

    }

    @Test
    void findByReporterId_Test_Returns_Data(){
        String uri = "http://localhost:" + port +"/v1/weather/8fgsffsghghd5g5461g6fg5";
        WeatherReport expected = ConstantesData.getWeatherReport();
        when(weatherService.findById(anyString())).thenReturn(expected);
        WeatherReport response = this.restTemplate.getForObject(uri,WeatherReport.class);

        assertEquals(expected,response);
    }
}