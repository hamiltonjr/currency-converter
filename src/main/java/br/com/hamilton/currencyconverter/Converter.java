package br.com.hamilton.currencyconverter;

public class Converter {
    public double convert(double amount, double exchangeRate) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Amount cannot be negative."
            );
        }

        if (exchangeRate <= 0) {
            throw new IllegalArgumentException(
                    "Exchange rate must be positive."
            );
        }

        return amount * exchangeRate;
    }
}
