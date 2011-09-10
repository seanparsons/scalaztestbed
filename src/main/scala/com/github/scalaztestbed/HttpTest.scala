package com.github.scalaztestbed

import unfiltered.netty.channel.Planify
import java.util.concurrent._
import scalaz._
import Scalaz._
import scalaz.concurrent._
import org.jboss.netty.handler.codec.http.{HttpMessage}
import unfiltered.request.{HttpRequest, GET}
import unfiltered.netty.{Http, ReceivedMessage}
import unfiltered.response.{Ok, ResponseString}

object HttpTest extends App {
  val pool = Executors.newFixedThreadPool(30)
  val strategy = Strategy.Executor(pool)
  val actors = List.fill(10)(actor{(request: ReceivedMessage) => request.respond(Ok ~> ResponseString("Wicked"))})
  val actorIterator = new CyclicIterator(actors)

  val plan = Planify{
    case GET(request: HttpRequest[ReceivedMessage]) => promise(request.underlying)(strategy) to actorIterator.next()
  }
  val http = Http(8080).handler(plan).run()
  pool.shutdown()
}