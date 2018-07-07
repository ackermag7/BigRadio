ThisBuild / resolvers ++= Seq(
    "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
    Resolver.mavenLocal,
    "Maven Central" at "https://mvnrepository.com/"
)

name := "BigRadio"

version := "1.0"

organization := "org.ackermag"

ThisBuild / scalaVersion := "2.11.12"

val flinkVersion = "1.5.0"

val flinkDependencies = Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "provided")

val jobDependencies = Seq(
  "javazoom" % "jlayer" % "1.0.1"
)

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "org.scalamock" %% "scalamock" % "4.1.0" % Test
)


lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies,
    libraryDependencies ++= jobDependencies,
    libraryDependencies ++= testDependencies
  )

assembly / mainClass := Some("org.ackermag.bigradio.JobRunner")

// make run command include the provided dependencies
Compile / run  := Defaults.runTask(Compile / fullClasspath,
                                   Compile / run / mainClass,
                                   Compile / run / runner
                                  ).evaluated

// stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
Compile / run / fork := true
Global / cancelable := true

// exclude Scala library from assembly
assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)

updateOptions := updateOptions.value.withCachedResolution(true)