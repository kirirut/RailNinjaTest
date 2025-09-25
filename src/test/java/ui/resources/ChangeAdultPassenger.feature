Feature: Changing data on Rail Ninja
  Scenario Outline: Check if display name change works correctly
    Given I'm on Rail Ninja web page
    And I choose route
    And I choose date
    And I click Search trains
    And I choose first train
    When I change Adult passenger to "<display_name>"



    Examples:
      | display_name |
      | Vadim        |
      | Anna         |
      | Dmitry       |
      | Elena        |
      | Alexey       |
      | Maria        |
      | Sergey       |
      | Olga         |
      | Nikolay      |
      | Tatiana      |
      | Ivan         |
      | Ekaterina    |
      | Mikhail      |
      | Yulia        |
      | Andrey       |
      | Svetlana     |
      | Viktor       |
      | Natalia      |
      | Pavel        |
      | Irina        |
