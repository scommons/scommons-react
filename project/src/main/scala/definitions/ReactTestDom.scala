package definitions

import common.TestLibs
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys.coverageExcludedPackages

import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object ReactTestDom extends ScalaJsModule {

  override val id: String = "scommons-react-test-dom"

  override val base: File = file("test-dom")
  
  override def definition: Project = super.definition
    .settings(
      description := "Web DOM Scala.js, React.js testing utilities",
      coverageExcludedPackages := "scommons.react.test.dom.raw",

      requireJsDomEnv in Test := true
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
