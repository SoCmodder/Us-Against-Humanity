<?php
function getJoinedGames($auth_token) {
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games";
  //The get request
  $getString = "?find=in";
  //Append the get to the url
  $url = $url . $getString;
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  // Set query data here with the URL
  curl_setopt($curl, CURLOPT_URL, $url); 
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,
		array("Content-Type: application/json","Accept:application/json","Authorization: Token token=$auth_token"));
  //Execute the curl GET and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  if ( $status != 200 ) {
      die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  // echo "My games json response " . $json_response ."<br>";
  //Grab the JSON response from the curl request as an array
  $return = json_decode($json_response, true);
  //return the array for the function
  return $return;
}

function getOpenGames($auth_token) {
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games";
  //The get request
  $getString = "?find=open";
  //Append the get to the url
  $url = $url . $getString;
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  // Set query data here with the URL
  curl_setopt($curl, CURLOPT_URL, $url); 
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,
  	array("Content-Type: application/json","Accept:application/json","Authorization: Token token=$auth_token"));
  //Execute the curl GET and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  if ( $status != 200 ) {
      die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  //Grab the JSON response from the curl request as an array
  $allGames = json_decode($json_response, true);
  $return = $allGames;
  return $return;
}

function getRoomCreator($gameList,$gameIndex) {
  return $gameList['data']['games'][$gameIndex]['host'];
}

function getNumberSlots($gameList,$gameIndex) {
  return $gameList['data']['games'][$gameIndex]['game']['slots'];
}

function getNumberPlayers($gameList,$gameIndex) {
  return sizeOf($gameList['data']['games'][$gameIndex]['players']);
}

function getPointsToWin($gameList,$gameIndex) {
  return $gameList['data']['games'][$gameIndex]['game']['points_to_win'];
}

function getPublicPrivate($gameList,$gameIndex) {
  $private = $gameList['data']['games'][$gameIndex]['game']['private'];
  if($private == null || $private == 0) {
    return "Public";
  } else {
    return "Private";
  }
  return $gameList['data']['games'][$gameIndex]['game']['private'];
}

function getGameID($gameList,$gameIndex) {
  return $gameList['data']['games'][$gameIndex]['game']['id'];
}

function getWinningScore($gameList,$gameIndex) {
  $size = getNumberPlayers($gameList,$gameIndex);
  $maxScore = 0;
  for($i=0;$i<$size;$i++) {
    $currentIndexScore = $gameList['data']['games'][$gameIndex]['players'][$i]['score'];
    if($currentIndexScore > $maxScore) {
      $maxScore = $currentIndexScore;
    }
  }
  return $maxScore;
}
###############################
#Functions for actual gameplay#
###############################
//getBlackCard returns variable with two values:['text'] for the back card text and ['num_white'] for the required white cards to play
function getBlackCard($gameID,$auth_token) {
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/$gameID/blackcard";
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  // Set query data here with the URL
  curl_setopt($curl, CURLOPT_URL, $url); 
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,
  	array("Content-Type: application/json","Accept:application/json","Authorization: Token token=$auth_token"));
  //Execute the curl GET and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  if ( $status != 200 ) {
      die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  //{ “success” : true, “data” : { “text” : Blackcard text, “numwhite” : int } }
  //Grab the JSON response from the curl request as an array
  $decode = json_decode($json_response, true);
  $whiteText = $decode['data'];
  $return['text'] = $whiteText['text'];
  $return['num_white'] = $whiteText['numwhite'];
  return $return;
}
//getHand returns an array with values: ['ids'] for the white card ID and ['texts'] for the white card text
function getHand($gameID,$auth_token) {
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/$gameID/hand";
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  // Set query data here with the URL
  curl_setopt($curl, CURLOPT_URL, $url); 
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,
  	array("Content-Type: application/json","Accept:application/json","Authorization: Token token=$auth_token"));
  //Execute the curl GET and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  if ( $status != 200 ) {
      die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  //{ “success” : true, “data” : { “ids” : [card ids] , “texts” : [texts] } }
  //Grab the JSON response from the curl request as an array
  $decode = json_decode($json_response, true);
  $return = $decode['data'];
  return $return;
}
function getScoreBoard($gameID,$auth_token) {
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/$gameID/score";
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  // Set query data here with the URL
  curl_setopt($curl, CURLOPT_URL, $url); 
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,
  	array("Content-Type: application/json","Accept:application/json","Authorization: Token token=$auth_token"));
  //Execute the curl GET and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  if ( $status != 200 ) {
      die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  //Grab the JSON response from the curl request as an array
  $decode = json_decode($json_response, true);
  $return = $decode['data']['score'];
  return $return;
}
function playCard() {

}
function who($auth_token) {
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = "http://r06sjbkcc.device.mst.edu:3000/api/v1/who";
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  // Set query data here with the URL
  curl_setopt($curl, CURLOPT_URL, $url); 
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,
  	array("Content-Type: application/json","Accept:application/json","Authorization: Token token=$auth_token"));
  //Execute the curl GET and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  if ( $status != 200 ) {
      die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  //Grab the JSON response from the curl request as an array
  $decode = json_decode($json_response, true);
  $return = $decode['data']['user'];
  return $return;
}

?>