name := "automata2graphviz"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "bintray-drdozer" at "http://dl.bintray.com/content/drdozer/maven"

libraryDependencies ++= Seq(
    "uk.co.turingatemyhamster" % "gv-core_2.11" % "0.3.1",
    "com.github.scopt" % "scopt" % "3.5.0"
)
