## Requirements

- Java 20
- Maven
- ChromeDriver
- Selenium
- JUnit 5
- dotenv-java

## Setup

1. Clone the repository.
2. Create a `.env` file in the root directory following the `.env.example`file
3. you may need to manually set system property with the path to you chromedriver.exe.

## Running the test suite

1. Initialize the test by bash command `mvn test` in the root directory or manually initialize `.pom` file from IDE.
2. Then run the `wordpress_automation/src/test/java/WordPressAutomationTest.java` file to run the test.
   
