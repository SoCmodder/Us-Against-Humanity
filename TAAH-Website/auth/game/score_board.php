<!DOCTYPE html>
<div id="score-board">
  <table class="table table">
    <thead>
      <tr>
        <th>Player</th>
        <th>Points</th>
        <th>Played<th>
      </tr>
    </thead>
    <?php
      $playerScores = getScoreBoard($gameID,$auth_token);
      for($i=0;$i<sizeOf($playerScores);$i++) {
        echo "<tr>";
          echo "<td>" . $playerScores[$i]['name'] . "</td>";
          echo "<td>" . $playerScores[$i]['score'] . "</td>";
          if($playerScores[$i]['czar'] == true) {
            echo "<td><span class=\"label label-inverse\">CZAR</span></td>";
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
</div>