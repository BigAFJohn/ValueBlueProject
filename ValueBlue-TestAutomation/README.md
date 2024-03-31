# Assessment Part 1 - Web Automation Testing

This repository is part of a two-part assessment focusing on web automation testing. It showcases automated UI tests for https://example.com, utilizing Selenium, Cucumber, and TestNG for navigation, link validation, and content verification within the domain names box.
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java JDK 11 or higher
- Maven
- Web browsers (Chrome, Firefox)
- WebDriverManager

### Installation

1. Clone the repository to your local machine:
    ```bash
    git clone https://github.com/BigAFJohn/ValueBlueProject
    ```
2. Navigate to the project directory:
    ```bash
    cd ValueBlue-TestAutomation
    ```
3. Use Maven to install dependencies and build the project:
    ```bash
    mvn clean install
    ```

## Running the Tests

To execute all tests, run the following command from the root of the `Part1` directory:

```bash
mvn test
## Breakdown of Tests
The tests cover the following scenarios:

Navigation to the base URL and verification of the landing page.
Click actions on the "More information..." link and verification of target content.
Verification of the presence of specific RFC links within the page.
Interaction with the "Domains" section and verification of the text within the "Domain Names" box.
Configuration
All test configurations can be adjusted in the config.properties file located under src/main/resources/Configuration. This includes the base URL, browser choice, and targeted elements' locators.

Built With
Selenium WebDriver - Web automation tool
Cucumber - BDD framework for writing clear and concise tests
TestNG - Testing framework
Maven - Dependency Management

Author
John Ige
Test Automation Engineer 
Contact - seyifunmi_aj@yahoo.com

