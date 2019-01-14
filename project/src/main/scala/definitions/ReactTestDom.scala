package definitions

import common.{Libs, TestLibs}
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

      npmDependencies in Compile ++= Seq(
        "react-addons-test-utils" -> "15.6.0"
      )
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactTest.definition
  )

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    Libs.scalajsDom.value,
    
    TestLibs.scalaTestJs.value,
    TestLibs.scalaMockJs.value
  ))

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)
}
