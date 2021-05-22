package definitions

import common.{Libs, TestLibs}
import sbt.Keys._
import sbt._

object ReactRedux extends ScalaJsModule {

  override val id: String = "scommons-react-redux"

  override val base: File = file("redux")

  override def definition: Project = super.definition
    .settings(
      description := "Scala.js facades for react-redux utilities and components"
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactCore.definition,
    ReactTest.definition % "test"
  )

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    Libs.sjsReactJsRedux.value
  ))

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Seq(
    TestLibs.scommonsNodejsTest.value
  ).map(_ % "test"))
}
