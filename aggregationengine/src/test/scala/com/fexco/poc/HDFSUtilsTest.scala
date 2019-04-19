package com.fexco.poc

import junit.framework.TestCase
import org.junit.Assert._


class HDFSUtilsTest extends TestCase {

  val ENVIRONMENT_PATH = getClass.getResource("/");
  val INPUT_FILE_EXISTENT_CSV: String = ENVIRONMENT_PATH + "/Input/HDFSUtils/CSV/raw-data.csv"
  val INPUT_FILE_NON_EXISTENT_PARQUET: String = ENVIRONMENT_PATH + "Input/HDFSUtils/Parquet/raw-data"
  val OUTPUT_FILE_CSV: String = ENVIRONMENT_PATH + "/Output/HDFSUtils/Data/CSV"
  val OUTPUT_FILE_PARQUET: String = ENVIRONMENT_PATH + "/Output/HDFSUtils/Data/Parquet"
  val FORMAT_CSV: String = "csv"

  // Generic method for reading with type of data source parametrization.
  def testReadDataSourceCSV: Unit = {
    assertEquals(false, HDFSUtils.readDataSource(INPUT_FILE_EXISTENT_CSV, FORMAT_CSV).take(1).isEmpty)
  }

  // Explicit for just reading csv files.
  def testReadCSV: Unit = {
    assertEquals(false, HDFSUtils.readCSV(INPUT_FILE_EXISTENT_CSV).take(1).isEmpty)
  }

  // Generic method for writing in the FS with type of output parametrization.
  def testWriteOnHDFSCSV: Unit = {
    val df = HDFSUtils.readCSV(INPUT_FILE_EXISTENT_CSV)
    HDFSUtils.writeOnHDFS(df, OUTPUT_FILE_CSV, "csv")
    assertEquals(true,HDFSUtils.existsFileOnHDFS(OUTPUT_FILE_CSV))
  }

  // Explicit method for writing in parquet files.
  def testWriteOnHDFSParquet: Unit = {
    val df = HDFSUtils.readCSV(INPUT_FILE_EXISTENT_CSV)
    HDFSUtils.writeOnHDFS(df, OUTPUT_FILE_PARQUET, "parquet")
    assertEquals(true,HDFSUtils.existsFileOnHDFS(OUTPUT_FILE_PARQUET))
  }

  def testExistsFileOnHDFS : Unit = {
    assertEquals(true,HDFSUtils.existsFileOnHDFS(INPUT_FILE_EXISTENT_CSV))
  }

  def testNonExistsFileOnHDFS : Unit = {
    assertEquals(false,HDFSUtils.existsFileOnHDFS(INPUT_FILE_NON_EXISTENT_PARQUET))
  }

}
