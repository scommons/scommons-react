import definitions._
import scommons.sbtplugin.project.CommonModule
import scommons.sbtplugin.project.CommonModule.ideExcludedDirectories

lazy val `scommons-react` = (project in file("."))
  .settings(CommonModule.settings: _*)
  .settings(ReactModule.settings: _*)
  .settings(
    publish / skip := true,
    publish := ((): Unit),
    publishLocal := ((): Unit),
    publishM2 := ((): Unit)
  )
  .settings(
    ideExcludedDirectories += baseDirectory.value / "docs" / "_site"
  )
  .aggregate(
  `scommons-react-core`,
  `scommons-react-dom`,
  `scommons-react-redux`,
  `scommons-react-test`,
  `scommons-react-test-dom`,
  `scommons-react-showcase`
)

lazy val `scommons-react-core` = ReactCore.definition
lazy val `scommons-react-dom` = ReactDom.definition
lazy val `scommons-react-redux` = ReactRedux.definition
lazy val `scommons-react-test` = ReactTest.definition
lazy val `scommons-react-test-dom` = ReactTestDom.definition
lazy val `scommons-react-showcase` = ReactShowcase.definition
