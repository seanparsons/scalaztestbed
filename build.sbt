

scalaVersion := "2.9.0-1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.1"

libraryDependencies += "net.databinder" %% "unfiltered-netty" % "0.4.1"

initialCommands in console := """
import scalaz._
import Scalaz._
import scalaz.concurrent._
import java.util.concurrent._
import com.github.scalaztestbed._
implicit val pool = Executors.newFixedThreadPool(10)
implicit val strategy = Strategy.Executor
"""