Feature: ETL
  @wip
  Scenario: Extract a BlackList type report from the raw data and save it inside the filesystem
    When a valid "/Input/HDFSUtils/CSV/raw-data.csv" for the raw data and "csv" format
    Then result should be a DataFrame with data
    #| ID_CLIENT | ID_SHOP | SEX | ... | TARGET_LABEL_BAD |
    #|-----------|---------|-----|-----|------------------|
    #| 1         | 15      | F   |     | 1                |
    #| 2         | 12      | F   |     | 1                |
    #| 3         | 16      | M   |     | 0                |
    #| 4         | 15      | F   |     | 0                |
    #| 5         | 12      | F   |     | 0                |
    #| 6         | 16      | M   |     | 0                |
    #| 7         | 15      | F   |     | 1                |
    #| 8         | 12      | F   |     | 1                |
    #| 9         | 16      | M   |     | 0                |
    #| 10        | 15      | F   |     | 0                |
    When i provide a "blacklist" report
    Then it should not contain any value 0 in the "TARGET_LABEL_BAD" column
    #| ID_CLIENT | ID_SHOP | SEX | ... | TARGET_LABEL_BAD |
    #|-----------|---------|-----|-----|------------------|
    #| 1         | 15      | F   |     | 1                |
    #| 2         | 12      | F   |     | 1                |
    #| 4         | 15      | F   |     | 1                |
    #| 5         | 12      | F   |     | 1                |
    When i save it in the fs with "/Output/HDFSUtils/Data/CSV" path and "csv" for output format
    #| ID_CLIENT | ID_SHOP | SEX | ... | TARGET_LABEL_BAD |
    #|-----------|---------|-----|-----|------------------|
    #| 1         | 15      | F   |     | 1                |
    #| 2         | 12      | F   |     | 1                |
    #| 4         | 15      | F   |     | 1                |
    #| 5         | 12      | F   |     | 1                |
    Then i should be able to validate existence of the output in "/Output/HDFSUtils/Data/CSV" path

  @wip
  Scenario: Extract a Scoring type report from the raw data and save it inside the filesystem
    When a valid "/Input/HDFSUtils/CSV/raw-data.csv" for the raw data and "csv" format
    Then result should be a DataFrame with data
    #| ID_CLIENT | ID_SHOP | SEX | ... | TARGET_LABEL_BAD |
    #|-----------|---------|-----|-----|------------------|
    #| 1         | 15      | F   |     | 1                |
    #| 2         | 12      | F   |     | 1                |
    #| 3         | 16      | M   |     | 0                |
    #| 4         | 15      | F   |     | 0                |
    #| 5         | 12      | F   |     | 0                |
    #| 6         | 16      | M   |     | 0                |
    #| 7         | 15      | F   |     | 1                |
    #| 8         | 12      | F   |     | 1                |
    #| 9         | 16      | M   |     | 0                |
    #| 10        | 15      | F   |     | 0                |
    When  i provide a "scoring" report
    # A scoring report(Delinquency Rate) will be calculated:
    # numberElementsInSet => Total number of elements in the ds(Includes TARGET_LABEL_BAD == 0 || TARGET_LABEL_BAD == 1
    # numberElementsBlackListed(Morosity) => If: TARGET_LABEL_BAD == 1 : Then -> Morosity
    # numberElementsInSet => count matrix elements = 10
    # | ID_CLIENT | ID_SHOP | SEX | ... | TARGET_LABEL_BAD |
    #|-----------|---------|-----|-----|------------------|
    #| 1         | 15      | F   |     | 1                |
    #| 2         | 12      | F   |     | 1                |
    #| 3         | 16      | M   |     | 0                |
    #| 4         | 15      | F   |     | 0                |
    #| 5         | 12      | F   |     | 0                |
    #| 6         | 16      | M   |     | 0                |
    #| 7         | 15      | F   |     | 0                |
    #| 8         | 12      | F   |     | 0                |
    #| 9         | 16      | M   |     | 0                |
    #| 10        | 15      | F   |     | 0                |
    When  i provide a "scoring" report
    #| ID_CLIENT | ID_SHOP | SEX | ... | TARGET_LABEL_BAD |
    #|-----------|---------|-----|-----|------------------|
    #| 1         | 15      | F   |     | 1                |
    #| 2         | 12      | F   |     | 1                |
    # So DelinquencyRate should be something like => numberElementsInSet/numberElementsBlackListed
    # For consequence the index
    Then it should return a new DataFrame who contains the calculated value for the Delinquency-Rate
    When i perform a validation on the calculated value, the Delinquency-Rate value should be "22.044088"