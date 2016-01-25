package com.Facebook

import spray.json._

class UserPageEntry(ts: Long, uid: Int, msg: String) {
  var user_id = uid
  var message = msg
  var time_stamp = ts

}
class UserProfileEntry(ts: Long, uid: Int, msg: String) {
  var user_id = uid
  var message = msg
  var time_stamp = ts
}

object JsonConversion extends DefaultJsonProtocol {
  implicit object UserPageinJson extends RootJsonFormat[UserPageEntry] {
    def write(upg: UserPageEntry) = JsObject(
      "time_stamp" -> JsNumber(upg.time_stamp),
      "user_id" -> JsNumber(upg.user_id),
      "message" -> JsString(upg.message)
    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("time_stamp", "user_id", "message") match {
        case Seq(JsString(time_stamp), JsNumber(user_id), JsNumber(message)) =>
          new UserPageEntry(time_stamp.toLong, user_id.toInt, message.toString)
        case _ => throw new DeserializationException("Oops! not User page")
      }
    }
  }
  
  implicit object UserProfileinJson extends RootJsonFormat[UserProfileEntry] {
    def write(upf: UserProfileEntry) = JsObject(
      "time_stamp" -> JsNumber(upf.time_stamp),
      "user_who_mentioned" -> JsNumber(upf.user_id),
      "message" -> JsString(upf.message)
    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("time_stamp", "user_who_mentioned", "message") match {
        case Seq(JsString(time_stamp), JsNumber(user_id), JsNumber(message)) =>
          new UserProfileEntry(time_stamp.toLong, user_id.toInt, message.toString)
        case _ => throw new DeserializationException("Oops! not user profile")
      }
    }
  }
}