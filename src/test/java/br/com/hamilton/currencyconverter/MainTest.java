package br.com.hamilton.currencyconverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
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
}
