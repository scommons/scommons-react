package definitions

import common.{Libs, TestLibs}
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys.coverageExcludedPackages

import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object ReactDom extends ScalaJsModule {

  override val id: String = "scommons-react-dom"

  override val base: File = file("dom")

  override def definition: Project = super.definition
    .settings(
      description := "Scala.js facades for React.js DOM utilities and components",
      coverageExcludedPackages := "scommons.react.dom.raw",

      requireJsDomEnv in Test := true
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactCore.definition
  )

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    Libs.scalajsDom.value
  ))

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    TestLibs.scalaTestJs.value,
    TestLibs.scalaMockJs.value
  ).map(_ % "test"))
}
