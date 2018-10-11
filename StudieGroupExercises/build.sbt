name := "StudieGroupExercises"

version := "0.1"

scalaVersion := "2.12.7"


scalacOptions += "-Ypartial-unification"

//functional programming librabry
libraryDependencies += "org.typelevel" %% "cats-core" % "1.3.1"

//property based testing
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"

//unit testing
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
//addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.3")