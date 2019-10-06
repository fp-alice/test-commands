name := "testcommands"

version := "0.1"

scalaVersion := "2.11.12"

scalacOptions += "-Ypartial-unification"

//resolvers +=
//  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "1.2.0",
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.tpolecat" %% "atto-core" % "0.6.5",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

