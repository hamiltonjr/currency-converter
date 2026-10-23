package br.com.hamilton.currencyconverter;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExchangeRateServiceTest {
    @Test
    void shouldReturnExchangeRateWhenApiRespondsSuccessfully()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        @SuppressWarnings("unchecked")
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
                {
                    "date": "2026-10-22",
                    "base": "USD",
                    "quote": "BRL",
                    "rate": 5.40
                }
                """);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);
        double rate = service.fetchExchangeRate();
        assertEquals(5.40, rate, 0.001);
    }


    @Test
    void shouldThrowExceptionWhenApiReturnsServerError()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        @SuppressWarnings("unchecked")
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(500);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);

        assertThrows(
                IOException.class,
                () -> service.fetchExchangeRate()
        );
    }

    @Test
    void shouldIOExceptionWhenApiReturnsJSONWithoutRate()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        @SuppressWarnings("unchecked")
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
        {
            "date": "2026-10-22",
            "base": "USD",
            "quote": "BRL"
        }
        """);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);

        assertThrows(
                IOException.class,
                () -> service.fetchExchangeRate()
        );
    }

    @Test
    void shouldThrowExceptionWhenRateIsZero()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        @SuppressWarnings("unchecked")
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
        {
            "date": "2026-10-22",
            "rate": 0.0,
            "base": "USD",
            "quote": "BRL"
        }
        """);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);

        assertThrows(
                IOException.class,
                () -> service.fetchExchangeRate()
        );
    }

    @Test
    void shouldThrowExceptionWhenRateIsNegative()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        @SuppressWarnings("unchecked")
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
        {
            "date": "2026-10-22",
            "rate": -1.0,
            "base": "USD",
            "quote": "BRL"
        }
        """);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);

        assertThrows(
                IOException.class,
                () -> service.fetchExchangeRate()
        );
    }
}