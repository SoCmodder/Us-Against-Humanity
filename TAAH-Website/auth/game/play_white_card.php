<?php
  //Card status
  //0 = not played anything
  //1 = played 1/1
  //2 = played 1/2
  //3 = played 2/2
  $sid = session_id();
  if(!$sid) {
    session_start();
  }
  $root = $_SERVER['DOCUMENT_ROOT'];
  include_once $root . '/php/gamefunctions.php';
  $gameID = $_SESSION['game_current'];

  if($_SESSION['num_white'] == 1) {
    $_SESSION['white_one'] = $_POST['card_id'];
    $_SESSION['white_one_text'] =  $_POST['card_text'];
    playCard($gameID,$_POST['card_id']);
    $_SESSION['cards_status'] = 1;//1/1 cards played
    echo "<h4 align=\"center\">Played card(s)</h4>";
    echo "<ul class=\"thumbnails\">";
        echo "<li class=\"span12\">";
          echo "<div class=\"thumbnail card-group\">";
            echo "<a href=\"#\" class=\"card-wrapper\">";
              echo "<div class=\"card\">";
                echo "<img class=\"backdrop-img\" src=\"http://placehold.it/250x250/ffffff/000000&text=+\" alt=\"\"/>";
                echo "<h4 class=\"slide-text\">" . $_POST['card_text'] . "</h4>";
              echo "</div>";
            echo "</a>";  
          echo "</div>";
        echo "</li>";
      echo "</ul>";
  }
  if($_SESSION['num_white'] == 2) {
    if($_SESSION['played_one'] == true) {
        $return = playCard($gameID,$_SESSION['white_one'],$_POST['card_id']);
        $_SESSION['white_two'] = $_POST['card_id'];
        $_SESSION['white_two_text'] =  $_POST['card_text'];
        $_SESSION['cards_status'] = 3;//2/2 cards played
        echo "<h4 align=\"center\">Played card(s)</h4>";
        echo "<li class=\"span6\">";
        echo "<div class=\"card-group\">";
          echo "<a class=\"thumbnail card-wrapper\" id=\"cardID-" . $_POST['card_id'] . "\">";
            echo "<div class=\"card\">";
              echo "<img class=\"backdrop-img\" src=\"http://placehold.it/200x200/ffffff/000000&text=+\" alt=\"\"/>";
              echo "<h5 class=\"slide-text\" style=\"font-size:24px\">" . $_POST['card_text'] . ".</h5>";
            echo "</div>";
          echo "</a>";
        echo "</div>";
      echo "</li>";
    } 
    else {
      $_SESSION['white_one'] = $_POST['card_id'];
      $_SESSION['white_one_text'] =  $_POST['card_text'];
      $_SESSION['cards_status'] = 2;//1/2 cards played
      echo "<li class=\"span6\">";
        echo "<div class=\"card-group\">";
          echo "<a class=\"thumbnail card-wrapper\" id=\"cardID-" . $_POST['card_id'] . "\">";
            echo "<div class=\"card\">";
              echo "<img class=\"backdrop-img\" src=\"http://placehold.it/200x200/ffffff/000000&text=+\" alt=\"\"/>";
              echo "<h5 class=\"slide-text\" style=\"font-size:12px\">" . $_POST['card_text'] . ".</h5>";
            echo "</div>";
          echo "</a>";
        echo "</div>";
      echo "</li>";
    }
  }
  
  
?>