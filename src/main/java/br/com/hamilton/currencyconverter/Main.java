package br.com.hamilton.currencyconverter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        ExchangeRateService exchangeRateService = new ExchangeRateService();
        double exchangeRate = exchangeRateService.fetchExchangeRate();

        run(input, System.out, exchangeRate);
    }

    static void run(Scanner input, PrintStream output, double exchangeRate) {
        Converter converter = new Converter();
        double amount;

        do {
            output.print("Enter the amount in USD (0 to exit): ");
            amount = input.nextDouble();

            if (amount < 0) {
                output.println("Amount cannot be negative.");
                continue;
            }

            if (amount != 0) {
                double result = converter.convert(amount, exchangeRate);

                output.printf(
                        Locale.US,
                        """
                                ========== Currency Converter ==========
                                
                                Amount to convert.: US$ %.2f
                                Exchange rate.....: 1 USD = R$ %.2f
                                Converted amount..: R$ %.2f
                                
                                ========================================
                                %n""",
                        amount,
                        exchangeRate,
                        result
                );
            }
        } while (amount != 0);

        output.println("Goodbye!");
    }
}
