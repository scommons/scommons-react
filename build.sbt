import definitions._
import scommons.sbtplugin.project.CommonModule

lazy val `scommons-react` = (project in file("."))
  .settings(CommonModule.settings: _*)
  .settings(ReactModule.settings: _*)
  .settings(
    skip in publish := true,
    publish := (),
    publishM2 := ()
  )
  .settings(
    ideaExcludeFolders += s"${baseDirectory.value}/docs/_site"
  )
  .aggregate(
  `scommons-react-core`
)

lazy val `scommons-react-core` = ReactCore.definition
