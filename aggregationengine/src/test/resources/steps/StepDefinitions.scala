package com.fexco.poc

import cucumber.api.scala.{EN, ScalaDsl}
import org.apache.spark.sql.{Column, DataFrame}

class StepDefinitions extends ScalaDsl with EN {
  val ENVIRONMENT_PATH = getClass.getResource("/");
  var dfRawData: DataFrame = null;
  var dfMutedData: DataFrame = null;
  var mutedDataScoring: DataFrame = null
  var validPath: String = null;
  var validFormat: String = null;

  // COMMON DEFINITION.
  Given("""^Acceptance Testing$""") { () =>
  }
  When("""^a valid "([^"]*)" for the raw data and "([^"]*)" format$""") { (path: String, format: String) =>
    validPath = path;
    validFormat = format;
  }
  Then("""^result should be a DataFrame with data""") { () =>
    dfRawData = HDFSUtils.readDataSource(ENVIRONMENT_PATH + validPath, validFormat)
    dfRawData.show(5)
    assert(dfRawData.count() > 0)
  }
  When("""^i provide a "([^"]*)" report$""") { (model: String) => {
    dfMutedData = ETLEngine.applyTransformation(model, dfRawData)
    assert(dfRawData.count() > dfMutedData.count())
  }
  }
  When("""^i save it in the fs with "([^"]*)" path and "([^"]*)" for output format""") { (path: String, format: String) => {
    try {
      HDFSUtils.writeOnHDFS(dfMutedData, ENVIRONMENT_PATH + path, format)
      assert(true)
    } catch {
      case e => assert(false)
    }
  }
  }
  Then("""^i should be able to validate existence of the output in "([^"]*)" path$""") { (path: String) => {
    assert(true, HDFSUtils.existsFileOnHDFS(ENVIRONMENT_PATH + path))
  }
  }

  // BlackList REPORT DEFINITION.
  Then("""^it should not contain any value 0 in the "([^"]*)" column$""") { (column: String) =>
    var columnFilter: Column = new Column(column)
    var numberOfExceptions = dfMutedData.filter(columnFilter.contains("0")).count()
    assert(0 == numberOfExceptions)
  }
  // Scoring REPORT DEFINITION.
  Then("""^it should return a new DataFrame who contains the calculated value for the Delinquency-Rate$""") { () =>
    mutedDataScoring = ETLEngine.applyTransformation("scoring", dfRawData)
    assert(mutedDataScoring.isInstanceOf[DataFrame])
  }
  When("""^i perform a validation on the calculated value, the Delinquency-Rate value should be "([^"]*)"""") { (expectedRate: String) =>
    // In this case we're expecting just a single row value.

    println("Array ->" +mutedDataScoring.head().getString(0).toFloat)
    println("Rate  ->" +expectedRate.toFloat)
    assert(mutedDataScoring.head().getString(0).toFloat == expectedRate.toFloat)
  }
}
