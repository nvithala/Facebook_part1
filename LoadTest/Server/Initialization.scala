package com.Facebook

import scala.collection.mutable.ListBuffer
import scala.util.Random

object DataInitialization {

	def initialize_friendlist(no_of_actors: Int): Array[List[Int]] = {
		println("*****Initializing Friend list*****")

		var friends_list = new Array[List[Int]](no_of_actors)

		friends_list(0) = for (i <- List.range((0.005 * no_of_actors).toInt, (0.035 * no_of_actors).toInt)) yield i
		friends_list(1) = for (i <- List.range((0.03501 * no_of_actors).toInt, (0.05501 * no_of_actors).toInt)) yield i

		for (i <- (0.05501 * no_of_actors).toInt until (0.05791 * no_of_actors).toInt)
			friends_list(i) = for (j <- List.range((0.05501 * no_of_actors).toInt, no_of_actors) if (j % (0.00094 * no_of_actors).toInt == 0 && j != i)) yield j
		println("*****")

		for (i <- (0.05791 * no_of_actors).toInt until (0.06191 * no_of_actors).toInt)
			friends_list(i) = for (j <- List.range((0.05501 * no_of_actors).toInt, no_of_actors) if (j % (0.00189 * no_of_actors).toInt == 0 && j != i)) yield j
		println("*****")

		for (i <- (0.06191 * no_of_actors).toInt until (0.06591 * no_of_actors).toInt)
			friends_list(i) = for (j <- List.range((0.05501 * no_of_actors).toInt, no_of_actors) if (j % (0.0063 * no_of_actors).toInt == 0 && j != i)) yield j
		println("*****")

		for (i <- (0.06591 * no_of_actors).toInt until (0.08541 * no_of_actors).toInt)
			friends_list(i) = for (j <- List.range((0.05501 * no_of_actors).toInt, no_of_actors) if (j % (0.00945 * no_of_actors).toInt == 0 && j != i)) yield j
		println("*****")

		for (i <- (0.08541 * no_of_actors).toInt until (0.10491 * no_of_actors).toInt)
			friends_list(i) = for (j <- List.range((0.05501 * no_of_actors).toInt, no_of_actors) if (j % (0.0189 * no_of_actors).toInt == 0 && j != i)) yield j
		println("*****")

		for (i <- (0.10491 * no_of_actors).toInt until (0.19491 * no_of_actors).toInt)
			friends_list(i) = for (j <- List.range((0.05501 * no_of_actors).toInt, no_of_actors) if (j % (0.0945 * no_of_actors).toInt == 0 && j != i)) yield j
		println("*****")

		for (i <- (0.19491 * no_of_actors).toInt until (0.25491 * no_of_actors).toInt)
			friends_list(i) = for (j <- List.range((0.05501 * no_of_actors).toInt, no_of_actors) if (j % (0.189 * no_of_actors).toInt == 0 && j != i)) yield j
		println("*****")

		for (i <- (0.25491 * no_of_actors).toInt until (0.99999 * no_of_actors).toInt) {
			var num = 0
			num = (0.51 * no_of_actors).toInt + Random.nextInt((0.99999 * no_of_actors).toInt - (0.51 * no_of_actors).toInt + 1)
			friends_list(i) = List(num)
		}
		println("*****")

		for (i <- 2 until (0.05500 * no_of_actors).toInt) {
			var num = 0
			num = (0.51 * no_of_actors).toInt + Random.nextInt(((0.99999 * no_of_actors).toInt) - ((0.51* no_of_actors).toInt) + 1)
			friends_list(i) = List(num)
		}

		println("*****Done initializing Friend list*****")
		return friends_list
	}

	def initialize_page(no_of_actors: Int): Array[Vector[UserPageEntry]] = {
		var user_page = new Array[Vector[UserPageEntry]](no_of_actors)
		for (i <- 0 until no_of_actors)
			user_page(i) = Vector[UserPageEntry]()
		println("*****Done initializing Facebook Page for Users*****")
		return user_page
	}

