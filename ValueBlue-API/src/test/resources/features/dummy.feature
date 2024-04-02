@smoke
Feature:  API CRUD operations

  Scenario: Create a new product
    Given I set POST product API endpoint
    When I set the POST request HEADER
    And I set request body with new product data
    Then I receive valid HTTP response code for the POST call 200
    And Response body contains new product

  Scenario: Read a product
    Given I set GET product API endpoint
    When I set the GET request HEADER
    Then I receive a valid HTTP response code 200
    And Response body contains the product brand

  Scenario: Update a product
    Given I set PUT product API endpoint
    When I set request HEADER and send PUT HTTP request
    And I set request body with updated product data
    Then I receive valid HTTP response code 200
    And Response body contains updated product

  Scenario: Delete a product
    Given I set DELETE product API endpoint
    When I set the delete request HEADER
    And I send DELETE HTTP request
    Then I receive the valid HTTP response code 200
