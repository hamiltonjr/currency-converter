package br.com.hamilton.currencyconverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExchangeRateService() {
        this(HttpClient.newHttpClient());
    }

    public ExchangeRateService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public double fetchExchangeRate() throws IOException, InterruptedException {
        URI uri = URI.create("https://economia.awesomeapi.com.br/json/last/USD-BRL");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // tratamento de possíveis erros HTTP
        if (response.statusCode() != 200) {
            throw new IOException(
                    "Awesome API returned HTTP status: "
                            + response.statusCode()
            );
        }

        JsonNode json = objectMapper.readTree(response.body());
        JsonNode rateNode = json.path("USDBRL").path("bid");

        if (!rateNode.isTextual()) {
            throw new IOException("Invalid response from AwesomeAPI: missing or invalid 'bid'.");
        }

        double rate;

        try {
            rate = Double.parseDouble(rateNode.asText());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid response from AwesomeAPI: invalid 'bid'.", e);
        }

        if (!Double.isFinite(rate) || rate <= 0) {
            throw new IOException("Invalid response from AwesomeAPI: 'bid' must be positive.");
        }

        return rate;
    }
}
