package br.com.hamilton.currencyconverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MainTest {
    @Test
    void shouldRunMainWithFetchedRate() throws Exception {
        java.io.InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(
                "100\n0\n".getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(capturedOutput, true, StandardCharsets.UTF_8));

        try (org.mockito.MockedConstruction<ExchangeRateService> construction =
                     Mockito.mockConstruction(
                             ExchangeRateService.class,
                             (service, context) -> Mockito.when(service.fetchExchangeRate())
                                     .thenReturn(5.47))) {
            Main.main(new String[0]);

            Assertions.assertTrue(capturedOutput.toString(StandardCharsets.UTF_8)
                    .contains("Converted amount..: R$ 547.00"));
            Assertions.assertTrue(capturedOutput.toString(StandardCharsets.UTF_8)
                    .contains("Goodbye!"));
            Mockito.verify(construction.constructed().get(0), Mockito.times(1))
                    .fetchExchangeRate();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }

    @Test
    void shouldConvertAmountAndExit() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("100\n0\n"
                .getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(byteArrayOutputStream);
        Scanner input = new Scanner(byteArrayInputStream);

        Main.run(input, output, 5.50);

        String consoleOutput = byteArrayOutputStream.toString();
        Assertions.assertTrue(consoleOutput.contains("Converted amount..: R$ 550.00"));
        Assertions.assertTrue(consoleOutput.contains("Goodbye!"));
    }

    @Test
    void shouldDisplayErrorWhenAmountIsNegative() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("-1\n0\n"
                .getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(byteArrayOutputStream);
        Scanner input = new Scanner(byteArrayInputStream);

        Main.run(input, output, 5.50);

        String consoleOutput = byteArrayOutputStream.toString();
        Assertions.assertTrue(consoleOutput.contains("Amount cannot be negative"));
    }

    @Test
    void shouldConvertMultipleAmountsAndExit() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("1\n2\n3\n0\n"
                .getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(byteArrayOutputStream);
        Scanner input = new Scanner(byteArrayInputStream);

        Main.run(input, output, 5.50);

        String consoleOutput = byteArrayOutputStream.toString();
        Assertions.assertTrue(consoleOutput.contains("Converted amount..: R$ 5.50"));
        Assertions.assertTrue(consoleOutput.contains("Converted amount..: R$ 11.00"));
        Assertions.assertTrue(consoleOutput.contains("Converted amount..: R$ 16.50"));
    }

    @Test
    void shouldStartApplicationAndExitWhenInputIsZero()
        throws IOException, InterruptedException {
        ExchangeRateService exchangeRateService = Mockito.mock(ExchangeRateService.class);
        Mockito.when(exchangeRateService.fetchExchangeRate()).thenReturn(5.47);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("0\n".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(byteArrayOutputStream);
        Scanner input = new Scanner(byteArrayInputStream);

        Main.start(input, output, exchangeRateService);
        String consoleOutput = byteArrayOutputStream.toString();
        Assertions.assertTrue(consoleOutput.contains("Goodbye!"));
        Mockito.verify(exchangeRateService, Mockito.times(1))
                .fetchExchangeRate();
    }


    @Test
    void shouldStartApplicationConvertAmountAndExit()
            throws IOException, InterruptedException {
        ExchangeRateService exchangeRateService =
                Mockito.mock(ExchangeRateService.class);
        Mockito.when(exchangeRateService.fetchExchangeRate())
                .thenReturn(5.47);
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(
                        "100\n0\n".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();

        PrintStream output = new PrintStream(byteArrayOutputStream);
        Scanner input = new Scanner(byteArrayInputStream);
        Main.start(input, output, exchangeRateService);
        String consoleOutput = byteArrayOutputStream.toString();

        Assertions.assertTrue(consoleOutput.contains("Converted amount..: R$ 547.00"));
        Assertions.assertTrue(consoleOutput.contains("Goodbye!"));
        Mockito.verify(exchangeRateService, Mockito.times(1))
                .fetchExchangeRate();
    }
}
