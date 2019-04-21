package com.fexco.poc

import junit.framework.TestCase
import org.apache.spark.sql.DataFrame
import org.junit.Assert.assertEquals


class ETLEngineTest extends TestCase {

  val ENVIRONMENT_PATH = getClass.getResource("/")
  val INPUT_FILE_CSV_BLACKLIST: String = ENVIRONMENT_PATH + "/Input/ETLEngine/BlackList/CSV/raw-data.csv"
  val INPUT_FILE_CSV_SCORING: String = ENVIRONMENT_PATH + "/Input/ETLEngine/Scoring/CSV/raw-data.csv"
  val FORMAT_CSV: String = "csv"

  // BlackList report testing in positive case.
  def testBlackListReport: Unit = {
    assertEquals(true,
      try {
        val df: DataFrame = HDFSUtils.readDataSource(INPUT_FILE_CSV_BLACKLIST, FORMAT_CSV)
        val dfBuffer = ETLEngine.applyTransformation("blacklist", df)
        if (df.count() > 0) { // There must be at least one value for the report.
          return true
        }
        return false
      } catch {
        case e: Exception => return false;
      }
    )
  }

  // TestScoring report testing in positive case.
  def testScoringReport: Unit = {
    assertEquals(true,
      try {
        val df: DataFrame = HDFSUtils.readDataSource(INPUT_FILE_CSV_SCORING, FORMAT_CSV)
        val dfBuffer = ETLEngine.applyTransformation("scoring", df)
        if (dfBuffer.count() > 0) { // There must be at leas one value for the report.
          return true
        }
        return false

      } catch {
        case e: Exception => return false
      }
    )
  }

  // TestScoring report testing in case of a non valid mode, this must return an Exception.
  def testInvalidModelReport: Unit = {
    assertEquals(true,
      try {
        val df: DataFrame = HDFSUtils.readDataSource(INPUT_FILE_CSV_SCORING, FORMAT_CSV)
        val dfBuffer = ETLEngine.applyTransformation("non-valid-model", df)
        return false

      } catch {
        case e: IllegalArgumentException => return true
      }
    )
  }

}
