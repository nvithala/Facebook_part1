package com.Facebook

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.IO
import akka.routing.RoundRobinRouter
import spray.can.Http
import spray.routing.HttpService
import scala.concurrent.duration._

sealed trait sever_specific
case class Stop() extends sever_specific
case class Print() extends sever_specific

object Main extends App {
  val no_of_users =  args(0).toInt
  var posts_rcvd = 0
  var requests_rcvd = 0

  val friend_list = DataInitialization.initialize_friendlist(no_of_users)
  var user_page = DataInitialization.initialize_page(no_of_users)
  var user_profile = DataInitialization.initialize_profile(no_of_users)
  var photos = DataInitialization.initialize_photos(no_of_users)
  var albums = DataInitialization.initialize_albums(no_of_users)
  var user_first_name = DataInitialization.initialize_first_name(no_of_users)
  var user_last_name = DataInitialization.initialize_last_name(no_of_users)
  var user_age = DataInitialization.initialize_age(no_of_users)




  println("All initiallizations at server are Done")
  println("Facebook server is up and running")
  println()

  implicit val system = ActorSystem("FbServerSimulator")
  val print_work = system.actorOf(Props[Prinitng_work], "print_work")

  val spray_server = system.actorOf(Props[Server], "server")
  IO(Http)(system) ! Http.Bind(spray_server, "127.0.0.1", port = 5220)

  readLine()
  println("Shutting down...")
  system.shutdown()
}


class Prinitng_work extends Actor {
  override def preStart() {
    import context.dispatcher
    context.system.scheduler.scheduleOnce(600 seconds, self, Stop)
    context.system.scheduler.schedule(30 seconds, 3 seconds, self, Print)
  }
  def receive = {
    case `Stop` =>
      println("-----------------System is shutting down--------------------")
      context.system.shutdown()
      System.exit(0)
    case `Print` =>
      println("Number of posts received :" + Main.posts_rcvd)
      println("Number of message delivered :" + Main.requests_rcvd)
      println()
  }
}


class Server extends Actor with SprayService{
  def actorRefFactory = context
  def receive = runRoute(FbRouting)
}

trait SprayService extends HttpService {
  val serveractor = actorRefFactory.actorOf(Props[MainActor].withRouter(RoundRobinRouter(nrOfInstances = 5)), name = "serverrouter")
  val FbRouting =
    path("post" / IntNumber / IntNumber / IntNumber / Segment) { (id, post_type, name, msg) =>
      requestContext =>
        serveractor ! Post(id, post_type, name, msg, requestContext)
    } ~
      path("page" / IntNumber) { (id) =>
        requestContext =>
          serveractor ! Page(id, requestContext)
      } ~
      path("profile" /IntNumber) { (id) =>
        requestContext =>
          serveractor ! Profile(id, requestContext)
      } ~
      path("friendlist" / IntNumber) { (id) =>
        requestContext =>
          serveractor ! GetFriendlist(id, requestContext)
      } ~
      path("getPost" / IntNumber) { (id) =>
        requestContext =>
          serveractor ! Page(id, requestContext)
      } ~
      path("photo" / IntNumber /IntNumber) { (id,pic_id) =>
        requestContext =>
          serveractor ! GetPhoto(id,pic_id,requestContext)
      } ~
      path("album" / IntNumber /IntNumber/ IntNumber) { (id,album_id,pic_id) =>
        requestContext =>
          serveractor ! GetAlbum(id,album_id,pic_id,requestContext)
      }
}