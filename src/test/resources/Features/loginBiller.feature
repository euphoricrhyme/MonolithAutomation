Feature: Test the login of biller

  Scenario: Biller Login
    When Biller login api is called
    Then Api must return a token