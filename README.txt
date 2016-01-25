Project structure:																					----> Server -->  Main.scala  (Spray server)
																									|		|------> DataInstance.scala (Data types for Server)
																									|		|-------> Initialization.scala(Initialize the Server)		
																									|       |-------> build.sbt	
																									|       |--------> MainActor.scala (Server Actors)
																									|		
		   		---Load Test-----> (This part covers statistics pertaining to number  -------------->		
				|			      of posts and numbers of Get friend list, 							|
				|				  Get Photo, Get Album requests)									|
				|				   																	-----> Client.scala  (Spray client)
 		  		|																								|		
Project4_Part1  -------------------->																			---------> build.sbt
				|
				|																					-----> Server----> Main.scala(Spray server)
				|																					|			|---------> build.sbt
				|																					|			|--------->DataInstance.scala (Data types for Server)
				--- REST Api Test---> (This part covers only the JSON response in response to--------			|-------> Initialization.scala(Initialize the Server)
				|			posts, requests like Get Get friend list, 					|			|--------> MainActor.scala (Server Actors)
				|			Get Photo, Get Album requests)								|
				|																					-----> Client.scala  (Spray client)
				|																								|			
				|																								-----> build.sbt
				------- Project Report (Implementation Details)	
				
Steps to execute with SBT:

To execute Load Test
1. Unzip the folder, go to folder Load Test

2. Go to folder Server

3. On command prompt type to compile the program : sbt "run 100000"
	(100000 is the number we got after scaling down Facebook model)

4. Go to folder Client	

5. On command prompt type to compile the program : sbt "run 100000 heavy"
	Note : light can be used instead of heavy
	
	
Results can be viewed on the terminal executing Server. 	

	
To execute Rest Api Test
1. Unzip the folder, go to folder Rest Api Test

2. Go to folder Server

3. On command prompt type to compile the program : sbt "run 100000"
	(100000 is the number we got after scaling down Facebook model)

4. Go to folder Client	

5. On command prompt type to compile the program : sbt "run 100000 heavy"
	Note : light can be used instead of heavy

JSON responses can be viewed on the Client terminal
	

Steps to execute without SBT:
1.  Unzip the folder, go to the folder Load Test 

2.  Go to the folder Server

3.  On command prompt type to compile the program: scalac Main.scala 

4.  To execute : scala Main 100000

5.  Go to the folder Client

6.  On command prompt type to compile the program: scalac SprayClient.scala 

7.  To execute : scala Client 100000 heavy

Same steps can be repeated in the Rest Api folder


If compiling without SBT follwowing Jars would be required:
Libraries used:
akka-actor_2.11 version:2.3.13
config-1.2.1 
spray-can_2.11 version:1.3.1
spray-http_2.11 version:1.3.1
spray-httpx_2.11 version:1.3.1
spray-json_2.11 version:1.3.1
spray-routing_2.11 version:1.3.1
spray-util_2.11 version:1.3.1

To compile client, additional library required:
spray-client_2.11 version:1.3.1


