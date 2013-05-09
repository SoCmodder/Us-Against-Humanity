<!DOCTYPE html>
<html lang="en">
  <head>
    <?php
      $root = $_SERVER['DOCUMENT_ROOT'];
      include $root . '/php/gamefunctions.php';
      session_start();
      $gameID = $_SESSION['game_current'];
      $auth_token = $_SESSION['auth_token'];
      $playWhiteURL = "play_white_card.php";
      // print $gameID;
      // print_r(who($auth_token));
      // print_r(getSessionVar());
    ?>
    <meta charset="utf-8">
    <title>App Against Humanity</title>
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
    <div id="get-session-vars" style="display:none;"> 
     <?php
      setInGameData($gameID,$auth_token);
     ?>
    </div>
    <?php
      require($root."/auth/navbar.php");
    ?>
    <div class="container">
      <div class="row-fluid">
        <?php 
        if($_SESSION['is_czar'] == true) {
          echo "<div class=\"span3\" style=\"margin-top: 10px;\">";
        } else {
          echo "<div class=\"span3\" style=\"margin-top: 30px;\">";
        }
        ?>
          <div class="row-fluid row-black" id="black_view">
            <ul class="thumbnails" id="black_card">
              <li>
                <div class="thumbnail card-group">
                  <a href="#" class="card-wrapper">
                    <div class="card">
                      <?php
                        $blackCard = getBlackCard($gameID,$auth_token);
                        echo "<img src=\"http://placehold.it/250x250/000000/ffffff&text=+\" alt=\"\"/>";
                        echo "<h4 class=\"blackcard-text\" style=\"color:white\">" . $blackCard['text'] . "</h4>"; 
                      ?>
                    </div>
                  </a>                        
                </div>
              </li>
            </ul>
          </div><!--Black card-->
          <div class="row-fluid" id="hand_display_row">
              <?php
                if($_SESSION['is_czar'] == false) {
                  require("hand_display.php");
                }
              ?>
          </div><!--Selected white card-->
        </div>
        <div class="span6" id="play_span">
        <div id="play_view">
          <div id="status_view" style="margin-bottom:0px;">
            <?php
              if($_SESSION['is_czar'] == true) {
                require("view_czar.php");
              }
              else {
                require("view_normal.php");
              }
            ?>
          </div>
          <?php
          if($_SESSION['is_czar'] == false) { 
          echo "<div id=\"hand_view\">";
            echo "<div class=\"row-fluid\" id=\"hand-title\" align=\"center\" style=\"margin-bottom:-10px;\">";
                if($_SESSION['num_white'] == 1){
                  echo "<h4>Play " . $_SESSION['num_white'] . " white card</h4>";
                }
                else {
                  echo "<h4>Play " . $_SESSION['num_white'] . " white cards</h4>";
                }
            echo "</div>";
            echo "<div class=\"row-fluid row-hand-container\">";
              require("hand.php");
            echo "</div>";
          echo "</div>"; 
          }
          ?>
        </div>
        </div><!--Status and winner selection-->
        
        <div class="span3" id="score_board">
            <?php
              require("score_board.php");
            ?>
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
      //Functions for reloading certain divs in the game page
      //Check for any buttons that need to be disabled
      function buttonCheck(checkVars) {
        console.log("check buttons is calling");
        // console.log(checkVars);
        //If the one and only card has been played, disable them all
        if (checkVars.has_played == true) {
          // console.log("has played is true, fired if");
          $("[id^=card-]").attr('disabled', true);
        }
        //If no cards have been played, then they don't need to be disabled
        else if (checkVars.has_played == false) {
          // console.log("has played is false, shit");
          $("[id^=card-]").attr('disabled', false);
        }
        //If one has been played, just disable that
        if(checkVars.played_one == true && checkVars.white_one != "") {
          console.log("played one is true, wtf");
          $('#card-'+checkVars.white_one).attr('disabled', 'disabled');
        }
      }
      //Reloads the status div, the one at the top. Works for normal and czar
      function reloadStatus(checkVars) {
        if(checkVars.is_czar == true) {
          console.log("reloadStatus czar is calling");
          $('#status_view').load('view_czar.php #czar_view', function() { 
            attachSubmitChooseWhite();
          });
        }
        else {
          console.log("reloadStatus player is calling");
          $('#status_view').load('view_normal.php #normal_view');
        }
      }
      //Reload the players hand
      function reloadHand() {
        console.log("reloadStatus hand is calling");
        $('#hand_view').load('game.php #hand_view', function() {
          console.log("reloadhand->carousel");
          $('[id^=cardID-]').click( function(){
            var id_selector = $(this).attr("id");
            var id = id_selector.substr(id_selector.indexOf("-")+1,1);
            var id = parseInt(id);
            $('#myCarousel').carousel(id);
          });
        });
      }
      function reloadHandView(checkVars) {
        console.log("reloadHandView is calling");
        $('#hand_display_row').load('game.php #hand_display_row', function() {
          attachSubmitWhite();
          buttonCheck(checkVars);
        });
      }
      function reloadScores() {
        console.log("reloadScores is calling");
        $('#score_board').load('score_board.php', function() {
          console.log('This is the callback for score reload...so it should have worked?');
        });
      }
      function reloadBlack() {
        $('#black_view').load('game.php #black_card');
      }
    </script>
    <script>
      //Function for refreshing important content on a time
      function startActivityRefresh() {
        console.log("activityRefesh has started");
        timer = setInterval(function() {
          var checkVars = setGameData();
          console.log(checkVars);
          reloadScores();
          reloadHandView(checkVars);
          reloadStatus(checkVars);
          reloadHand();
          reloadBlack();
        }, 15000)
      }
    </script>
    <script>
    //Carousel movement and display //GETS BROKEN SOMETIMES!!!?
    jQuery(document).ready(function($) {
          console.log("the document ready has fired");
          $('#myCarousel').carousel({
                  interval: 5000
          });
          $('[id^=cardID-]').click( function(){
                  // console.log("wtf i just clicked something");
                  var id_selector = $(this).attr("id");
                  var id = id_selector.substr(id_selector.indexOf("-")+1,1);
                  var id = parseInt(id);
                  $('#myCarousel').carousel(id);
          });
          var checkVars = setGameData();
          // console.log(checkVars);
          // console.log("in doc ready checkvars: " + checkVars);
          buttonCheck(checkVars);
          attachSubmitWhite();
          attachSubmitChooseWhite();
          startActivityRefresh();
    });
  </script>
  <script>
    // Gettings certain session variables //TODO: replace with prefered setInGameData call
  function getSessionVar() {
    var sessionVar;
    $.ajax({
      type: 'POST',
      url: "getSessionVar.php",
      dataType: 'json',
      async:false,
      success: function (data) {
        // console.log(data);
        sessionVar = data;
      }
    });
    return sessionVar;
  }
  </script>
  <script>
  //Gettings certain session variables //TODO: replace with prefered setInGameData call
  function setGameData() {
    var inGameData;
    $.ajax({
      type: 'POST',
      url: "/php/setgamedata.php",
      dataType: 'json',
      async:false,
      success: function (data) {
        inGameData = data;
      }
    });
    console.log("I just got some om game data vars!");
    return inGameData;
  }
  </script>
  <script> 
  //Make this a function because event handles are connected to objects when this is
  //called. So if a div gets reloaded we loose those handlers. 
  function attachSubmitWhite() {
    console.log("attaching the onclick submit handler");
    /* attach a submit handler to the form */
    $("[id^=play-card]").click(function(event) {
      /* stop form from submitting normally */
      event.preventDefault();
      /* get some values from elements on the page: */
      var $form = $( this ),
          id = $form.find( 'input[name="card_id"]' ).val(),
          text = $form.find( 'input[name="card_text"]' ).val();
      var $button = $form.find("button");
      /* Send the data using post */
      var posting = $.post( "play_white_card.php", { card_id: id, card_text: text } );
      /* Put the results in a div */
      posting.done(function( data ) {
        var sessionVars = getSessionVar();
        //If one out of one card is played, Show it at top, disable all play buttons, reload everything needed.
        if(sessionVars.cards_status == 1) {
          // console.log(data);
          $( "#played" ).empty().append( data );
          $( "#played" ).hide().fadeIn('slow');
          var checkVars = setGameData();
          reloadScores();
          // reloadHandView(checkVars);
          reloadStatus(checkVars);
          reloadHand();
          $("[id^=card-]").attr('disabled', 'disabled');
        }
        //If one of two cards are played, show at top, disable that button, reload variables.
        else if(sessionVars.cards_status == 2) {
          console.log("one of two played with id: " + id);
          $( "#played" ).empty().append( data );
          $( "#played" ).hide().fadeIn('slow');
          $('#card-'+id).attr('disabled', 'disabled');
          setGameData();
        }
        //If two of two cards are played, show second at top, disable all plays, reload everyting needed.
        else if(sessionVars.cards_status == 3) {
          $( "#played" ).append( data );
          $( "#played" ).hide().fadeIn('slow');
          $("[id^=card-]").attr('disabled', 'disabled');
          var checkVars = setGameData();
          reloadHandView(checkVars);
          reloadStatus(checkVars);
          reloadScores();
          reloadHand();
        }
      });
    });
  }
  </script>
  <script>
  function attachSubmitChooseWhite() {
    /* attach a submit handler to the form */
    $("[id^=winning-white-]").click(function(event) {
      /* stop form from submitting normally */
      event.preventDefault();
      //get the ID of the winning player for the post
      var id_selector = $(this).attr("id");
      var id = id_selector.substr(id_selector.lastIndexOf("-")+1);
      var id = parseInt(id);
      /* Send the data using post */
      var posting = $.post( "submit_winning_white.php", { gameuser_id: id } );
      /* Put the results in a div */
      posting.done(function( data ) {
        var checkVars = setGameData();
        $('#play_span').load('game.php #play_view', function() {
            reloadScores();
            reloadHandView(checkVars);
            attachSubmitWhite();
            reloadBlack();
          });
      });
    });
  }
  </script>
  </body>
</html>
