import sbt._

object Dependencies {
  val versions = new {
    val spark   = "2.3.2"
    val guava   = "20.0"
    val jackson = "2.9.4"

    val scalaTest     = "3.0.5"
    val scalaCheck    = "1.14.0"
    val scalaMock     = "4.1.0"
    val dockerTestkit = "0.9.8"
  }

  lazy val main = Seq(
    "org.apache.spark" %% "spark-core" % versions.spark % Provided,
    "org.apache.spark" %% "spark-sql"  % versions.spark % Provided
  )

  lazy val test = Seq(
    "org.scalatest"  %% "scalatest"                   % versions.scalaTest,
    "org.scalacheck" %% "scalacheck"                  % versions.scalaCheck,
    "org.scalamock"  %% "scalamock"                   % versions.scalaMock,
    "com.whisk"      %% "docker-testkit-scalatest"    % versions.dockerTestkit,
    "com.whisk"      %% "docker-testkit-impl-spotify" % versions.dockerTestkit
  ).map(_ % Test)

  lazy val overrides = Seq(
    "com.google.guava"             % "guava"                 % versions.guava,
    "com.fasterxml.jackson.core"   % "jackson-core"          % versions.jackson,
    "com.fasterxml.jackson.core"   % "jackson-databind"      % versions.jackson,
    "com.fasterxml.jackson.core"   % "jackson-annotations"   % versions.jackson,
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % versions.jackson
  )
}
