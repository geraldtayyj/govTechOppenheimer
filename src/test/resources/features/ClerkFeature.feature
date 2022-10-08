@ClerkFeatures
Feature: Addition of heroes via UI by Clerk
  As a Clerk, I should be able to upload a csv file through the portal so that I can populate the database from a UI.

  Background:
              Given I am logged in as the Clerk
              And I am able to navigate to /clerk/upload-csv page

    @heroesAddedUsingWorkingHeroesCsv
  Scenario: TC1 Addition of working class heroes using a valid csv file "workingHeroes.csv" from UI
      When I upload a csv file "workingHeroes.csv" with below data
      |natid|name|gender|birthDate|deathDate|salary|taxPaid|browniePoints|
    |"natid-999"|"James Tay"|"MALE"|"1990-10-01T00:00:00|null|10000|1000|10|
    |"natid-1000"|"Angela Lim"|"FEMALE"|"1993-08-01T00:00:00|null|5000|500|5|
      And I create working class heroes by csv
      Then I should see a notification "Created Successfully!"
      And All the records I added are persisted into the database table WORKING_CLASS_HEROES

    @heroesAddedUsingInvalidWorkingHeroesCsv
  Scenario: TC2 Addition of working class heroes using an invalid csv file "invalidWorkingHeroes.csv" from UI containing invalid entry with natid "anatid-2000"
    When I upload a csv file "invalidWorkingHeroes.csv" with below data
      |natid|name|gender|birthDate|deathDate|salary|taxPaid|browniePoints|
    |"natid-1999"|"Johnson Tan"|"MALE"|"1990-10-01T00:00:00|null|10000|1000|10|
    |"anatid-2000"|"Amanda Lim"|"FEMALE"|"1993-08-01T00:00:00|null|5000|500|5 |
    And I create working class heroes by csv
    Then I should see a notification "Unable to create hero!"
    And I should see error message "There are 1 records which were not persisted! Please contact tech support for help!"
    And All valid records I added are persisted into the database table WORKING_CLASS_HEROES
    And All invalid records I added are not persisted into the database table WORKING_CLASS_HEROES