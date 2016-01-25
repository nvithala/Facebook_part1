package com.Facebook

import akka.actor._
import com.Facebook.JsonConversion._
import spray.http.HttpResponse
import spray.json._
import spray.routing.RequestContext

case class Post(id: Int, post_type: Int, name: Int, msg: String, requestContext: RequestContext)
case class Page(id: Int, requestContext: RequestContext)
case class Profile(id: Int, requestContext: RequestContext)
case class GetFriendlist(id: Int, requestContext: RequestContext)
case class GetAlbum(id:Int,album_id:Int,pic_id:Int,requestContext:RequestContext)
case class GetPhoto(id:Int,pic_id:Int,requestContext:RequestContext)

//Main Actor where requests are processed
class MainActor extends Actor {

  def receive = {
    case Post(id, post_type, name, msg, requestContext) =>
			Main.posts_rcvd += 1
			//println("Post number :" + Main.posts_recieved )
			val time_stamp = System.currentTimeMillis
			//Add it to your own page, profile and
			val user_page_entry = new UserPageEntry(time_stamp, id, msg)
			Main.user_page(id) = Main.user_page(id) :+ user_page_entry
			val user_profile_entry = new UserProfileEntry(time_stamp, id, msg)
			Main.user_profile(id) = Main.user_profile(id) :+ user_profile_entry
			if (post_type == 0) {
				if (Main.friend_list(id).size > 0) {
					for (j <- Main.friend_list (id) ) {
						Main.user_page (j) :+= user_page_entry
						if (Main.user_page (j).size >= 100) {
							Main.user_page (j).drop (Main.user_page (j).size - 100)
						}
					}
				}
			}
			else {
				val user_page_entry = new UserPageEntry(time_stamp, id, msg)
				Main.user_page(id) = Main.user_page(id) :+ user_page_entry
				val my_timeline = new UserProfileEntry(time_stamp, id, msg)
				Main.user_profile(id) = Main.user_profile(id) :+ my_timeline
				val user_profile_entry = new UserProfileEntry(time_stamp, id, msg)
				if(Main.friend_list(id).size > 0) {
					for(j <- Main.friend_list(id)) {
						Main.user_page(j) :+= user_page_entry
						if(Main.user_page(j).size >= 100)
							Main.user_page(j).drop(Main.user_page(j).size - 100)
					}
					Main.user_profile(name) :+= user_profile_entry
					if(Main.user_profile(name).size >= 50)
						Main.user_profile(name).drop(Main.user_profile(name).size - 50)
				}
			}
	  	var post_json = ""
			post_json += user_page_entry.toJson
	  	requestContext.complete(HttpResponse(entity = post_json))

		case Page(id, requestContext) =>
			Main.requests_rcvd += 1
		 	var reply = Main.user_page(id)
			var j_reply = ""
			if(reply.length == 0)
	    	j_reply = "Nothing to show"
	  	else {
				for(item <- reply) {
					j_reply += "\n" + item.toJson
				}
	  	}
	  	requestContext.complete(HttpResponse(entity = j_reply))
	  
		case Profile(id, requestContext) =>
			Main.requests_rcvd += 1
			var reply = Main.user_profile(id)
			var j_reply = ""
			if(reply.length == 0)
				j_reply = "No posts on my wall yet."
			else {
				for(item <- reply) {
					j_reply += "\n" + item.toJson
				}
			}

			var profile_details : String = "First name :" + Main.user_first_name(id) + "\n" + "Last name :" + Main.user_last_name(id) + "\n" + "Age :"+ Main.user_age(id) + "\n" + j_reply
			requestContext.complete(HttpResponse(entity = profile_details))
	  
		case GetFriendlist(id, requestContext) =>
			Main.requests_rcvd += 1
			var reply = Main.friend_list(id)
			var j_reply = ""
			if(reply == null)
				j_reply = "No friends made yet."
			else
				j_reply += reply.toJson
			requestContext.complete(HttpResponse(entity = j_reply))

		case GetPhoto(id,pic_id,requestContext) =>
			Main.requests_rcvd += 1
			var reply1:List[String] = Main.photos(id)
			var reply = reply1(pic_id)
			var json_reply = ""
			if(reply == null)
				json_reply = "No photos yet."
			else
				json_reply += reply.toJson
			requestContext.complete(HttpResponse(entity = json_reply))

		case GetAlbum(id,album_id,pic_id,requestContext) =>
			Main.requests_rcvd += 1
			var reply1:List[List[String]] = Main.albums(id)
			var reply2:List[String] = reply1(album_id)
			var reply:String = reply2(pic_id)
			var json_reply = ""
			if(reply == null)
				json_reply = "No albums yet."
			else
				json_reply += reply.toJson
			requestContext.complete(HttpResponse(entity = json_reply))
	}
}