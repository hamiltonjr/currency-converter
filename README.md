# Currency Converter

A simple command-line currency converter built with Java. The application retrieves the USD-to-BRL exchange rate from the Frankfurter API and converts amounts from US dollars to Brazilian reais.

## Features

- Fetches exchange rates from the Frankfurter API.
- Converts USD to BRL using the retrieved exchange rate.
- Accepts multiple amounts in a single execution.
- Rejects negative amounts.
- Validates the HTTP response and exchange rate data.
- Exits when the user enters `0`.

## Technologies

- Java 17
- Maven
- Jackson Databind
- Java HTTP Client
- Frankfurter API

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

This project uses the [Frankfurter API](https://frankfurter.dev/) to retrieve reference exchange rates. No API key is required.

The application fetches the USD-to-BRL rate when it starts and uses that rate for the duration of the execution.

## Project Goals

This project is part of my Java development portfolio and demonstrates HTTP requests, JSON parsing, input validation, exception handling, and object-oriented programming.

## Author

Hamilton Jr.

GitHub: [@hamiltonjr](https://github.com/hamiltonjr)