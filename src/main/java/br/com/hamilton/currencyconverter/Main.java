package br.com.hamilton.currencyconverter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private Main() {}

    public static void main(String[] args) throws IOException, InterruptedException {
        start(new Scanner(System.in), System.out, new ExchangeRateService());
    }

    static void start(Scanner input, PrintStream output, ExchangeRateService exchangeRateService)
        throws IOException, InterruptedException {
        run(input, output, exchangeRateService);
    }

    static void run(Scanner input, PrintStream output, ExchangeRateService exchangeRateService)
            throws IOException, InterruptedException {
        Converter converter = new Converter();
        Double exchangeRate = null;
        double amount;

        while (true) {
            output.print("Enter the amount in USD (0 to exit): ");
            amount = input.nextDouble();

            if (amount == 0) break;

            if (amount < 0) {
                output.println("Amount cannot be negative.");
                continue;
            }

            if (exchangeRate == null) {
                exchangeRate = exchangeRateService.fetchExchangeRate();
            }

            double result = converter.convert(amount, exchangeRate);
            output.printf(
                    Locale.US,
                    """
                            ========== Currency Converter ==========
                            
                            Amount to convert.: US$ %.2f
                            Exchange rate.....: 1 USD = R$ %.4f
                            Converted amount..: R$ %.2f
                            
                            ========================================
                            %n""",
                    amount,
                    exchangeRate,
                    result
            );
        }

        output.println("Goodbye!");
    }
}
