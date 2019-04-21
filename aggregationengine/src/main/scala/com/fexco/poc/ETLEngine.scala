package com.fexco.poc

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.DataFrame


object ETLEngine extends LazyLogging {


  def applyTransformation(model: String, df: DataFrame): DataFrame = {
    logger.info("ETL Engine - Invoking transformation model " + model)
    model match {
      case "blacklist" => return Transformations.blackListReport(df)
      case "scoring" => return Transformations.scoringReport(df)
      case default => throw new IllegalArgumentException("Model not defined.")
    }
  }

  def persistResults(path: String, format: String, df: DataFrame) = {
    logger.info("ETL Engine - Persist results invoke")
    HDFSUtils.writeOnHDFS(df, path, format)
  }

  def preconditionsEngine(pathInputData: String): Any = {
    logger.info("ETL Engine - Preconditions validation started.")
    if (HDFSUtils.existsFileOnHDFS(pathInputData) == false) {
      throw new IllegalArgumentException("Input file not found")
    }
    logger.info("ETL Engine - Precondition validation finished successfully")
  }

  def invokeEngine(pathInputData: String, formatInput: String, pathOutput: String, model: String): Any = {
    // Preconditions.
    preconditionsEngine(pathInputData)
    // Load Input
    val df = HDFSUtils.readDataSource(pathInputData, formatInput)
    // Apply Transformation
    val df_output = applyTransformation(model, df)
    // Persist Data
    persistResults(pathOutput, format = "csv", df_output)

  }


}
