@smoke
Feature: Example
  @critical
  Scenario: FinishMe
    When I start the browser
    And I navigate to the configured URL
    And I click on the 'More information...' link
    Then a link with text 'RFC 2606' must be present
    And a link with text 'RFC 6761' must be present
    And I click on the 'Domains' element
    And the 'Domain Names' box must contain 'Root Zone Management' at index 2

