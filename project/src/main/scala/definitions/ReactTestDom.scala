package definitions

import common.TestLibs
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys._

object ReactTestDom extends ScalaJsModule {

  override val id: String = "scommons-react-test-dom"

  override val base: File = file("test-dom")
  
  override def definition: Project = super.definition
    .settings(
      description := "Web DOM Scala.js, React.js testing utilities",
      
      coverageEnabled := false, //until this is fixed: https://github.com/scalacenter/scalajs-bundler/issues/197
      coverageExcludedPackages := "scommons.react.test.dom.raw"
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactDom.definition,
    ReactTest.definition
  )

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    TestLibs.scalaTestJs.value,
    TestLibs.scalaMockJs.value
  ))

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)
}
