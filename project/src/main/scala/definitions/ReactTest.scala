package definitions

import common.{Libs, TestLibs}
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys.coverageExcludedPackages
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object ReactTest extends ScalaJsModule {

  override val id: String = "scommons-react-test"

  override val base: File = file("test")

  override def definition: Project = super.definition
    .settings(
      description := "Core Scala.js, React.js testing utilities",
      coverageExcludedPackages := "scommons.react.test.raw",

      Compile / npmDependencies ++= Seq(
        "react-test-renderer" -> "^17.0.1"
      )
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactCore.definition
  )

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    TestLibs.scalaTestJs.value,
    TestLibs.scalaMockJs.value,
    Libs.scalaJsJavaSecureRandom.value
  ))

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)
}
