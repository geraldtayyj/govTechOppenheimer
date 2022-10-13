# govTechOppenheimer Project

## User Stories
1. Enable Users to create a single Working Class Hero by API Call.
2. Enable Clerks to upload a csv file via UI to populate the database.
3. Enable Book Keepers to generate a Tax Relief Egress File from the UI
4. Enable Governors to search for a working class hero by nat-id and name

## Setup
```
  git clone "https://github.com/geraldtayyj/govTechOppenheimer.git"
  cd "{path to govTechOppenheimer}\govTechProject"
  mvn clean test
```

## Test scenarios and features
> APIFeature.feature
```
  Feature: Create single working class hero via API call to /api/v1/hero
  
  Scenario: TC1 Post request /api/v1/hero with valid payload
  Scenario: TC2(a-h) Field validations for Post request to /api/v1/hero
  Scenario: TC3 No changes to WORKING_CLASS_HEROES table should be made if the natid already exists.
  Scenario: TC4 Verify record is created in database table WORKING_CLASS_HEROES
  
```


> ClerkFeature.feature
```
  Feature: Addition of heroes via UI by Clerk
  As a Clerk, I should be able to upload a csv file through the portal so that I can populate the database from a UI.
  
  Scenario: TC1 Addition of working class heroes using a valid csv file "workingHeroes.csv" from UI
  Scenario: TC2 Addition of working class heroes using an invalid csv file "invalidWorkingHeroes.csv" from UI containing invalid entry with natid "anatid-2000"
  
```

> BookkeeperFeature.feature
```
  Feature: Generating Tax Relief Egress File as a Book Keeper
  As a Book Keeper, I should be to generate a tax relief egress file "taxrelief.txt" from the UI
  
  Scenario: TC1 Generate Tax Relief Egress File "taxrelief.txt"
  Scenario: TC2 Check format of file "taxrelief.txt" and validate each line is in the format <natid>, <tax relief amount>, followed by a footer which indicates total number of records written to file
  Scenario: TC3 If there are no records to be generated, "taxrelief.txt" is still generated containing the footer
  Scenario: TC4 When a file process is triggered, a file of FILE_TYPE: TAX_RELIEF record is
  being persisted into a database table FILE containing the file status, total count of records being
  written into file. File statuses are PROCESSING, COMPLETED, ERROR
```

> GovernorFeature.feature
```
  Feature: As the Governor, I am able to search for a hero using name or natid from the UI.

  Scenario: TC1 I am able to see all the heroes as the Governor when I click the "List All" button
  Scenario: TC2 I can search for any hero by typing their natid into the search bar
  Scenario: TC3 I can search for any hero by typing their name into the search bar
```
## Test Results
![image](https://user-images.githubusercontent.com/61403205/195511662-6c80f91d-938d-4cf8-a90b-73c389431bc4.png)
![image](https://user-images.githubusercontent.com/61403205/195511909-19c257ad-ce4e-40c5-b7e8-58cdd48b60e8.png)

## Bugs
> APIFeature.feature
```
  TC1-4: HTTP Status code 400 when sending post request to /api/v1/hero with valid payload, 
  with error message "Something horrible has occurred when saving working class hero!"
  Suspected root cause is due to improper DateTimeFormatter format used to parse birthDate and deathDate (refer to stack trace below)
  As a result, new working class heroes cannot be added to database via API.
```
  ![image](https://user-images.githubusercontent.com/61403205/195513376-e1166858-f436-4907-9de3-2c4e34f3e373.png)

> ClerkFeature.feature
```
  TC2: Addition of working class hero using an improperly formatted csv file with natid in improper format was possible. 
  On further exploratory testing, it was found that there is a lack of backend validation for natid for addition of working class heroes using csv files. 
```

