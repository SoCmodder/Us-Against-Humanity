<!DOCTYPE html>
<!--<div id="played-cards">-->
<div class="row-fluid row-played" id="normal_view">
  <?php
    $sid = session_id();
    if(!$sid) {
      session_start();
    }
  ?>
  <div class="row-fluid">
    <div class="span5" id="played">
    <h4 align="center">Played card(s)</h4>
    <?php
    if($_SESSION['num_white'] == 1) {
      echo "<ul class=\"thumbnails\">";
        echo "<li class=\"span12\">";
          echo "<div class=\"thumbnail card-group\">";
            echo "<a href=\"#\" class=\"card-wrapper\">";
              echo "<div class=\"card\">";
                echo "<img class=\"backdrop-img\" src=\"http://placehold.it/250x250/ffffff/000000&text=+\" alt=\"\"/>";
                  if($_SESSION['has_played'] == true){
                    echo "<h4 class=\"slide-text\">" . $_SESSION['white_one_text'] . "</h4>";
                  }
                  else if($_SESSION['has_played'] == false) {
                    echo "<h4 class=\"slide-text\">Waiting on your card...</h4>";
                  }
              echo "</div>";
            echo "</a>";  
          echo "</div>";
        echo "</li>";
      echo "</ul>";
    }
    else if($_SESSION['num_white'] == 2) {
      echo "<ul class=\"thumbnails\">";
        echo "<li class=\"span6\">";
          echo "<div class=\"thumbnail card-group\">";
            echo "<a href=\"#\" class=\"card-wrapper\">";
              echo "<div class=\"card\">";
                echo "<img class=\"backdrop-img\" src=\"http://placehold.it/300x300/ffffff/000000&text=+\" alt=\"\"/>";
                  if($_SESSION['has_played'] == true){
                    echo "<h4 class=\"slide-text\" style=\"font-size:12px\">" . $_SESSION['white_one_text'] . "</h4>";
                  }
                  else if($_SESSION['has_played'] == false && $_SESSION['cards_status'] == 2) {
                    echo "<h4 class=\"slide-text\" style=\"font-size:12px\">" . $_SESSION['white_one_text'] . "</h4>";
                  }
                  else if($_SESSION['has_played'] == false) {
                    echo "<h4 class=\"slide-text\" style=\"font-size:12px\">Waiting on your first card...</h4>";
                  }
              echo "</div>";
            echo "</a>";   
          echo "</div>";
        echo "</li>";
        echo "<li class=\"span6\">";
          echo "<div class=\"thumbnail card-group\">";
            echo "<a href=\"#\" class=\"card-wrapper\">";
              echo "<div class=\"card\">";
                echo "<img class=\"backdrop-img\" src=\"http://placehold.it/300x300/ffffff/000000&text=+\" alt=\"\"/>";
                  if($_SESSION['has_played'] == true){
                    echo "<h4 class=\"slide-text\" style=\"font-size:12px\">" . $_SESSION['white_two_text'] . "</h4>";
                  }
                  else if($_SESSION['has_played'] == false && $_SESSION['cards_status'] == 2) {
                    echo "<h4 class=\"slide-text\" style=\"font-size:12px\">Waiting on your second card...</h4>";
                  }
                  else if($_SESSION['has_played'] == false) {
                    echo "<h4 class=\"slide-text\" style=\"font-size:12px\">Waiting on your second card...</h4>";
                  }
              echo "</div>";
            echo "</a>";
          echo "</div>";
        echo "</li>";
      echo "</ul>";
    }
    else {
    echo "<ul class=\"thumbnails\">";
        echo "<li class=\"span12\">";
          echo "<h3>Awwwwww man, I don't know what to do with 3 cards....well, this is awkward.</h3>";
        echo "</li>";
      echo "</ul>";
    }
    ?>
    </div>
    <div class="span7" align="center">
      <div class="well well-small normal-status">
      <?php
        // echo "<h5>" . $_SESSION['winning_player'] . " is leading with " . $_SESSION['winning_score'] . "/? points.</h5>";
        echo "<h4>" . $_SESSION['czar'] . " is the Card Czar</h4>";
        if($_SESSION['num_waiting'] != 0) {
          echo "<h5>Waiting on " . $_SESSION['num_waiting'] . " white card(s)</h5>";
        }
        else {
          echo "<h5>Waiting on the Czar to choose white card(s)</h5>";
        }
        
      ?>
      </div>
    </div>
  </div><!--Second played Row.-->
</div>