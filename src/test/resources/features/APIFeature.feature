Feature: Create single working class hero via API call to /api/v1/hero

  @postWithValidPayload
  Scenario: TC1 Post request /api/v1/hero with valid payload {
  "natid": <natid>,
  "name": <name>,
  "gender": <gender>,
  "birthDate": <birthDate>,
  "deathDate": <deathDate>,
  "salary": <salary>,
  "taxPaid": <taxPaid>,
  "browniePoints": <browniePoints> }

    Given A valid payload with below details

      | natid    | name  | gender | birthDate           | deathDate | salary | taxPaid | browniePoints |
      | natid-12 | hello | MALE   | 2020-01-01T00:00:00 | null      | 10.00  | 1       | 9             |

    When I send a POST request to "/api/v1/hero"
    Then The status code is 200

  Scenario Template: (TC2.a) validate natid format (natid must be in this format - natid-number, where number is between 0 to 9999999)
    Given A valid payload with natid <invalidNatId>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "Invalid natid"

    Examples:
      | invalidNatId   |
      | natid-10000000 |
      | anatid-100     |

  Scenario Template: (TC2.b) validate name format (name must contain alphabets and spaces and length of name between 1 and 100)
    Given A valid payload with name <invalidName>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "Invalid name"

    Examples:
      | invalidName                                                                                                                |
      | "containsNumbers123"                                                                                                       |
      | "containsSpecialChars!@#"                                                                                                  |
      | ""                                                                                                                         |
      | "this name is in a valid format but it is more than one hundred characters in length therefore it becomes an invalid name" |

  Scenario Template: (TC2.c) validate gender format (gender must be either MALE or FEMALE)
    Given A valid payload with gender <invalidGender>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "Invalid gender"

    Examples:
      | invalidGender |
      | "male"        |
      | "newGender"   |

  Scenario Template: (TC2.d) validate birthDate format (birthDate format is in yyyy-mm-ddTHH24:mm:ss and cannot be a future date. The ‘T’ is a string literal.)
    Given A valid payload with birthDate <invalidBirthDate>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "Invalid birthdate format"

    Examples:
      | invalidBirthDate      |
      | "2035-10-07T00:00:00" |
      | "10-07-1990T00:00:00" |
      | "1990-10-0700:00:00"  |
      | "null"                |

  Scenario Template: (TC2.e) validate deathDate format (deathDate format is in yyyy-mm-ddTHH24:mm:ss)
    Given A valid payload with deathDate <invalidDeathDate>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "Invalid death date format"

    Examples:
      | invalidDeathDate      |
      | "2035-10-07T00:00:00" |
      | "10-07-1990T00:00:00" |
      | "1990-10-0700:00:00"  |

  Scenario Template: (TC2.f) validate salary format (salary is a decimal and cannot be negative)
    Given A valid payload with salary <salary>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "Salary must be greater than or equals to zero"

    Examples:
      | salary |
      | -10.00 |

  Scenario Template: (TC2.g) validate taxPaid format (taxPaid is a decimal and cannot be negative)
    Given A valid payload with taxPaid <taxPaid>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "must be greater than or equal to 0"

    Examples:
      | taxPaid |
      | -10.00  |

  Scenario Template: (TC2.h) validate that browniePoints and deathDate are nullable
    Given A valid payload with null browniePoints <browniePoints> and null deathDate <deathDate>
    When I send a POST request to "/api/v1/hero"
    Then The status code is 200
    Examples:
      | browniePoints | deathDate |
      | "null"        | "null"    |

  Scenario: TC3 No changes to WORKING_CLASS_HEROES table should be made if the natid already exists.
    Given A valid payload containing an existing natid "natid-1"
    And Valid database entry of hero with natid "natid-1"
    When I send a POST request to "/api/v1/hero"
    Then The status code is 400
    And errorMsg is "Working Class Hero of natid: natid-1 already exists!"
    And verify no changes has been made to the database entry with natid "natid-1"

  @successfullyCreatedHero
  Scenario: TC4 Verify record is created in database table WORKING_CLASS_HEROES
    Given A valid payload with natid not existing in the database table
    When I send a POST request to "/api/v1/hero"
    Then The status code is 200
    And record is created in database table with natid as in payload

