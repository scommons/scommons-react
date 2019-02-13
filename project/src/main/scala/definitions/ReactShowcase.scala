package definitions

import sbt.Keys._
import sbt._

object ReactShowcase extends ScalaJsModule {

  override val id = "scommons-react-showcase"

  override val base: File = file("showcase")

  override def definition: Project = super.definition
    .settings(
      skip in publish := true,
      publish := (),
      publishM2 := (),
      publishLocal := ()
    )

  override val internalDependencies: Seq[ClasspathDep[ProjectReference]] = Seq(
    ReactCore.definition,
    ReactTest.definition % "test",
    ReactTestDom.definition % "test"
  )

  override val superRepoProjectsDependencies: Seq[(String, String, Option[String])] = Nil

  override val runtimeDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)

  override val testDependencies: Def.Initialize[Seq[ModuleID]] = Def.setting(Nil)
}
