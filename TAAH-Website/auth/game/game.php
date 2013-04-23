<!DOCTYPE html>
<html lang="en">
  <head>
    <?php
      $root = $_SERVER['DOCUMENT_ROOT'];
      include $root . '/php/gamefunctions.php';
      session_start();
      $gameID = $_SESSION['game_current'];
      $auth_token = $_SESSION['auth_token'];
      print $gameID;
    ?>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- CSS selector -->
    <script src="/javascript/css_browser_selector.js" type="text/javascript"></script>
    <!-- Le styles -->
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="/css/standard.css" rel="stylesheet" type="text/css">
    <link href="/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/bootstrap/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="/bootstrap/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="/bootstrap/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="/bootstrap/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="/bootstrap/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="/bootstrap/ico/favicon.png">
  </head>

  <body>
    <?php
      require($root."/auth/navbar.php");
    ?>
    <div class="container">
      <div class="row-fluid">
        <div class="span3">
          <div class="row-fluid row-black">
            <ul class="thumbnails">
              <li>
                <div class="thumbnail card-group">
                  <a href="#" class="card-wrapper">
                    <div class="card">
                      <?php
                        $blackCard = getBlackCard($gameID,$auth_token);
                        echo "<img src=\"http://placehold.it/250x250/000000/ffffff&text=+\" alt=\"\"/>";
                        echo "<h4 class=\"blackcard-text\" style=\"color:white\">" . $blackCard['text'] . "</h4>"; //Do somethign with the number... 
                      ?>
                    </div>
                  </a>                        
                </div>
              </li>
            </ul>
          </div><!--Black card-->
          <div class="row-fluid" id="hand-view">
            <?php
              require("hand_display.php");
            ?>
          </div><!--Selected white card-->
        </div>
        <div class="span6">
          <div class="row-fluid row-played" id="played-cards">
            <?php
              require("played_cards.php");
            ?>
          </div>
          <div class="row-fluid" id="hand-title" align="center" style="margin-bottom:-10px;">
            <h4>Choose Wisely. . .</h4>
          </div>
          <div class="row-fluid row-hand-container" id="hand">
            <?php
              require("hand.php");
            ?>
          </div><!--White card hand. Tied to selecte white card in previous span-->
        </div><!--Played white cards/whatever its going to be before they are played-->
        
        <div class="span3">
          <div id="score_board">
            <?php
              require("score_board.php");
            ?>
          </div>
        </div><!--Score board, always left side of screen -->
      </div><!--Main Row-->
    </div><!--Main container-->
    
    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!--<script src="/bootstrap/js/jquery.js"></script>-->
    <script src="/bootstrap/js/bootstrap-transition.js"></script>
    <script src="/bootstrap/js/bootstrap-alert.js"></script>
    <script src="/bootstrap/js/bootstrap-modal.js"></script>
    <script src="/bootstrap/js/bootstrap-dropdown.js"></script>
    <script src="/bootstrap/js/bootstrap-scrollspy.js"></script>
    <script src="/bootstrap/js/bootstrap-tab.js"></script>
    <script src="/bootstrap/js/bootstrap-tooltip.js"></script>
    <script src="/bootstrap/js/bootstrap-popover.js"></script>
    <script src="/bootstrap/js/bootstrap-button.js"></script>
    <script src="/bootstrap/js/bootstrap-collapse.js"></script>
    <script src="/bootstrap/js/bootstrap-carousel.js"></script>
    <script src="/bootstrap/js/bootstrap-typeahead.js"></script>
    <script>
      function reloadHand() {
        $('#hand').load('hand.php #hand');
      }
      function reloadScores() {
        $('#score-board').load('score_board.php #score-board');
      }
      function reloadPlayed() {
        $('#played-cards').load('played_cards.php #played-cards');
      }
    </script>
    <script>  
      $(function () { 
        $("#popover").popover({
            placement: 'top',
            html: 'true',
            content : '<p>Not giving a shit about the Third World</p><button class="btn btn-small btn-block">'+
            'Play Card <i class="icon-share"></i></button>',
          });  
      });  
    </script>
    <script>
    jQuery(document).ready(function($) {
 
          $('#myCarousel').carousel({
                  interval: 5000
          });
  
          //Handles the carousel thumbnails
          $('[id^=cardID-]').click( function(){
                  var id_selector = $(this).attr("id");
                  var id = id_selector.substr(id_selector.indexOf("-")+1,1);
                  var id = parseInt(id);
                  $('#myCarousel').carousel(id);
          });
  });
  </script>
  </body>
</html>
