Infrastructure
currency-converter-api/
├── src/main/java/com/currencyconverter/
│   ├── api/          # API communication classes
│   ├── exception/    # Custom exception classes
│   ├── model/        # Data model classes
│   ├── util/         # Utility classes
│   └── Main.java     # Main application class
├── data/             # Data storage directory
└── build.gradle      # Build configuration


# Currency Converter API

A comprehensive Java-based currency converter application that demonstrates Object-Oriented Programming principles, collection handling, API consumption, file operations, and error handling.

## Features

- **Real-time Currency Conversion**: Uses live exchange rates from ExchangeRate-API
- **Multiple Currency Support**: Supports conversion between 15+ major currencies
- **Conversion History**: Saves all conversion operations to a local file
- **Error Handling**: Comprehensive exception handling for robust operation
- **User-friendly Interface**: Console-based menu system

## Technologies Used

- **Java 11+**: Core programming language
- **GSON**: JSON parsing library
- **HTTPURLConnection**: For API communication
- **Java Collections Framework**: For data management
- **File I/O**: For persistent data storage

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- Gradle (for building)
- Internet connection (for API access)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/currency-converter-api.git

cd currency-converter-api
