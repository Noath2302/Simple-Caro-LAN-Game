# Simple-Caro-LAN-Game
Simple Caro game<br/>
a JavaFX FXML application<br/>
the only while loops can be found under the thread that maintain the connection</br>

# Some Basic Info
1.The game was made with InteliJ IDE<br/>
2.Before playing the game make sure you have a reliable internet connection<br/>
3.Two computer should be in the same subnet (192.168.0.101 and 192.168.0.102) <br/>
  or they are connected to the same router<br/>
4.The test appears to go well under the same network, but is not reliable as we have different in higher subnets<br/>
5.the Server will seem to crash(when waiting for client)-will fix later-<br/>
  just connect the client and everything is okay<br/>
6.if anything crash, open Task Manager and "end task" the game<br/>
  you should also "end task" Java SE Library in case it crash (or you want to end the Server waiting)<br/>
  The Client cannot crash when connecting, if it doesn't find any host,it will be back to the welcome scene<br/>


# How to play
1.Start the Game (either in Intelij IDE, <br/>
or with the jar file located at "PocketWatch/out/artifacts/PocketWatch_jar/")<br/>
2.At the Welcome Screen you have to enter:<br/>
--"your name"<br/>
--"port number"<br/>
--wether your "Role" is SERVER or CLIENT:<br/>
    Server : Start the ServerSocket<br/>
    Client : Connect to the Server Socket<br/>
    notes - so SERVER should "start" first then CLIENT<br/>
-- the SERVER Option in "Role" also reveal the IP Address of your computer <br/>
--if you are CLIENT you also have to input hostName as <br/>
the computer name of the client or the IP Address<br/>
3.When all information is entered click "Start"<br/>
4.Beat the other guy or "Concede"<br/>
  both will bring you to the welcome scene<br/>

# Additional feature
1.you can change the "Style" in any stage of the game<br/>
2.you can used "Role" to find your computer name and IP Address<br/> 
