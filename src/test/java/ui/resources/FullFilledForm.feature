Feature: Fill form and press continue
  Scenario Outline: Check if filled form works correctly
    Given I'm on Rail Ninja web page
    And I choose route
    And I choose date
    And I click Search trains
    And I choose first train
    When I fill email and confirm it
    When I change Adult passenger to "<display_name>"
    And I fill over fields
    When I click continue
    Then I should see the next step page
    Examples:
      | display_name      |
      | Vadim Rutkovsky   |


