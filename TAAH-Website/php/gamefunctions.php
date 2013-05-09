<?php
///////////////////////////////////////////////////////////////////////////
//Lobby functions
//////////////////////////////////////////////////////////////////////////
function getJoinedGames($auth_token) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $serverURL . "games";
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
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games";
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

function getState($gameList,$gameIndex) {
  if($gameList['data']['games'][$gameIndex]['game']['state'] == 0) {
    return "Waiting on Players";
  }
  else if($gameList['data']['games'][$gameIndex]['game']['state'] == 1) {
    return "In Progress";
  }
  else if($gameList['data']['games'][$gameIndex]['game']['state'] == 1) {
    return "Finished";
  }
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
#Functions for gameplay#
###############################
//getBlackCard returns variable with two values:['text'] for the back card text and ['num_white'] for the required white cards to play
function getBlackCard($gameID,$auth_token) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games/$gameID/blackcard";
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
  //$return['num_white'] = $whiteText['numwhite'];
  $_SESSION['num_white'] = $whiteText['numwhite'];
  return $return;
}
//getHand returns an array with values: ['ids'] for the white card ID and ['texts'] for the white card text
function getHand($gameID,$auth_token) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games/$gameID/hand";
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
//Gets info on all players ['name'],['score'],['czar'],['submitted'],
function getScoreBoard($gameID,$auth_token) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games/$gameID/score";
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
  echo " wat " . $json_response;
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

//Sends all cards to server to be played, then removes session vars
function playCard($gameID,$cardID,$secondCardID = false,$thirdCardID = false) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  $auth_token = $_SESSION['auth_token'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games/$gameID/whitecard";
  //shwat {"card_id":[157]}
  if($secondCardID == false) {
    $data = array("card_id" => array((int)$cardID)); 
  }
  else if($secondCardID != false && $thirdCardID == false) {
    $data = array("card_id" => array((int)$cardID,(int)$secondCardID));
  }
  else {
    $data = array("card_id" => array((int)$cardID,(int)$secondCardID,(int)$thirdCardID));
  }
  //Turn the array into a json object
  $content = json_encode($data);
  // echo $content;
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
  //Close the curl instance
  // echo "play card json response " . $json_response ."<br>";
  $response = json_decode($json_response, true);
  if($status == 200) { //We got a success response from the server
    if($response['success'] == true) {
      // unset($_SESSION['white_one']);
      // unset($_SESSION['white_two']);
      return "true";
    }
  }
  //If the status is not 200, either something bad happened(?) or it the player has already played
  if ( $status != 200 ) {
      //die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
      if($response['success'] == false) {
        // echo "white one: " . $_SESSION['white_one'];
        return "false failed status: " . $status . " response " . $json_response;
      }
  }
  //Close the curl instance
  curl_close($curl);
}
//Info on current user
function who($auth_token) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "who";

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

function getPlayedCards($gameID,$auth_token) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games/$gameID/round";
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
  $decode = json_decode($json_response, true);
  if($status == 200) {
    if($decode['data']['all_submit'] == false) {
      $return = "false";
    }
    else {
      $return = $decode['data']['submitted'];
    }
  }
  else {
    die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  return $return;
}

