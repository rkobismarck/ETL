package com.fexco.poc

import junit.framework.TestCase
import org.junit.Assert._


class ETLTest extends TestCase {

  val ENVIRONMENT_PATH = getClass.getResource("/");
  val INPUT_FILE_EXISTENT_CSV: String = ENVIRONMENT_PATH + "/Input/ETL/CSV/raw-data.csv"
  val INPUT_FILE_NON_EXISTENT_CSV: String = ENVIRONMENT_PATH + "/Input/ETL/CSV/any-csv.csv"
  val OUTPUT_FILE_CSV: String = ENVIRONMENT_PATH + "/Output/ETL/Data/CSV"
  val FORMAT_CSV: String = "csv"
  val MODEL:String = "blacklist"

  // Test case for a non valid input for Main.
  def testMainNonValidInputNumberOfParameters: Unit = {
    assertEquals(true,
      try {
        ETL.main(Array("", "", ""))
        return false
      } catch {
        case e: IllegalArgumentException => return true
      }
    )
  }

  // Test for a valid input with all the input validated.
  def testMainValidInput: Unit = {
    assertEquals(true,
      try {
        ETL.main(Array(INPUT_FILE_EXISTENT_CSV, FORMAT_CSV, OUTPUT_FILE_CSV,FORMAT_CSV,MODEL))
        return true
      } catch {
        case e: IllegalArgumentException => return false
      }
    )
  }

  // Test for a valid format input, but non existent input data source in the path cited.
  def testMainValidInputWithNonExistentInputPath: Unit = {
    assertEquals(true,
      try {
        ETL.main(Array(INPUT_FILE_NON_EXISTENT_CSV, FORMAT_CSV, OUTPUT_FILE_CSV,FORMAT_CSV,MODEL))
        return false
      } catch {
        case e: IllegalArgumentException => return true
      }
    )
  }

}
