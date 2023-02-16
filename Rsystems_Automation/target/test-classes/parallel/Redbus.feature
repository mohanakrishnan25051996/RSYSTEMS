@redbus
Feature: Redbus

 
  Scenario: Seat Selection
    Given Launch Redbus Application
    When Select Currency Type
    Then Search Bus
    Then Validate Departure Time Sorted in DescendingOrder
    Then View Seats

 