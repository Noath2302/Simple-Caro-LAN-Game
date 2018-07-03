# Simple-Caro-LAN-Game
Simple Caro game<br/>
a JavaFX FXML application
the only while loops can be found under the thread that maintain the connection

# Some Basic Info
1.The game was made with InteliJ IDE
2.Before playing the game make sure you have a reliable internet connection
3.Two computer should be in the same subnet (192.168.0.101 and 192.168.0.102) 
  or they are connected to the same router
4.The test appears to go well under the same network, but is not reliable as we have different in higher subnets
5.the Server will seem to crash(when waiting for client)-will fix later-
  just connect the client and everything is okay
6.if anything crash, open Task Manager and "end task" the game
  you should also "end task" Java SE Library in case it crash (or you want to end the Server waiting)
  The Client cannot crash when connecting, if it doesn't find any host,it will be back to the welcome scene


# How to play
1.Start the Game (either in Intelij IDE, 
or with the jar file located at "PocketWatch/out/artifacts/PocketWatch_jar/")
2.At the Welcome Screen you have to enter:
--"your name"
--"port number"
--wether your "Role" is SERVER or CLIENT:
    Server : Start the ServerSocket
    Client : Connect to the Server Socket
    notes - so SERVER should "start" first then CLIENT
-- the SERVER Option in "Role" also reveal the IP Address of your computer 
--if you are CLIENT you also have to input hostName as 
the computer name of the client or the IP Address
3.When all information is entered click "Start"
4.Beat the other guy or "Concede"
  both will bring you to the welcome scene

# Additional feature
1.you can change the "Style" in any stage of the game
2.you can used "Role" to find your computer name and IP Address 
