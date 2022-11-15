package common

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._
import scommons.sbtplugin.project.CommonLibs

object Libs extends CommonLibs {

  val scommonsNodejsVersion = "1.0.0-SNAPSHOT"
  private val sjsReactJsVer = "1.0.0"

  lazy val sjsReactJsCore = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-core" % sjsReactJsVer)
  lazy val sjsReactJsDom = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-dom" % sjsReactJsVer)
  lazy val sjsReactJsRedux = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-redux" % sjsReactJsVer)
}
