<?php
  session_start();//Start a session so we can have global variables
  $root = $_SERVER['DOCUMENT_ROOT'];
  include_once $root . '/php/gamefunctions.php';
  $gameID = $_POST['game_id'];
  $auth_token = $_SESSION['auth_token'];
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $serverURL . "games/$gameID";
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
      // die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  //Close the curl instance
  curl_close($curl);
  echo "Put user json response " . $json_response ."<br>";
  if($status == 200) { //We got a success response from the server
    $response = json_decode($json_response, true);
    $_SESSION['game_current'] = $response['data']['gameuser']['game_id'];
    setInGameData();
    if($_SESSION['game_current_state'] == 1) {
      header("Location: game/game.php");//Only go here if the game has started!...
    }
    else {
     header("Location: lobby.php");
    }
    
  }
  if($status == 405) {
    $_SESSION['game_current'] = $gameID;
    setInGameData();
    if($_SESSION['game_current_state'] == 1) {
      header("Location: game/game.php");//Only go here if the game has started!...
    }
    else {
     header("Location: lobby.php");
    }
  }
  else {
    header("location /error/error.php");
  }
  //return the array for the function
  return $return;
?>