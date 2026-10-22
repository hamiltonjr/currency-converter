package br.com.hamilton.currencyconverter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Converter converter = new Converter();
        double exchangeRate = 5.40;
        double amount;

        do {
            System.out.print("Enter the amount in USD (0 to exit): ");
            amount = input.nextDouble();

            if (amount != 0) {
                double result = converter.convert(amount, exchangeRate);

                System.out.printf(
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

        input.close();
        System.out.println("Goodbye!");
    }
}
