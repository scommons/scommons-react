package definitions

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

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

      scalaJSUseMainModuleInitializer := true,
      webpackBundlingMode := BundlingMode.LibraryOnly(),

      //TODO: move to ReactCore module
      npmDevDependencies in Compile ++= Seq(
        "babel-core" -> "6.26.3",
        "babel-loader" -> "7.1.5", //see: https://github.com/babel/babel-loader/tree/7.x
        //"@babel/preset-env" -> "7.4.2",
        "babel-preset-es2015" -> "6.24.1",
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
    ReactTest.definition % "test",
    ReactTestDom.definition % "test"
  )

  override val superRepoProjectsDependencies: Seq[(String, String, Option[String])] = Nil

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)
}