	def initialize_profile(no_of_actors: Int): Array[Vector[UserProfileEntry]] = {
		var user_wall = new Array[Vector[UserProfileEntry]](no_of_actors)
		for (i <- 0 until (no_of_actors))
			user_wall(i) = Vector[UserProfileEntry]()
		println("*****Done initializing Facebook Page for Users*****")
		return user_wall

	}

	def initialize_first_name(no_of_actors:Int) : Array[String] = {
		var user_first_name = new Array[String](no_of_actors)
		println("*****Initializing Profile for Users*****")
		for(i <- 0 to ((no_of_actors*0.2).toInt - 1)) {
			user_first_name(i) = gen_ran_string(7)
		}

		for(i <- ((no_of_actors * 0.2).toInt) to ((no_of_actors*0.4).toInt - 1)) {
			user_first_name(i) = gen_ran_string(7)
		}

		for(i <- (no_of_actors*0.6).toInt to ((no_of_actors*0.8).toInt - 1)) {
			user_first_name(i) = gen_ran_string(7)
		}

		for(i <- (no_of_actors*0.4).toInt to ((no_of_actors*0.6).toInt - 1)) {
			user_first_name(i) = gen_ran_string(7)
		}

		for(i <- (no_of_actors*0.8).toInt to ((no_of_actors*1).toInt - 1)) {
			user_first_name(i) = gen_ran_string(7)
		}
		return user_first_name
	}

	def initialize_last_name(no_of_actors:Int) : Array[String] = {
		var user_last_name = new Array[String](no_of_actors)
		for(i <- 0 to ((no_of_actors*0.2).toInt - 1)) {
			user_last_name(i) = gen_ran_string(7)
		}

		for(i <- (no_of_actors*0.2).toInt to ((no_of_actors*0.4).toInt - 1)) {
			user_last_name(i) = gen_ran_string(7)
		}

		for(i <- (no_of_actors*0.6).toInt to ((no_of_actors*0.8).toInt - 1)) {
			user_last_name(i) = gen_ran_string(7)
		}

		for(i <- (no_of_actors*0.4).toInt to ((no_of_actors*0.6).toInt - 1)) {
			user_last_name(i) = gen_ran_string(7)
		}

		for(i <- (no_of_actors*0.8).toInt to ((no_of_actors*1).toInt - 1)) {
			user_last_name(i) = gen_ran_string(7)
		}
		return user_last_name

	}

	def initialize_age(no_of_actors:Int) : Array[Int] = {
		var user_age = new Array[Int](no_of_actors)
		for(i <- 0 to ((no_of_actors*0.2).toInt - 1)) {
			user_age(i) = Random.nextInt(90)+ 1
		}

		for(i <- (no_of_actors*0.2).toInt to ((no_of_actors*0.4).toInt - 1)) {
			user_age(i) = Random.nextInt(90)+ 1
		}

		for(i <- (no_of_actors*0.6).toInt to ((no_of_actors*0.8).toInt - 1)) {
			user_age(i) = Random.nextInt(90)+ 1
		}

		for(i <- (no_of_actors*0.4).toInt to ((no_of_actors*0.6).toInt - 1)) {
			user_age(i) = Random.nextInt(90)+ 1
		}

		for(i <- (no_of_actors*0.8).toInt to ((no_of_actors*1).toInt - 1)) {
			user_age(i) = Random.nextInt(90)+ 1
		}
		println("*****Initializing Profile for Users*****")
		return user_age
	}

