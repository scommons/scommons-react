package definitions

import common.TestLibs
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

      npmDependencies in Compile ++= Seq(
        "react-test-renderer" -> "^16.8.0"
      ),

//      npmResolutions in Test := Map(
//        "react" -> "^16.8.0",
//        "react-dom" -> "^16.8.0" //TODO: remove dependency on the react-dom !!!
//      ),
      requireJsDomEnv in Test := false
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactCore.definition
  )

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    TestLibs.scalaTestJs.value,
    TestLibs.scalaMockJs.value
  ))

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)
}
