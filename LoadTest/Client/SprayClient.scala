package com.facebookClient

import akka.actor.{Props, ActorRef, Actor, ActorSystem}
import akka.util.Timeout
import spray.client.pipelining.sendReceive
import spray.http.{HttpRequest, HttpResponse}
import spray.httpx.RequestBuilding
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Random

sealed trait message
case class SendPost() extends message
case class RequestFacebookPage() extends message
case class RequestFacebookProfile() extends message
case class RequestFacebookPicture() extends message
case class RequestFacebookAlbum() extends message
case class Stop() extends message


object UserSimulation extends App with RequestBuilding {

	var count = 0;
	val no_of_actors = args(0).toInt
	//val client_no = args(1).toInt
	val load = args(1)
	var actorArray = Array.ofDim[ActorRef](no_of_actors / 5)

	//When multiple actor invoking is required
	//val multiplier = 2 * (client_no - 1)
	//val begin = multiplier * 10000
	//val end = (no_of_actors / 5) + begin - 1
	val system = ActorSystem("Client")

	for (i <- 0 until no_of_actors) {
		val user = system.actorOf(Props(new User(no_of_actors,load)), name = i.toString)
	}

	if (count ==no_of_actors ){
		system.shutdown()
		System.exit(0)
	}

	class User(no_of_actors:Int,load:String) extends Actor {
		var text = "sdajkhfjksdhfhjkewhfjkd"
		var posting_frequency = 0
		var pagecheck_frequency = 0
		var profilecheck_frequency = 0
		var picturecheck_frquency = 0
		var albumcheck_frequency = 0
		var myid = self.path.name.toInt

		/*
		if(myid >= 0 && myid < (0.05*no_of_actors).toInt) {
			if(load == "light") {
				posting_frequency = 10
				pagecheck_frequency = 1000
			}
			else {
				posting_frequency = 5
				pagecheck_frequency = 250
			}
			profilecheck_frequency = -1
		}

		else if(myid >= (0.05*no_of_actors).toInt && myid < (0.1925*no_of_actors).toInt) {
			if(load == "light") {
				posting_frequency = 20
				pagecheck_frequency = 40
			}
			else {
				posting_frequency = 40
				pagecheck_frequency = 30
			}
			profilecheck_frequency = 500
		}
		//fraction of average fb users
		else if(myid >= (0.1925*no_of_actors).toInt && myid < (0.45*no_of_actors).toInt) {
			posting_frequency = 100
			pagecheck_frequency = 30
			profilecheck_frequency = 2000
		}
		//Fraction of people who check pages frequently but dont post
		else if(myid >= (0.45*no_of_actors).toInt && myid < (0.87*no_of_actors).toInt) {
			posting_frequency = -1
			pagecheck_frequency = 20
			profilecheck_frequency = 2000
		}

		else {
			posting_frequency = -1
			pagecheck_frequency = -1
			profilecheck_frequency = -1
		}
		*/

		//Simulation using statistics
		//Users who are active frequenlty
		if(myid >= 0 && myid < (0.05*no_of_actors).toInt) {
			if(load == "light") {
				posting_frequency = 10
				pagecheck_frequency = 1000
				albumcheck_frequency = 1000
				picturecheck_frquency = 500

			}
			else {
				posting_frequency = 2
				pagecheck_frequency = 80
				albumcheck_frequency = 150
				picturecheck_frquency = 80
			}
			profilecheck_frequency = 2000
		}
		//Users who are the most active
		else if(myid >= (0.05*no_of_actors).toInt && myid < (0.1925*no_of_actors).toInt) {
			if(load == "light") {
				posting_frequency = 90
				pagecheck_frequency = 40
				albumcheck_frequency = 150
				picturecheck_frquency = 80
			}
			else {
				posting_frequency = 45
				pagecheck_frequency = 20
				albumcheck_frequency = 60
				picturecheck_frquency = 50
			}
			profilecheck_frequency = 1000
		}
		//Users who check their news feed frequently
		else if(myid >= (0.1925*no_of_actors).toInt && myid < (0.89*no_of_actors).toInt) {
			posting_frequency = -1
			pagecheck_frequency = 20
			profilecheck_frequency = 2000
			albumcheck_frequency = 1000
			picturecheck_frquency = 200
		}
		//Least active users
		else {
			posting_frequency = -1
			pagecheck_frequency = -1
			profilecheck_frequency = -1
			albumcheck_frequency = -1
			picturecheck_frquency = -1
		}

