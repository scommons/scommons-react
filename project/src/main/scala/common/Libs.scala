package common

import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._
import scommons.sbtplugin.project.CommonLibs

object Libs extends CommonLibs {

  val scommonsNodejsVersion = "0.4.0"
  private val sjsReactJsVer = "0.15.0"

  lazy val sjsReactJs = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs" % sjsReactJsVer)
  lazy val sjsReactJsRouterDom = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-router-dom" % sjsReactJsVer)
  lazy val sjsReactJsRouterRedux = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-router-redux" % sjsReactJsVer)
  lazy val sjsReactJsRedux = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-redux" % sjsReactJsVer)
  lazy val sjsReactJsReduxDevTools = Def.setting("org.scommons.shogowada" %%% "scalajs-reactjs-redux-devtools" % sjsReactJsVer)
}
