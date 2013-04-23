<!DOCTYPE html>
<?php
  //session_start();//Start a session so we can have global variables
  $gameID = $_SESSION['game_current'];
  $auth_token = $_SESSION['auth_token'];
  $_SESSION['hand'] = getHand($gameID,$auth_token);
  // print_r($_SESSION['hand']);
?>
<div id="hand">
  <?php
    echo "<div class=\"row-fluid row-hand\">";
      echo "<ul class=\"thumbnails\">";
      for($i=0;$i<5;$i++) {
        echo "<li class=\"span2\">";
          echo "<div class=\"card-group\">";
            echo "<a class=\"thumbnail card-wrapper\" id=\"cardID-" . $i . "-" . $_SESSION['hand']['ids'][$i] . "\">";
              echo "<div class=\"card\">";
                echo "<img class=\"backdrop-img\" src=\"http://placehold.it/200x200/ffffff/000000&text=+\" alt=\"\"/>";
                echo "<h5 class=\"whitecard-text\" style=\"font-size:12px\">" . $_SESSION['hand']['texts'][$i] . ".</h5>";
              echo "</div>";
            echo "</a>";
          echo "</div>";
        echo "</li>";
      }
      echo "</ul>";
    echo "</div>";
    echo "<div class=\"row-fluid row-hand\">";
      echo "<ul class=\"thumbnails\">";
      for($i=5;$i<10;$i++) {
        echo "<li class=\"span2\">";
          echo "<div class=\"card-group\">";
            echo "<a class=\"thumbnail card-wrapper\" id=\"cardID-" . $i . "-" . $_SESSION['hand']['ids'][$i] . "\">";
              echo "<div class=\"card\">";
                echo "<img class=\"backdrop-img\" src=\"http://placehold.it/200x200/ffffff/000000&text=+\" alt=\"\"/>";
                echo "<h5 class=\"whitecard-text\" style=\"font-size:12px\">" . $_SESSION['hand']['texts'][$i] . ".</h5>";
              echo "</div>";
            echo "</a>";
          echo "</div>";
        echo "</li>";
      }
      echo "</ul>";
    echo "</div>";
  ?>
</div>