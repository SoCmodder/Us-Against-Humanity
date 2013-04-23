<!DOCTYPE html>
<html lang="en">
  <head>
    <?php
      $root = $_SERVER['DOCUMENT_ROOT'];
      include $root . '/php/gamefunctions.php';
      session_start();
      $joinedGames = getJoinedGames($_SESSION['auth_token']);
      $openGames = getOpenGames($_SESSION['auth_token']);
      // echo print_r($openGames);
      $createGameURL = "create_game.php";
      $joinGameURL = "add_user.php";
    ?>
    <meta charset="utf-8">
    <title>App Against Humanity</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Le styles -->
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet">
    <style>
      body {
        padding-top: 70px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
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
      require("navbar.php");
    ?>
    <div class="container">
      <div class="row-fluid">
        <div class="span4 offset4" align="center">
          <h2>Games I'm Playing</h2>
        </div>
        <div class="span2" align="right">
          <a class="btn btn-inverse" id="newGameButton" data-toggle="modal" href="#newGame">New Game <i class="icon-plus-sign icon-white"></i></a>
        </div>
      </div>
      <div class="row-fluid">
        <div class="span8 offset2">
          <table class="table table-hover table-bordered table-condensed">
            <thead>
              <tr id='link1'>
                <th>Room Creator</th>
                <th>Slots Open</th>
                <th>Points to Win</th>
                <th>Public/Private</th>
                <th>Play</th>
              </tr>
            </thead>            <?php              for($i=0;$i<sizeOf($joinedGames['data']['games']);$i++) {                echo "<tr id='link" . $i . "'>";                  echo "<td>" . getRoomCreator($joinedGames,$i) . "</td>";                  echo "<td>" . getNumberPlayers($joinedGames,$i) . "/" . getNumberSlots($joinedGames,$i) . "</td>";
                  echo "<td>" . getWinningScore($joinedGames,$i) . "/" . getPointsToWin($joinedGames,$i) . "</td>";
                  echo "<td>" . getPublicPrivate($joinedGames,$i) . "</td>";
                  echo "<td>";
                  echo "<form class=\"form\" style=\"padding:0; margin:0\" method=\"POST\" action=\"$joinGameURL\">"; //Needs to redirect from the joinGame php file to the game screen
                  echo "<input type=\"hidden\" name=\"game_id\" value=\"" . getGameID($joinedGames,$i) . "\">"; 
                  echo "<button class=\"btn btn-medium btn-inverse\" type=\"submit\">Play <i class=\"icon-play\"></i></button>";
                  echo "</form>";
                  echo "</td>";
                echo "</tr>";              }            ?>
          </table>
        </div>
      </div>
      <div class="row-fluid">
        <div class="span12" align="center">
          <h2>All Open Games</h2>
        </div>
      </div>
      <div class="row-fluid">
        <div class="span8 offset2">
          <table class="table table-hover table-bordered table-condensed">
            <thead>
              <tr id='link1'>
                <th>Room Creator</th>
                <th>Slots Open</th>
                <th>Points to Win</th>
                <th>Public/Private</th>
                <th>Join</th>
              </tr>
            </thead>
            <?php
            for($i=0;$i<sizeOf($openGames['data']['games']);$i++) {
                echo "<tr id='link" . $i . "'>";
                echo "<td>" . getRoomCreator($openGames,$i) . "</td>";
                echo "<td>" . getNumberPlayers($openGames,$i) . "/" . getNumberSlots($openGames,$i) . "</td>";
                echo "<td>" . getWinningScore($openGames,$i) . "/" . getPointsToWin($openGames,$i) . "</td>";
                echo "<td>" . getPublicPrivate($openGames,$i) . "</td>";
                echo "<td>";
                echo "<form class=\"form\" style=\"padding:0; margin:0\" method=\"POST\" action=\"$joinGameURL\">"; //Needs to redirect from the joinGame php file to the game screen
                echo "<input type=\"hidden\" name=\"game_id\" value=\"" . getGameID($openGames,$i) . "\">"; 
                echo "<button class=\"btn btn-medium btn-inverse\" type=\"submit\">Join <i class=\"icon-plus\"></i></button>";
                echo "</form>";
                echo "</tr>";
              }
              ?>
          </table>
        </div>
      </div>
      
      <!--Modal view for new game-->
      <div class="modal hide" id="newGame">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">x</button>
        </div>
        <div class="modal-body">
          <form class="form-horizontal" action="<?php echo($createGameURL);?>" method="POST">
            <fieldset>
              <div id="legend">
                <legend class="" align="center">New Game</legend>
              </div>
              <div class="control-group">
                <!-- Number of slots -->
                <label class="control-label" for="select">Number of Players </label>
                <div class="controls">
                  <select class="span2" name="slots">
                    <?php
                      echo "<option value=\"3\" selected=\"selected\">3</option>";
                      for($i=4;$i<=20;$i++) {
                        echo "<option value=\"$i\">$i</option>";
                      }
                    ?>
                  </select>
                </div>
              </div>
          
              <div class="control-group">
                <!-- Number of points to win -->
                <label class="control-label" for="select">Points to Win </label>
                <div class="controls">
                  <select class="span2" name="points_to_win">
                    <?php
                    echo "<option value=\"1\" selected=\"selected\">1</option>";
                      for($i=2;$i<=10;$i++) {
                        echo "<option value=\"$i\">$i</option>";
                    }
                    ?>
                  </select>
                </div>
              </div>
          
              <div class="control-group">
                <!-- Private -->
                <div class="controls">
                  <label class="checkbox">
                    <input type="checkbox" name="checkbox" value="">
                    Private Game?
                  </label>
                </div>
              </div>
              
              <div class="control-group">
                <!-- Button -->
                <div class="controls">
                  <button class="btn btn-inverse" id="submit">Create</button>
                </div>
              </div>
            </fieldset>
          </form><!--Sign up form-->
        </div>
      </div>
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="/bootstrap/js/jquery.js"></script>
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
  </body>
</html>
