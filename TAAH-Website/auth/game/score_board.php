<!DOCTYPE html>
<table class="table table" id="scores">
  <thead>
    <tr>
      <th>Player</th>
      <th>Points</th>
      <th>Played<th>
    </tr>
  </thead>
  <?php
    $sid = session_id();
    if(!$sid) {
      session_start();
    }
    $root = $_SERVER['DOCUMENT_ROOT'];
    include_once $root . '/php/gamefunctions.php';
    $gameID = $_SESSION['game_current'];
    $auth_token = $_SESSION['auth_token'];
    $playerScores = $_SESSION['player_scores'];
    
    for($i=0;$i<sizeOf($playerScores);$i++) {
      echo "<tr>";
        echo "<td>" . $playerScores[$i]['name'] . "</td>";
        echo "<td>" . $playerScores[$i]['score'] . "</td>";
        if($playerScores[$i]['czar'] == true) {
          $_SESSION['czar'] = $playerScores[$i]['name'];
          echo "<td><span class=\"label label-inverse\">Czar</span></td>";
        }
        if($playerScores[$i]['submitted'] == true && $playerScores[$i]['czar'] == false) {
          echo "<td><i class=\"icon-ok-circle\"></i></td>";
        }
        elseif($playerScores[$i]['submitted'] == false && $playerScores[$i]['czar'] == false) {
          echo "<td><i class=\"icon-ban-circle\"></i></td>";
        }
      echo "</tr>";
    }
  ?>
</table>