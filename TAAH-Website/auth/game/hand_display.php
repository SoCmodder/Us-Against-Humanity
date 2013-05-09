<!DOCTYPE html>
<?php
  $sid = session_id();
  if(!$sid) {
    session_start();
  }
  $root = $_SERVER['DOCUMENT_ROOT'];
  include_once $root . '/php/gamefunctions.php';
?>
<div id="hand_display">
  <div id="carousel-bounding-box" style="margin-left:35px">
    <div id="myCarousel" class="carousel slide" style="height:200px;">
      <!-- Carousel items -->
      <div class="carousel-inner slide-inner">
        <?php
          echo "<div class=\"active item\" data-slide-number=\"" . $_SESSION['hand']['ids'][0] . "\">";
            echo "<div class=\"new_html_code\">";
              echo "<h5 class=\"slide-text\">" . $_SESSION['hand']['texts'][0] . ".</h5>";
              echo "<form id=\"play-card\" style=\"padding:0; margin:0\">"; 
                echo "<input type=\"hidden\" name=\"card_id\" value=\"" . $_SESSION['hand']['ids'][0] . "\">";
                echo "<input type=\"hidden\" name=\"card_text\" value=\"" . $_SESSION['hand']['texts'][0] . "\">";
                echo "<button class=\"btn btn-small btn-inverse slide-button\" name=\"play_button\" type=\"sumbit\" id=\"card-" . $_SESSION['hand']['ids'][0] . "\">Play Card <i class=\"icon-play icon-white\"></i></button>";
                echo "</form>";
            echo "</div>";
          echo "</div>";
          for($i=1;$i<sizeOf($_SESSION['hand']['ids']);$i++) {
            echo "<div class=\"item\" data-slide-number=\"" . $_SESSION['hand']['ids'][$i] . "\">";
              echo "<div class=\"new_html_code\">";
                echo "<h5 class=\"slide-text\">" . $_SESSION['hand']['texts'][$i] . ".</h5>";
                echo "<form id=\"play-card\" style=\"padding:0; margin:0\">"; 
                echo "<input type=\"hidden\" name=\"card_id\" value=\"" . $_SESSION['hand']['ids'][$i] . "\">";
                echo "<input type=\"hidden\" name=\"card_text\" value=\"" . $_SESSION['hand']['texts'][$i] . "\">";
                echo "<button class=\"btn btn-small btn-inverse slide-button\" name=\"play_button\" type=\"sumbit\" id=\"card-" . $_SESSION['hand']['ids'][$i] . "\">Play Card <i class=\"icon-play icon-white\"></i></button>";
                echo "</form>";
              echo "</div>";
            echo "</div>";
          }
        ?>
      </div>
      <!-- Carousel nav -->
      <!--<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>-->
      <!--<a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>-->
    </div>
  </div>
</div>