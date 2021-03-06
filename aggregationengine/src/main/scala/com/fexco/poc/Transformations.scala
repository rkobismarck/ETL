package com.fexco.poc

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.{Column, DataFrame}


object Transformations extends LazyLogging {

  def blackListReport(df: DataFrame): DataFrame = {
    logger.info("ETL Transformations - Transformation Blacklist Started.")
    var columnFilter: Column = new Column("TARGET_LABEL_BAD")
    var dataFrameOutput = df.filter(columnFilter.contains("1"))
    logger.info("ETL Transformations - Transformation Blacklist Finished Successfully.")
    return dataFrameOutput
  }

  def scoringReport(df: DataFrame): DataFrame = {
    logger.info("ETL Transformations - Transformation Scoring Started.")
    var columnFilter: Column = new Column("TARGET_LABEL_BAD")
    var numberTotalOfRecords: Int = df.count().toInt
    var numberOfBlacklisted: Float = df.filter(columnFilter.contains("1")).count().toFloat
    var index: Float =  (numberOfBlacklisted / numberTotalOfRecords)*100
    logger.info("ETL Transformations - Transformation Scoring Finished Successfully.")
    return HDFSUtils.convertListToDF(Seq(index.toString()), Seq("delinquency-rate"))
  }


}
