package br.com.hamilton.currencyconverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public double fetchExchangeRate() throws IOException, InterruptedException {
        URI uri = URI.create(
                "https://api.frankfurter.dev/v2/rate/usd/brl"
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        // tratamento de possíveis erros HTTP
        if (response.statusCode() != 200) {
            throw new IOException(
                    "Frankfurter API returned HTTP status: "
                            + response.statusCode()
            );
        }


        JsonNode json = objectMapper.readTree(response.body());
        JsonNode rateNode = json.get("rate");

            if (rateNode == null
                || !rateNode.isNumber()
                || rateNode.asDouble() <= 0) {
            throw new IOException(
                    "Invalid response from Frankfurter API: missing or invalid 'rate'."
            );
        }

        return rateNode.asDouble();
    }
}
