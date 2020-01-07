Feature: Testing a GET Invoices API
  Users should be able to GET invoices
  represented by WireMock

  Scenario: Make a get call to server
    When make get call
    Then have invoices