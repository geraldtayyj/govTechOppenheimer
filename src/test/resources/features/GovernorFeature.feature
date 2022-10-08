@GovernorFeatures
Feature: As the Governor, I am able to search for a hero using name or natid from the UI.

  Background:
    Given I am logged in as the Governor

  Scenario: TC1 I am able to see all the heroes as the Governor when I click the "List All" button
    When I click on the List ALL!! button
    Then A table appears with the headers as below
      | Nat Id | Name | Gender | Birth Date | Death Date | Salary | Tax Paid | Brownie Points |
    And The total number of entries matches the total in the "working_class_heroes" database

  Scenario: TC2 I can search for any hero by typing their natid into the search bar
    When I click on the List ALL!! button
    And A table appears with the headers as below
      | Nat Id | Name | Gender | Birth Date | Death Date | Salary | Tax Paid | Brownie Points |
    And I search for natid "natid-2"
    Then I see the results for natid "natid-2"

  Scenario: TC3 I can search for any hero by typing their name into the search bar
    When I click on the List ALL!! button
    And A table appears with the headers as below
      | Nat Id | Name | Gender | Birth Date | Death Date | Salary | Tax Paid | Brownie Points |
    And I search for name with "natid-2"
    Then I see the results for name with "natid-2"
