name := "UserConversations"

version := "1.0"

lazy val `userconversations` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc ,
  cache ,
  ws,
  "com.typesafe.play" %% "play-json" % "2.4.0",
  "com.typesafe.play" %% "play-jdbc-evolutions" % "2.4.0",
  "com.github.mauricio" %% "postgresql-async" % "0.2.18",
  "joda-time" % "joda-time" % "2.1",
  "org.spire-math" %% "cats" % "0.2.0",
  "org.scalaz" %% "scalaz-core" % "7.1.4",
  "org.scalaz" %% "scalaz-concurrent" % "7.1.4",
  specs2 % Test )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.b" +
  "intray.com/scalaz/releases"

routesGenerator := InjectedRoutesGenerator
