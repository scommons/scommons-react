package common

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._
import scommons.sbtplugin.project.CommonLibs

object Libs extends CommonLibs {

  val scommonsNodejsVersion = "0.8.0"
  private val sjsReactJsVer = "0.17.1"

  lazy val sjsReactJsCore = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-core" % sjsReactJsVer)
  lazy val sjsReactJsDom = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-dom" % sjsReactJsVer)
  lazy val sjsReactJsRedux = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-redux" % sjsReactJsVer)
}
