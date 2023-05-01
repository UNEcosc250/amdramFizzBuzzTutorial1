lazy val root = (project in file(".")).
  settings(
    name := "Actors with Amdram",
    version := "2022.0",
    scalaVersion := "3.2.2"
  )

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "com.wbillingsley" %% "amdram" % "0.0.0+5-cd0f1dd9-SNAPSHOT",
)

Compile / run / fork := true
