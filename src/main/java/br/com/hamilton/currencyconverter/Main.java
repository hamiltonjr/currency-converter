package br.com.hamilton.currencyconverter;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Converter converter = new Converter();
        double amount = 50.00;
        double exchangeRate = 5.40;
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
}