function submitWinningWhite($gameID,$userID) {
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  $auth_token = $_SESSION['auth_token'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games/$gameID/winningcard";
  //{ “user_id” : gameuser_id }
  $data = array("user_id" => (int)$userID); 
  //Turn the array into a json object
  $content = json_encode($data);
  // echo $content;
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
  echo $json_response;
  //Grab the status of the GET
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //Close the curl instance
  curl_close($curl);
  // echo "play card json response " . $json_response ."<br>";
  $response = json_decode($json_response, true);
  if($status == 200) { //We got a success response from the server
    return true;
  }
  else {
    return false;
  }
}

function getRound($gameID,$auth_token) {
// $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  // $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = $_SESSION['serverURL'];
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $url = $serverURL . "games/$gameID/round";
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
  $decode = json_decode($json_response, true);
  if($status == 200) {
    $return = $decode['data'];
  }
  else {
    die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  return $return;
}

function setInGameData() {
  //Set some one line variables for use in caclulations
  $gameID = $_SESSION['game_current'];
  $auth_token = $_SESSION['auth_token'];
  $round = getRound($gameID,$auth_token);
  $whoAmI = who($auth_token);
  //Set some one line sessions variables
  $_SESSION['user_name'] = $whoAmI['name'];
  $_SESSION['hand'] = $round['hand'];
  $_SESSION['player_scores'] = $round['score'];
  $_SESSION['is_czar'] = $round['is_czar'];
  $_SESSION['game_current_state'] = $round['game']['game']['state'];//0=waiting/1=in prog/2=over?
  //Set the white card texts to empty strings if they are not set yet(so checks don't shit themselves)
  // if(!isSet($_SESSION['white_one_text'])) {
    // $_SESSION['white_one_text'] = "";
  // }
  // if(!isSet($_SESSION['white_two_text'])) {
    // $_SESSION['white_two_text'] = "";
  // }
  $numWaiting = 0;//Num waiting to be counted in a second
  //Loop through the score key of the array
  for($i=0;$i<sizeOf($round['score']);$i++) {
    //Se if the current player has played
    if($round['score'][$i]['submitted'] == true && $round['score'][$i]['name'] == $_SESSION['user_name']) {
      $_SESSION['has_played'] = true;
    }
    //Or not
    else if($round['score'][$i]['submitted'] == false && $round['score'][$i]['name'] == $_SESSION['user_name']) {
      $_SESSION['has_played'] = false;
    }
    //If they haven't played and they aren't the czar, add to the numberwaiting
    if($round['score'][$i]['submitted'] == false && $round['score'][$i]['czar'] == false) {
      $numWaiting++;
    }
    //Grab the name of the czar
    if($round['score'][$i]['czar'] == true){
      $_SESSION['czar'] = $round['score'][$i]['name'];
    }
  }
  //Finally set the num_waiting session var
  $_SESSION['num_waiting'] = $numWaiting;
  //See if the player has submitted only one card
  if(!isSet($_SESSION['cards_status'])) {
    $played_one = false;
  }
  else if($_SESSION['cards_status'] == 2) {
    $played_one = true;
  }
  else {
    $played_one = false;
  }
  
  //Get my gameUserID so I we can load the card we played
for($i=0;$i<sizeOf($round['game']['players']);$i++) {
  if($round['game']['players'][$i]['name'] == $_SESSION['user_name']) {
    $gameUserID = $round['game']['players'][$i]['id'];
  }
}

//Now grab the card(s) we've submitted...this is a little bit of silly conditional looop thingy
$testVal = "test";
if($_SESSION['has_played'] == true) {
  $testVal = $round['submitted'];
  for($i=0;$i<sizeOf($round['submitted']);$i++) {
    //$testVal = "test in a loop with size of" . $round['submitted'][$i]['whitetext'] ;
    if($round['submitted'][$i]['gameuser_id'] == $gameUserID) {
      $submittedCards = $round['submitted'][$i]['whitetext'];
      break;
    }
  }
    if($_SESSION['num_white'] == 1) {
      $_SESSION['white_one_text'] = $submittedCards[0];
      if(!isSet($_SESSION['white_two_text'])) {
        $_SESSION['white_two_text'] = "";
      }
    }
    else {
      $_SESSION['white_one_text'] = $submittedCards[1];
      $_SESSION['white_two_text'] = $submittedCards[0];
    }
}
else {
  //Set the white card texts to empty strings if they are not set yet(so checks don't shit themselves)
  if(!isSet($_SESSION['white_one_text'])) {
    $_SESSION['white_one_text'] = "";
  }
  if(!isSet($_SESSION['white_two_text'])) {
    $_SESSION['white_two_text'] = "";
  }
}

if(!isSet($_SESSION['white_one_text'])) {
  $_SESSION['white_one_text'] = "";
}
if(!isSet($_SESSION['white_two_text'])) {
  $_SESSION['white_two_text'] = "";
}
  
  //Check to see if we've not played this round. If we havent, reset the cards_status unless it is 1
  if(isSet($_SESSION['cards_stats'])) {
    if($_SESSION['has_played'] == false && $_SESSION['cards_status'] != 1) {
      $_SESSION['cards_status'] = 0;
    }
  }
}

?>