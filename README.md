# Restful Booker API Test Automation

## Overview

This project contains automated API tests for the **Restful Booker API**.

The goal is to validate core booking functionality, authentication behavior, and detect potential defects related to data validation and business logic.

API documentation:  
https://restful-booker.herokuapp.com/apidoc/index.html

---

## Tech Stack

- Java 21
- JUnit 5
- RestAssured
- Lombok
- JavaFaker
- Allure

---

## Project Structure

src/main/java
- config              API configuration (base URL, request spec, content type)
- helper              POJOs for request models
- services            API client layer
- utils               Authentication handling

src/test/java
- base                Base test setup
- tests               Test suites (CRUD, Auth, Defects)
- testdata            Test data factories

---

## Test Strategy

The suite covers:

### Positive Scenarios
- Create booking
- Retrieve booking by ID
- Update booking (PUT)
- Partial update booking (PATCH)
- Delete booking
- Valid authentication

### Negative / Security Scenarios
- Invalid authentication
- Unauthorized update attempt

### Defect Coverage
- Invalid booking date ranges
- Empty required fields

---

## How to Run Tests

### Run all tests
```bash
mvn clean test
```

### Run Allure report
```bash
mvn clean test
allure allure:serve
```

---

## Key Design Decisions

### Service Layer
API calls are separated into a service layer (BookingService) to keep tests clean and readable.

### Test Data Factory
Random test data is generated using JavaFaker while ensuring valid booking dates.

### Configuration
Base URL and content type are centralized in Config.

### Authentication
Token generation is handled in AuthTokenManager.

### Test Structure
Tests are grouped into:
- CRUD tests
- Auth tests
- Defect tests

---

## Implemented Test Cases

### Booking CRUD
- Create booking and verify retrieval
- Update booking
- Partial update booking
- Delete booking

### Authentication
- Valid credentials
- Invalid credentials
- Unauthorized update attempt

### Defect Tests
- Checkout date is before check-in date
- Empty required fields acceptance

---

## Known Defects

- Booking allows checkout date earlier than checkin date
- Invalid date formats are accepted and converted incorrectly
- Empty required fields are accepted
- Invalid total price values are accepted
- Invalid depositpaid values are silently coerced

---

## Notes

- Tests are independent and can run in any order
- Random data is used for better coverage
- Defect tests are intentionally separated from happy path tests

---

## Author

Sara Pajic