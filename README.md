# Reactive Data Anonymizer

Project Overview

Description:
The Reactive Data Anonymizer is a Spring WebFlux application that accepts JSON data, applies masking and anonymization rules, and returns de-identified JSON data. The project demonstrates reactive programming, streaming data processing, and data privacy techniques.

Purpose:
To provide a simple, configurable system for anonymizing sensitive fields like email and phone in JSON data streams.

---

## Tech Stack

- **Java 17** – language for application logic  
- **Spring Boot 3** – framework for REST API and reactive backend  
- **Spring WebFlux** – reactive programming with non-blocking I/O  
- **Project Reactor** – `Flux`/`Mono` for reactive streams  
- **Lombok** – automatic getters/setters via annotations  
- **SnakeYAML** – read rules from `rules.yml`  

**Frontend:**  
- Plain **HTML/CSS** for demo form  
- **JavaScript Fetch API** to call backend  

**Testing:**  
- **WebTestClient** for reactive endpoint testing  

---


## Project Structure  

```
anonymizer//
│
├── main/
│   ├── java/com/data/anonymizer/
│   │   │   ├── controller/AnonymizerController.java    
│   │   │   ├── model/Rule.java
│   │   │   ├── service/AnonymizerService.java
│   │   │   └── service/RuleLoader.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── rules.yml
│   │       └── static/index.html
│   │
│   │   
│   └──   test/java/com/data/anonymizer/AnonymizerControllerTest.java 
│      
│     
└── pom.xml        
```


##  Configuration Files

application.yml

```sh
spring:
  application:
    name: anonymizer
server:
  port: 8080

```

rules.yml

```sh
rules:
  - field: email
    type: mask
    strategy: keep-domain
  - field: phone
    type: mask
    strategy: last-4-digits


```


## How It Works

### Rule Loading
- `RuleLoader` reads `rules.yml` at application startup and converts each rule into a `Rule` object.

### Anonymization Service
- `AnonymizerService` receives a JSON object, applies rules, and returns a masked copy.
- **Masking strategies implemented:**
  - `keep-domain` → replaces email username with `*****`
  - `last-4-digits` → masks phone number except last 4 digits

### Controller
- `AnonymizerController` exposes POST `/api/anonymize`.
- Accepts a **JSON array of objects** and returns anonymized JSON.

### Frontend Demo
- Users enter **name, email, phone** in a simple HTML form.
- JavaScript sends data to `/api/anonymize` using **Fetch API**.
- Result is displayed below the form.



##  Configuration Files

- Endpoint:

```sh
POST /api/anonymize
Content-Type: application/x-ndjson
Accept: application/x-ndjson

```

Request Example:

```sh
[
  {"name":"Alice","email":"alice@example.com","phone":"0771234567"},
  {"name":"Bob","email":"bob@example.com","phone":"0719876543"}
]

```

Response Example:

```sh
[
  {"name":"Alice","email":"*****@example.com","phone":"****4567"},
  {"name":"Bob","email":"*****@example.com","phone":"****6543"}
]


```

## Dependencies (`pom.xml`)

- `spring-boot-starter-webflux` → reactive web framework  
- `spring-boot-starter-test` → testing framework  
- `reactor-test` → reactive stream testing  
- `lombok` → generate getters/setters automatically  
- `snakeyaml` → read YAML configuration  
- `spring-boot-configuration-processor` → optional configuration metadata


## How to Run

### Prerequisites
- **Java 17** installed
- **Maven** installed
- IDE  IntelliJ IDEA

### Steps

1. **Clone the repository**
```bash
git clone https://github.com/your-username/reactive-data-anonymizer.git
cd reactive-data-anonymizer
```

2. **Build the project with Maven**
```bash
mvn clean install
```
2. **Run the Spring Boot application**
```bash
mvn spring-boot:run
```

## Contributing

Contributions are welcome!

 - &copy; Avishka14
