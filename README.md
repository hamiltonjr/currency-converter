# Currency Converter

A Java 17-based command-line application that converts US dollars (USD) into Brazilian reais (BRL) using real-time exchange rate data retrieved from the [AwesomeAPI](https://docs.awesomeapi.com.br/api-de-moedas?utm_source=chatgpt.com). Built with object-oriented programming principles, the application features input validation, HTTP communication, JSON processing,  exception handling and automated testing.

## Features

- Fetches exchange rates from the [Awesome API](https://docs.awesomeapi.com.br/api-de-moedas?utm_source=chatgpt.com).
- Converts USD to BRL using the retrieved exchange rate.
- Accepts multiple amounts in a single execution.
- Rejects negative amounts.
- Validates the HTTP response and exchange rate data.
- Fetches the exchange rate only when a valid amount is entered.
- Exits when the user enters `0`.

## Technologies

- [Java 17](https://docs.oracle.com/en/java/javase/17/?utm_source=chatgpt.com)
- [Maven](https://maven.apache.org/guides/?utm_source=chatgpt.com)
- [Jackson Databind](https://github.com/FasterXML/jackson?utm_source=chatgpt.com)
- [AwesomeAPI](https://docs.awesomeapi.com.br/api-de-moedas?utm_source=chatgpt.com)
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/?utm_source=chatgpt.com)
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html?utm_source=chatgpt.com)
- [JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/?utm_source=chatgpt.com)

## Requirements

- JDK 17 or later
- Maven, or the included Maven Wrapper
- Internet connection

## How to Run

Clone the repository:

```bash
git clone https://github.com/hamiltonjr/currency-converter.git
cd currency-converter
```

Build the project:

```bash
./mvnw package
```

Run the application using IntelliJ IDEA, or execute the compiled class with the appropriate classpath.

## Usage

Enter an amount in USD when prompted. The application displays the amount in USD, the exchange rate, and the converted amount in BRL.

Enter `0` to exit.

Example:

```text
Enter the amount in USD (0 to exit): 99

========== Currency Converter ==========

Amount to convert.: US$ 99.00
Exchange rate.....: 1 USD = R$ 5.03
Converted amount..: R$ 498.30

========================================

Enter the amount in USD (0 to exit): 0
Goodbye!
```

*Exchange rates and conversion results may vary between executions.*

## API

This project uses the [Awesome API](https://docs.awesomeapi.com.br/api-de-moedas?utm_source=chatgpt.com) to retrieve reference exchange rates. No API key is required.

The application fetches the USD-to-BRL rate when it starts and uses that rate for the duration of the execution.

## Project Goals

This project is part of my Java development portfolio and demonstrates HTTP requests, JSON parsing, input validation, exception handling, tests and object-oriented programming.

## Author

Hamilton Jr.

GitHub: [@hamiltonjr](https://github.com/hamiltonjr)