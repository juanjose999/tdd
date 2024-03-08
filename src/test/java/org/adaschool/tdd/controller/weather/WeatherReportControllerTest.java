package org.adaschool.tdd.controller.weather;

import org.adaschool.tdd.controller.weather.dto.WeatherReportDto;
import org.adaschool.tdd.mongoweatherservice.fakeData.ConstantesData;
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

        // Create the expected WeatherReport
        WeatherReport expected = new WeatherReport(
                request.getGeoLocation(),
                request.getTemperature(),
                request.getHumidity(),
                request.getReporter(),
                request.getCreated()
        );

        // Mock the report method and return the expected WeatherReport
        when(weatherService.report(any())).thenReturn(expected);

        HttpEntity<WeatherReportDto> body = new HttpEntity<>(request);

        ResponseEntity<WeatherReport> responseEntity = this.restTemplate.postForEntity(uri, body, WeatherReport.class);
        WeatherReport response = responseEntity.getBody();

        // Compare individual fields or override equals method in WeatherReport class
        assertEquals(expected.getGeoLocation(), response.getGeoLocation());
        assertEquals(expected.getTemperature(), response.getTemperature());
        assertEquals(expected.getHumidity(), response.getHumidity());
        assertEquals(expected.getReporter(), response.getReporter());
        assertEquals(expected.getCreated(), response.getCreated());
    }



    @Test
    void findById_Test_Returns_Equals_Expected() {
        String uri = "http://localhost:" + port + "/v1/weather/validId";
        WeatherReport expected = ConstantesData.getWeatherReport();
        when(weatherService.findById(eq("validId"))).thenReturn(expected);
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