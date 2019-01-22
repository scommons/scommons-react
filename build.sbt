import definitions._
import scommons.sbtplugin.project.CommonModule
import scommons.sbtplugin.project.CommonModule.ideExcludedDirectories

lazy val `scommons-react` = (project in file("."))
  .settings(CommonModule.settings: _*)
  .settings(ReactModule.settings: _*)
  .settings(
    skip in publish := true,
    publish := (),
    publishM2 := ()
  )
  .settings(
    ideExcludedDirectories += baseDirectory.value / "docs" / "_site"
  )
  .aggregate(
  `scommons-react-core`,
  `scommons-react-test`,
  `scommons-react-test-dom`
)

lazy val `scommons-react-core` = ReactCore.definition
lazy val `scommons-react-test` = ReactTest.definition
lazy val `scommons-react-test-dom` = ReactTestDom.definition
