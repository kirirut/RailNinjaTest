Feature: Changing data on Rail Ninja
  Scenario Outline: Check if display name change works correctly
    Given I'm on Rail Ninja web page
    And I choose route
    And I choose date
    And I choose first train
    And I choose flexible
    And I click continue
    When I change Adult passenger to "<display_name>"



    Examples:
      | display_name |
      |   Vadim      |