		implicit val requestTimeout = Timeout(60 seconds)
		implicit val system = ActorSystem("spray-client")
		implicit val executionContext = system.dispatcher
		val pipeline: HttpRequest => Future[HttpResponse] = sendReceive


		//Fixing the frequency at which requests are sent to Spray Server
		override def preStart() {
			if(posting_frequency > -1)
				context.system.scheduler.schedule(30 milliseconds, posting_frequency seconds, self, SendPost)
			if(pagecheck_frequency > -1)
				context.system.scheduler.schedule(30 milliseconds, pagecheck_frequency seconds, self, RequestFacebookPage)
			if(profilecheck_frequency > -1)
				context.system.scheduler.schedule(30 milliseconds, profilecheck_frequency seconds, self, RequestFacebookProfile)
			if(profilecheck_frequency > -1)
				context.system.scheduler.schedule(30 milliseconds, picturecheck_frquency seconds, self, RequestFacebookPicture)
			if(profilecheck_frequency > -1)
				context.system.scheduler.schedule(30 milliseconds, albumcheck_frequency seconds, self, RequestFacebookProfile)

			context.system.scheduler.scheduleOnce(600 seconds, self, Stop)
		}

		def receive = {

			case `SendPost` =>
				val post_type = Random.nextInt(2).abs
				val id = myid
				var send_post_to = 0
				if (post_type != 0) {
					send_post_to = Random.nextInt(no_of_actors)
				}

				//Variable input for posting simulated here
				val simulation_response: Future[HttpResponse] = pipeline {
					Get("http://127.0.0.1:5220/post/" + id + "/" + post_type + "/" + send_post_to + "/" + text)
				}

			//Commented section if we actually want to print the JSON response sent
			/*
      simulation_response.foreach {
        response =>
          println(s"Request Homepage :\n${response.entity.asString}")
      }
      */

			case `RequestFacebookPage` =>
				val simulation_response: Future[HttpResponse] = pipeline {
					Get("http://127.0.0.1:5220/page/" + myid)
				}

			//Commented section if we actually want to print the JSON response sent
			/*
      simulation_response.foreach {
        response =>
          println(s"Request Homepage :\n${response.entity.asString}")
      }
      */
			case `RequestFacebookProfile` =>
				val simulation_response: Future[HttpResponse] = pipeline {
					Get("http://127.0.0.1:5220/profile/" + myid)
				}

			//Commented section if we actually want to print the JSON response sent
			/*
      simulation_response.foreach {
        response =>
          println(s"Request MentionFeed :\n${response.entity.asString}")
      }
      */
			case `RequestFacebookPicture` =>
				val request_id = Random.nextInt(no_of_actors)
				val re_pic_id = Random.nextInt(3)
				//println("Pic id requested is"+re_pic_id)
				val simulation_response: Future[HttpResponse] = pipeline {
					Get("http://127.0.0.1:5220/photo/" + request_id + "/" + re_pic_id)
				}
			//Commented section if we actually want to print the JSON response sent
			/*
      simulation_response.foreach {
        response =>
          println(s"Request Homepage :\n${response.entity.asString}")
      }
      */

			case `RequestFacebookAlbum` =>
				val request_id = Random.nextInt(no_of_actors-1)
				val re_alb_id = Random.nextInt(2)
				//println("Album id requested is"+re_alb_id)
				val re_pic_id = Random.nextInt(2)
				//println("Pic id requested is"+re_pic_id)
				val simulation_response: Future[HttpResponse] = pipeline {
					Get("http://127.0.0.1:5220/album/"+ request_id + "/" + re_alb_id + "/" + re_pic_id)
				}
			//Commented section if we actually want to print the JSON response sent
			/*
      simulation_response.foreach {
        response =>
          println(s"Request Homepage :\n${response.entity.asString}")
      }
      */

			case Stop() =>
				context.stop(self)
				count += 1
				//context.system.shutdown()
				//System.exit(0)

		}
	}

}