Feature: Passenger prove mail
  Scenario: User fills passenger email form successfully
    Given I'm on Rail Ninja web page
    And I choose route
    And I choose date
    And I click Search trains
    And I choose first train
    When I fill email and confirm it
    Then I should see email confirmation message
