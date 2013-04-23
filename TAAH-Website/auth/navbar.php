<!DOCTYPE html>
<?php
  //$root = $_SERVER['DOCUMENT_ROOT'];
  $accountURL = "/auth/account/account.php";
  $contactURL = "/auth/contact/contact.php";
  $indexURL = "/index.php";
  $lobbyURL = "/auth/lobby.php";
  $usersURL = "/auth/users/users.php";
  $logoutURL = "/auth/sessions/destroy.php";
  
  $auth_token = $_SESSION['auth_token'];
  $player = who($auth_token);
  $player = $player['name'];
?>
<div class="navbar navbar-inverse navbar-fixed-top" id="top-navbar">
  <div class="navbar-inner">
    <div class="container">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <?php
        if(isSet($_SESSION['auth_token'])) {
          echo "<a class=\"brand\" href=\"$lobbyURL\">App Against Humanity</a>";
        }
        else {
          echo "<a class=\"brand\" href=\"$indexURL\">App Against Humanity</a>";
        }
      ?>
      <!--<a class="brand" href="#">App Against Humanity</a>-->
      <div class="nav-collapse collapse">
        <ul class="nav">
          <li><a href="/auth/lobby.php">Games</a></li>
          <?php
            if(isSet($_SESSION['active_page']) && $_SESSION['active_page'] == "users") {
              echo "<li><a class=\"active item\" href=\"$usersURL\">Users</a></li>";
            } else {
              echo "<li><a href=\"$usersURL\">Users</a></li>";
            }
            if(isSet($_SESSION['active_page']) && $_SESSION['active_page'] == "contact") {
              echo "<li><a class=\"active item\" href=\"$contactURL\">Contact</a></li>";
            } else {
              echo "<li><a href=\"$contactURL\">Contact</a></li>";
            }
          ?>
        </ul>
      </div><!--/.nav-collapse -->
      <!--TODO: Implement the logout and go to user buttons! & Add username to the text-->
      <div class="pull-right">
        <ul class="nav pull-right">
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome, <?php echo $player ?> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="<?php echo $accountURL?>"><i class="icon-user"></i> User Account</a></li>
                    <li class="divider"></li>
                    <li><a href="<?php echo $logoutURL?>"><i class="icon-off"></i> Logout</a></li>
                </ul>
            </li>
        </ul>
      </div>
    </div>
  </div>
</div><!--Navbar-->