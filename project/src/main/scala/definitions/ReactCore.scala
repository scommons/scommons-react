package definitions

import common.{Libs, TestLibs}
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys.coverageExcludedPackages

object ReactCore extends ScalaJsModule {

  override val id: String = "scommons-react-core"

  override val base: File = file("core")

  override def definition: Project = super.definition
    .settings(
      description := "Core Scala.js, React.js UI utilities and components",
      coverageExcludedPackages := "scommons.react.raw",

      requiresDOM in Test := false
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Nil

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    Libs.sjsReactJs.value
  ))

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    TestLibs.scalaTestJs.value,
    TestLibs.scalaMockJs.value
  ).map(_ % "test"))
}
