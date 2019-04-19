package com.fexco.poc

import com.typesafe.scalalogging.LazyLogging


object ETL extends LazyLogging {


  def main(args: Array[String]): Unit = {
    val INPUT_FILE = 0;
    val INPUT_FORMAT = 1;
    val OUTPUT_FILE = 2
    val OUTPUT_FORMAT = 3
    val MODEL = 4

    if (args.length < 5) {
      logger.error("ETL Engine - Illegal Argument Input for Process.")
      throw new IllegalArgumentException()
    }
    invokeETLEngine(args(INPUT_FILE), args(INPUT_FORMAT), args(OUTPUT_FILE), args(OUTPUT_FORMAT), args(MODEL))
  }

  def invokeETLEngine(input_file: String, input_format: String, output_file: String, output_format: String, model: String): Unit = {
    logger.info("ETL INFO - Starting ETL Engine")
    ETLEngine.invokeEngine(input_file, input_format, output_file, model)
    logger.info("ETL INFO - Shutting Down Engine")

  }

}