	def initialize_photos(no_of_actors: Int): Array[List[String]] = {
		var pictures = new Array[List[String]](no_of_actors)
		var pic_list = new ListBuffer[String]
		println("*****Initializing Facebook Photos for Users*****")
		for(i <- 0 to ((no_of_actors*0.2).toInt - 1)) {
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pictures(i) = pic_list.toList
			pic_list.clear()
		}

		for(j <- (no_of_actors*0.2).toInt to ((no_of_actors*0.4).toInt - 1)) {
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pictures(j) = pic_list.toList
			pic_list.clear()
		}

		for(j <- (no_of_actors*0.6).toInt to ((no_of_actors*0.8).toInt - 1)) {
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pictures(j) = pic_list.toList
			pic_list.clear()
		}

		for(j <- (no_of_actors*0.4).toInt to ((no_of_actors*0.6).toInt - 1)) {
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pictures(j) = pic_list.toList
			pic_list.clear()
		}

		for(j <- (no_of_actors*0.8).toInt to ((no_of_actors*1).toInt - 1)) {
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pic_list += gen_ran_string(10)
			pictures(j) = pic_list.toList
			pic_list.clear()
		}

		println("*****Done initializing Facebook Page for Users*****")
		return pictures
	}


	def initialize_albums(no_of_actors: Int): Array[List[List[String]]] = {
		var album = new Array[List[List[String]]](no_of_actors)
		var temp_list = new ListBuffer[String]
		println("*****Initializing Facebook Albums for Users*****")
		var builder = List.newBuilder[List[String]]

		for(i<- 0 until ((no_of_actors*0.2)-1).toInt) {
			var temp :List[String] = Main.photos(i)
			temp_list += temp(0)
			temp_list += temp(1)
			builder += temp_list.toList
			temp_list.clear()

			temp_list += temp(2)
			temp_list += temp(3)
			builder += temp_list.toList
			temp_list.clear()

			album(i) = builder.result()
			builder.clear()
		}

		for(i<- (0.2*no_of_actors).toInt until ((0.4*no_of_actors)-1).toInt) {
			var temp :List[String] = Main.photos(i)
			temp_list += temp(0)
			temp_list += temp(1)
			builder += temp_list.toList
			temp_list.clear()

			temp_list += temp(2)
			temp_list += temp(3)
			builder += temp_list.toList
			temp_list.clear()

			album(i) = builder.result()
			builder.clear()
		}

		for(i<- (0.4*no_of_actors).toInt until (no_of_actors* 0.6-1).toInt) {
			var temp :List[String] = Main.photos(i)
			temp_list += temp(0)
			temp_list += temp(1)
			builder += temp_list.toList
			temp_list.clear()

			temp_list += temp(2)
			temp_list += temp(3)
			builder += temp_list.toList
			temp_list.clear()

			album(i) = builder.result()
			builder.clear()
		}

		for(i<- (0.6*no_of_actors).toInt until ((no_of_actors*0.8)-1).toInt) {
			var temp :List[String] = Main.photos(i)
			temp_list += temp(0)
			temp_list += temp(1)
			builder += temp_list.toList
			temp_list.clear()

			temp_list += temp(2)
			temp_list += temp(3)
			builder += temp_list.toList
			temp_list.clear()

			album(i) = builder.result()
			builder.clear()
		}

		for(i<- (0.8*no_of_actors).toInt until ((no_of_actors*1)-1).toInt) {
			var temp :List[String] = Main.photos(i)
			temp_list += temp(0)
			temp_list += temp(1)
			builder += temp_list.toList
			temp_list.clear()

			temp_list += temp(2)
			temp_list += temp(3)
			builder += temp_list.toList
			temp_list.clear()

			album(i) = builder.result()
			builder.clear()
		}

		println("*****Done initializing Facebook Albums for Users*****")
		return album
	}

	//Random string for representing a photo
	def gen_ran_string(count:Int): String = {
		val random = new scala.util.Random(System.nanoTime)
		val string2 = new StringBuilder(20)
		val string1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
		for (i <- 0 until count) {
			string2.append(string1(random.nextInt(string1.length)))
		}
		string2.toString()
	}
}