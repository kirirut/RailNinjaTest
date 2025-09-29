Feature: Empty form
Scenario: Check behavior with empty passenger form
  Given I'm on Rail Ninja web page
  And I choose route
  And I choose date
  And I click Search trains
  And I choose first train
  When I click continue
  Then I should see an error message indicating the form is empty