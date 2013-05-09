<!DOCTYPE html>
<div class="row-fluid" id="czar_view">
     <?php
      $sid = session_id();
      if(!$sid) {
        session_start();
      }
      $root = $_SERVER['DOCUMENT_ROOT'];
      include_once $root . '/php/gamefunctions.php';
      $gameID = $_SESSION['game_current'];
      $auth_token = $_SESSION['auth_token'];
      //Get the played cards
      $playedCards = getPlayedCards($gameID,$auth_token);
      //Display them for choosing if everyone has submitted
      if($playedCards != "false") {
        echo "<div class=\"well well-small\" align=\"center\" style=\"margin-top: 10px;\">";
          echo "<h3>You are the Card Czar</h3>";
          echo "<h4>Choose a winning card</h4>"; 
        echo "</div>";
        
        if($_SESSION['num_white'] == 1) {
          echo "<ul class=\"thumbnails\">";
          for($i=0;$i<sizeOf($playedCards);$i++){
            if(empty($playedCards[$i]['whitetext'])){
              //Don't print that fucker
            }
            else {
              echo "<li class=\"span3\">";
                echo "<div class=\"card-group\">";
                  echo "<a class=\"thumbnail card-wrapper\" id=\"winning-white-" . $playedCards[$i]['gameuser_id'] . "\">";
                    echo "<div class=\"card\">";
                      echo "<img class=\"backdrop-img\" src=\"http://placehold.it/200x200/ffffff/000000&text=+\" alt=\"\"/>";
                      echo "<h5 class=\"whitecard-text\" style=\"font-size:12px\">" . $playedCards[$i]['whitetext'][0] . ".</h5>";
                    echo "</div>";
                  echo "</a>";
                echo "</div>";
              echo "</li>";
            }
          }
          echo "</ul>";
        }
        else if($_SESSION['num_white'] == 2){
          echo "<ul class=\"thumbnails\">";
          for($i=0;$i<sizeOf($playedCards);$i++){
            if(empty($playedCards[$i]['whitetext'])){
              //Don't print that fucker
            }
            else {
              echo "<li class=\"span2\">";
                echo "<div class=\"card-group\">";
                  echo "<a class=\"thumbnail card-wrapper\" id=\"winning-white-" . $playedCards[$i]['gameuser_id'] . "\">";
                    echo "<div class=\"card\">";
                      echo "<img class=\"backdrop-img\" src=\"http://placehold.it/200x200/ffffff/000000&text=+\" alt=\"\"/>";
                      echo "<h5 class=\"whitecard-text\" style=\"font-size:12px\">" . $playedCards[$i]['whitetext'][1] . ".</h5>";
                    echo "</div>";
                  echo "</a>";
                echo "</div>";
              echo "</li>";
              echo "<li class=\"span1\" align=\"center\">";
              echo "<h2 align=\"center\">+</h2>";
              echo "</li>";
              echo "<li class=\"span2\">";
                echo "<div class=\"card-group\">";
                  echo "<a class=\"thumbnail card-wrapper\" id=\"winning-white-" . $playedCards[$i]['gameuser_id'] . "\">";
                    echo "<div class=\"card\">";
                      echo "<img class=\"backdrop-img\" src=\"http://placehold.it/200x200/ffffff/000000&text=+\" alt=\"\"/>";
                      echo "<h5 class=\"whitecard-text\" style=\"font-size:12px\">" . $playedCards[$i]['whitetext'][0] . ".</h5>";
                    echo "</div>";
                  echo "</a>";
                echo "</div>";
              echo "</li>";
            }
          }
          echo "</ul>";
        }
      }
      else { //Print a status if not everyone has played the cards yet.
        echo "<div class=\"well well-small\" align=\"center\" style=\"margin-top: 10px;\">";
          echo "<h3>You are the Card CZAR</h3>";
          echo "<h4>Waiting on " . $_SESSION['num_waiting'] . " white cards</h4>"; 
        echo "</div>";
      }
     ?>
  </div>