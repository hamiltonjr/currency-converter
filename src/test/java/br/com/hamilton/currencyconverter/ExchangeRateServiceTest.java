package br.com.hamilton.currencyconverter;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExchangeRateServiceTest {
    @Test
    void shouldCreateServiceWithDefaultHttpClient() {
        ExchangeRateService service = new ExchangeRateService();
        org.junit.jupiter.api.Assertions.assertNotNull(service);
    }

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
                    "USDBRL": {"bid": "5.6513"}
                }
                """);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);
        double rate = service.fetchExchangeRate();
        assertEquals(5.6513, rate, 0.0001);
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
        assertThrows(IOException.class, service::fetchExchangeRate);
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
        assertThrows(IOException.class, service::fetchExchangeRate);
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
                    "USDBRL": {"bid": "-1.0"},
                    "base": "USD",
                    "quote": "BRL"
                }
                """);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);
        assertThrows(IOException.class, service::fetchExchangeRate);
    }

    @Test
    void shouldThrowExceptionWhenApiReturnsMalformedJSON()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        @SuppressWarnings("unchecked")
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("Isto não é um JSON!");
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        ExchangeRateService service = new ExchangeRateService(httpClient);
        assertThrows(IOException.class, service::fetchExchangeRate);
    }


    @Test
    void shouldThrowExceptionWhenConnectionFails()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);

        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenThrow(new IOException("Connection failed"));

        ExchangeRateService service = new ExchangeRateService(httpClient);
        assertThrows(IOException.class, service::fetchExchangeRate);
    }

    @Test
    void shouldThrowIOExceptionWhenRateIsNotPositive()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);
        ExchangeRateService service = new ExchangeRateService(httpClient);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
        {
            "date": "2026-10-22",
            "USDBRL": {"bid": "0.0"},
            "base": "USD",
            "quote": "BRL"
        }
        """);
        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        IOException exception = assertThrows(IOException.class, service::fetchExchangeRate);

        assertEquals("Invalid response from AwesomeAPI: 'bid' must be positive.",
                exception.getMessage());
    }

    @Test
    void shouldThrowIOExceptionWhenRateIsInvalid()
            throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);
        ExchangeRateService service = new ExchangeRateService(httpClient);

        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
        {
            "date": "2026-10-22",
            "USDBRL": {"bid": "abc"},
            "base": "USD",
            "quote": "BRL"
        }
        """);
        when(httpClient.send(
                any(java.net.http.HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        IOException exception = assertThrows(IOException.class, service::fetchExchangeRate);
        assertEquals(
                "Invalid response from AwesomeAPI: invalid 'bid'.",
                exception.getMessage()
        );
    }


    @Test
    void shouldThrowInterruptedExceptionWhenRequestIsInterrupted() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        ExchangeRateService service = new ExchangeRateService(httpClient);

        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenThrow(new InterruptedException("Request interrupted"));
        assertThrows(InterruptedException.class, service::fetchExchangeRate);
    }

    @Test
    void shouldThrowIOExceptionWhenRateIsNotFinite()
        throws IOException, InterruptedException {
        HttpClient httpClient = mock(HttpClient.class);
        ExchangeRateService service = new ExchangeRateService(httpClient);

        HttpResponse<String> response = mock(HttpResponse.class);

        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
        {
            "USDBRL": {"bid": "Infinity"}
        }
        """);

        when(httpClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(response);

        IOException exception = assertThrows(
                IOException.class,
                service::fetchExchangeRate
        );

        assertEquals(
                "Invalid response from AwesomeAPI: 'bid' must be positive.",
                exception.getMessage()
        );
    }
}
