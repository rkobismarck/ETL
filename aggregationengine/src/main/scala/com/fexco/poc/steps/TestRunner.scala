package com.fexco.poc.steps

import cucumber.api.CucumberOptions
import cucumber.api.cli.Main
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith


@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("test-classes/features/acceptance"),
  glue     = Array("test-classes/features/steps"),
  plugin = Array("pretty", "html:target/cucumber/test-report.html")
)
class TestRunner {
  def main(args: Array[String]): Unit = {
    Main.main(args)
  }
}
