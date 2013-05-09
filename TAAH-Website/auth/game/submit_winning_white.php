<?php
  $sid = session_id();
  if(!$sid) {
    session_start(); }
  $root = $_SERVER['DOCUMENT_ROOT'];
  include_once $root . '/php/gamefunctions.php';
  $gameID = $_SESSION['game_current'];
  $userID = $_POST['gameuser_id'];
  $return = submitWinningWhite($gameID,$userID);
  if($return == true) {
    echo "<h3>Round over, waiting for new round to start!</h3>";
  }
  else {
    echo "<h3>Shit went wrong</h3>";
    //Do nothing? Try again? Who knows
  }
?>