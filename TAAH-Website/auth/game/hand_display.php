<!DOCTYPE html>
<?php
  //session_start();//Start a session so we can have global variables
?>
<div id="hand-view">
  <div id="carousel-bounding-box" style="margin-left:35px">
    <div id="myCarousel" class="carousel slide" style="height:200px;">
      <!-- Carousel items -->
      <div class="carousel-inner slide-inner">
        <?php
          echo "<div class=\"active item\" data-slide-number=\"" . $_SESSION['hand']['ids'][0] . "\">";
            echo "<div class=\"new_html_code\">";
              echo "<h5 class=\"slide-text\">" . $_SESSION['hand']['texts'][0] . ".</h5>";
              echo "<button class=\"btn btn-small btn-inverse slide-button\">Play Card <i class=\"icon-play icon-white\"></i></button>";
            echo "</div>";
          echo "</div>";
          for($i=1;$i<sizeOf($_SESSION['hand']['ids']);$i++) {
            echo "<div class=\"item\" data-slide-number=\"" . $_SESSION['hand']['ids'][$i] . "\">";
              echo "<div class=\"new_html_code\">";
                echo "<h5 class=\"slide-text\">" . $_SESSION['hand']['texts'][$i] . ".</h5>";
                echo "<button class=\"btn btn-small btn-inverse slide-button\">Play Card <i class=\"icon-play icon-white\"></i></button>";
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