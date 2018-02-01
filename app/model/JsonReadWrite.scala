package model

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class User(val firstName: String, val lastName: String, val mobno: Long)

object JsonReadWrite {

  implicit val userRead = ((__ \ "user" \ "firstName"))
    .read[String]
    .and((__ \ "user" \ "lastName").read[String])
    .and((__ \ "user" \ "mobno").read[Long])(User.apply _)

  implicit val userWrite = ((__ \ "user" \ "firstName"))
    .write[String]
    .and((__ \ "user" \ "lastName").write[String])
    .and((__ \ "user" \ "mobno").write[Long])(unlift(User.unapply))

  def validate(jsValue: Option[JsValue]): Either[User, String] = {
    val jsvalue = Json.toJson(jsValue)
    jsvalue.validate[User] match {
      case JsSuccess(user: User, __) =>
        Left(user)
      case _ =>
        Right("Invalid data entered")
    }

  }

}