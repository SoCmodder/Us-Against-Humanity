<?php
  session_start();//Start a session so we can have global variables
  $gameID = $_POST['game_id'];
  $auth_token = $_SESSION['auth_token'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/$gameID";
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  // Set query data here with the URL
  curl_setopt($curl, CURLOPT_URL, $url);
  curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "PUT"); 
  curl_setopt($curl, CURLOPT_POSTFIELDS,$content);
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,array("Content-Type: application/json","Accept:application/json","Authorization: Token token=$auth_token"));
  //Execute the curl GET and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  // if ( $status != 200 ) {
      // die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  // }
  //Close the curl instance
  curl_close($curl);
  echo "Put user json response " . $json_response ."<br>";
  if($status == 200) { //We got a success response from the server
    //Grab the JSON response from the curl request
    //Put user json response {"success":true,"info":"Game User created!","data":{"gameuser":{"id":34,"game_id":4,"user_id":5}}}
    $response = json_decode($json_response, true);
    $_SESSION['game_current'] = $response['data']['gameuser']['game_id'];
    header("Location: playgame.php");//Only go here if the game has started!...
  }
  if($status == 405) {
    $_SESSION['game_current'] = $gameID;
    header("location: game/game.php");
  }
  //return the array for the function
  return $return;
?>