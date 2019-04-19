package com.fexco.poc

import org.apache.spark.sql.{DataFrame, SparkSession}


object HDFSUtils {

  val spark: SparkSession = SparkSession.builder()
    .config("spark.master", "local[2]")
    .getOrCreate()

  val conf = spark.sparkContext
    .hadoopConfiguration

  val fs = org.apache.hadoop.fs.FileSystem.get(conf)

  import spark.implicits._

  def readDataSource(path: String, format: String): DataFrame = {
    var df: DataFrame = null
    if (format == "csv") {
      df = readCSV(path)
    }
    return df
  }

  def readCSV(path: String): DataFrame = {
    val df = spark.read
      .format("csv")
      .option("header", "true")
      .load(path)
    return df
  }

  def readParquet(path: String): DataFrame = {
    val df: DataFrame = null
    return df
  }

  def writeOnHDFS(df: DataFrame, path: String, format: String): Any = {
    if (format == "csv") {
      writeCSVOnHDFS(df, path)
    }
    if (format == "parquet") {
      writeParquetOnHDFS(df, path)
    }

  }

  def writeCSVOnHDFS(df: DataFrame, path: String): Any = {
    df.coalesce(1).write.mode("overwrite").csv(path)
  }

  def writeParquetOnHDFS(df: DataFrame, path: String): Any = {
    df.coalesce(1).write.mode("overwrite").parquet(path)
  }

  def convertListToDF(sequence: Seq[String], sequenceHeaders: Seq[String]): DataFrame = {
    return sequence.toDF(sequenceHeaders: _*)
  }

  def existsFileOnHDFS(path: String): Boolean = {
    return fs.exists(new org.apache.hadoop.fs.Path(path))
  }
}
