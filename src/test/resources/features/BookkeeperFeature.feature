Feature: Generating Tax Relief Egress File as a Book Keeper
  As a Book Keeper, I should be to generate a tax relief egress file "taxrelief.txt" from the UI

  @UITest
Scenario: TC1 Generate Tax Relief Egress File "taxrelief.txt"
    Given I am logged in as the Book Keeper
  When I click on the Generate Tax Relief File button
  Then A file "taxrelief.txt" is created

Scenario: TC2 Check format of file "taxrelief.txt" and validate each line is in the format <natid>, <tax relief amount>, followed by a footer which indicates total number of records written to file
  Given A file "taxrelief.txt" exists
  When I read the file "taxrelief.txt"
  Then Each line in "taxrelief.txt" is in the format <natid>, <tax relief amount>,
  And Footer indicates total number of records written to file

  @noRecordsInTable
    @UITest
  @deleteTaxReliefFile
  Scenario: TC3 If there are no records to be generated, "taxrelief.txt" is still generated containing the footer
    Given There are no records in the database table working_class_heroes
    And I am logged in as the Book Keeper
    When I click on the Generate Tax Relief File button
    And A file "taxrelief.txt" is created
    And I read the file "taxrelief.txt"
    Then Footer indicates total number of records written to file

    @UITest
    @deleteTaxReliefFile
    Scenario: TC4 When a file process is triggered, a file of FILE_TYPE: TAX_RELIEF record is
    being persisted into a database table FILE containing the file status, total count of records being
    written into file. File statuses are PROCESSING, COMPLETED, ERROR
      Given I am logged in as the Book Keeper
      And There exists n counts of files in the FILE database table
      When I click on the Generate Tax Relief File button
      And A file "taxrelief.txt" is created
      And I read the file "taxrelief.txt"
      Then There exists n+1 counts of files in the FILE database table
      And Last record contains file status, total count of records and FILE_TYPE: TAX_RELIEF