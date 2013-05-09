<?php
  $sid = session_id();
  if(!$sid) {
    session_start();
  }
  //Grab all the current session variables or important ones and put them into an array.
  if(isSet($_SESSION['white_one']) && !isSet($_SESSION['white_two'])){
    $myArray = array("game_id" => $_SESSION['game_current'],"num_white" => $_SESSION['num_white'],"white_one" => $_SESSION['white_one'],"cards_status" => $_SESSION['cards_status']);
  }
  else if(isSet($_SESSION['white_one']) && isSet($_SESSION['white_two'])){
    $myArray = array("game_id" => $_SESSION['game_current'],"num_white" => $_SESSION['num_white'],"white_one" => $_SESSION['white_one'], "white_two" => $_SESSION['white_two'], "cards_status" => $_SESSION['cards_status']);
  }
  else {
    $myArray = array("game_id" => $_SESSION['game_current'],"num_white" => $_SESSION['num_white'],"cards_status" => $_SESSION['cards_status']);
  }
  echo json_encode($myArray);
?>