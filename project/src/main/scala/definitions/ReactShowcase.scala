package definitions

import common.TestLibs
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import scoverage.ScoverageKeys.coverageExcludedPackages

import scalajsbundler.BundlingMode
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object ReactShowcase extends ScalaJsModule {

  override val id = "scommons-react-showcase"

  override val base: File = file("showcase")

  override def definition: Project = super.definition
    .settings(
      skip in publish := true,
      publish := ((): Unit),
      publishLocal := ((): Unit),
      publishM2 := ((): Unit),

      coverageExcludedPackages :=
        "scommons.react.showcase.app.ShowcaseReactApp" +
          ";scommons.react.showcase.dom.ReactPortalDemo",

      scalaJSUseMainModuleInitializer := true,
      webpackBundlingMode := BundlingMode.LibraryOnly(),

      npmDevDependencies in Compile ++= Seq(
        "babel-loader" -> "8.0.5",
        "@babel/core" -> "7.4.0",
        "@babel/preset-env" -> "7.4.2",
        "webpack-merge" -> "4.2.1"
      ),

      //dev
      webpackConfigFile in fastOptJS := Some(baseDirectory.value / "client.webpack.config.js"),
      //prod
      webpackConfigFile in fullOptJS := Some(baseDirectory.value / "client.webpack.config.js")
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactCore.definition,
    ReactDom.definition,
    ReactRedux.definition,
    ReactTest.definition % "test"
  )

  override val superRepoProjectsDependencies: Seq[(String, String, Option[String])] = Nil

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    TestLibs.scommonsNodejsTest.value
  ).map(_ % "test"))
}
