//resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

//addSbtPlugin(("org.scommons.sbt" % "sbt-scommons-plugin" % "1.0.0-SNAPSHOT").changing())
addSbtPlugin("org.scommons.sbt" % "sbt-scommons-plugin" % "1.0.0")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.5")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.9.3")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.2")
