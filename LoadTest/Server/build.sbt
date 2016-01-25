name := "FacebookServer"
 
version := "0.1"
 
scalaVersion := "2.11.6"
 
resolvers ++= Seq(
    "spray repo" at "http://repo.spray.io/"
)
 
libraryDependencies ++= {
    val akka_version = "2.3.9"
    val spray_version = "1.3.1"
    Seq(
        "com.typesafe.akka" %% "akka-actor" % akka_version,
        "io.spray" %% "spray-can" % spray_version,
        "io.spray" %% "spray-routing" % spray_version,
        "io.spray" %% "spray-json" % spray_version
        )
}
 
    