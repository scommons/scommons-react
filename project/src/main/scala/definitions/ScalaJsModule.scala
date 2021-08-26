package definitions

import org.scalajs.jsenv.nodejs.NodeJSEnv
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._
import scommons.sbtplugin.project.CommonModule.ideExcludedDirectories
import scoverage.ScoverageKeys.{coverageEnabled, coverageScalacPluginVersion}
import scoverage.ScoverageSbtPlugin._

trait ScalaJsModule extends ReactModule {

  override def definition: Project = {
    super.definition
      .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
      .settings(ScalaJsModule.settings: _*)
      .settings(
        //TODO: remove these temporal fixes for Scala.js 1.1+ and scoverage
        coverageScalacPluginVersion := {
          val current = coverageScalacPluginVersion.value
          if (scalaJSVersion.startsWith("0.6")) current
          else "1.4.2" //the only version that supports Scala.js 1.1+
        },
        libraryDependencies ~= { modules =>
          if (scalaJSVersion.startsWith("0.6")) modules
          else modules.filter(_.organization != OrgScoverage)
        },
        libraryDependencies ++= {
          if (coverageEnabled.value) {
            if (scalaJSVersion.startsWith("0.6")) Nil
            else Seq(
              OrgScoverage %% s"${ScalacRuntimeArtifact}_sjs1" % coverageScalacPluginVersion.value,
              OrgScoverage %% ScalacPluginArtifact % coverageScalacPluginVersion.value % ScoveragePluginConfig.name
            )
          }
          else Nil
        }
      )
  }
}

object ScalaJsModule {

  val settings: Seq[Setting[_]] = Seq(
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.CommonJSModule)
        .withSourceMap(false)
        .withESFeatures(_.withUseECMAScript2015(false))
    },
    //Opt-in @ScalaJSDefined by default
    scalacOptions += {
      if (scalaJSVersion.startsWith("0.6")) "-P:scalajs:sjsDefinedByDefault"
      else ""
    },
    requireJsDomEnv in Test := false,
    version in webpack := "4.29.0",
    webpackEmitSourceMaps := false,

    // required for node.js >= v12.12.0
    // see:
    //   https://github.com/nodejs/node/pull/29919
    scalaJSLinkerConfig in Test ~= {
      _.withSourceMap(true)
    },
    jsEnv in Test := new NodeJSEnv(NodeJSEnv.Config().withArgs(List("--enable-source-maps"))),

    ideExcludedDirectories ++= {
      val base = baseDirectory.value
      List(
        base / "build",
        base / "node_modules"
      )
    }
  )
}
