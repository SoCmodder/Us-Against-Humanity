<?php
//Check if the session has started. If not, start it
$sid = session_id();
if(!$sid) {
  session_start();
}
$root = $_SERVER['DOCUMENT_ROOT']; //Grab the document root
include_once $root . '/php/gamefunctions.php'; //Include the game functions
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

//Set the white card id's to nutin if they dont exist already (white_one,white_two)
if(!isSet($_SESSION['white_one'])) {
  $_SESSION['white_one'] = "";
}
if(!isSet($_SESSION['white_two'])) {
  $_SESSION['white_two'] = "";
}

//hashdfdf
if(!isSet($_SESSION['cards_status'])) {
  $_SESSION['cards_status'] = 0;
}

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
$_SESSION['played_one'] = $played_one;

//Check to see if we've not played this round. If we havent, reset the cards_status unless it is 1
if(isSet($_SESSION['cards_stats'])) {
  if($_SESSION['has_played'] == false && $_SESSION['cards_status'] != 2) {
    $_SESSION['cards_status'] = 0;
  }
}

//Return the variables our javascript is going to need to use
echo json_encode(array("has_played" => $_SESSION['has_played'],"played_one" => $played_one,"is_czar" => $_SESSION['is_czar'],"white_one" => $_SESSION['white_one'],"white_one_text" => $_SESSION['white_one_text'],"gameuserid" => $gameUserID,"test" => $testVal));
?>