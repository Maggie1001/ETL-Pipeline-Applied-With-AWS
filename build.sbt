name := "hotels-spark"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Dependencies.main
libraryDependencies ++= Dependencies.test
dependencyOverrides ++= Dependencies.overrides

enablePlugins(JavaAppPackaging)

mainClass in Compile := Some("by.maggie.bigdata101.hotels.Main")
mainClass in assembly := Some("by.maggie.bigdata101.hotels.Main")

assemblyJarName in assembly := "hotels-spark.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _ @_*) => MergeStrategy.discard
  case _                           => MergeStrategy.first
}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
