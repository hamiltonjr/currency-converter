package br.com.hamilton.currencyconverter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConverterTest {
    @Test
    void shouldConvertUsdToBrl() {
        Converter converter = new Converter();
        double result = converter.convert(100.0, 5.0);
        assertEquals(500.0, result, 0.001);
    }

    @Test
    void shouldReturnZeroWhenAmountIsZero() {
        Converter converter = new Converter();
        double result = converter.convert(0.0, 5.0);
        assertEquals(0.0, result, 0.001);
    }


    @Test
    void shouldRejectNegativeAmount() {
        Converter converter = new Converter();
        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(-100.0, 5.0)
        );
    }

    @Test
    void shouldRejectZeroExchangeRate() {
        Converter converter = new Converter();
        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(100.0, 0.0)
        );
    }

    @Test
    void shouldRejectNegativeExchangeRate() {
        Converter converter = new Converter();
        assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(100.0, -5.0)
        );
    }
}
