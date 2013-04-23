<!DOCTYPE html>
<?php
  session_start();
  if(isSet($_SESSION['val_login'])) {
    //nothing
  }
  else {
    $_SESSION['val_login'] = true;
  }
  $signInURL = "/signin.php";
  $signUpURL = "/register.php";
?>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Sign in</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet" media="screen">
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
      #passwordError {
        display: none;
      }
      #register {
          margin-left:155px;
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
    <div class="container">
      <div class="row-fluid">
        <div class="span4 offset4" align="center">
          <h1>App Against Humanity</h1>
        </div>
      </div>

      <form class="form-signin" method="POST" action="<?php echo($signInURL);?>">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" name="email" class="input-block-level" placeholder="Email address">
        <input type="password" name="password" class="input-block-level" placeholder="Password">
        <?php
          if($_SESSION['val_login'] == false) {
            echo "<div id=\"passwordErrorLogin\" class=\"alert alert-error fade in\">
                <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>
                Username or password was incorrect.
              </div>";
          }
        ?>
        <button class="btn btn-medium btn-inverse" type="submit">Sign in</button>
        <a class="btn btn-primary offset1" id="register" align="right" data-toggle="modal" href="#signUp" >Sign up</a>
      </form><!--Sign in form-->
      
      
      
      <!--MOdal view for signing up-->
      <!-- Modal -->
      <div class="modal hide" id="signUp">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">x</button>
        </div>
        <div class="modal-body">
          <form class="form-horizontal" action="<?php echo($signUpURL);?>" method="POST" onsubmit="return validate(this);">
            <fieldset>
              <div id="legend">
                <legend class="">Register</legend>
              </div>
              <div class="control-group">
                <!-- Username -->
                <label class="control-label" for="username">Username</label>
                <div class="controls">
                  <input type="text" id="username" name="username" placeholder="Username" class="input-xlarge">
                </div>
              </div>
          
              <div class="control-group">
                <!-- E-mail -->
                <label class="control-label" for="email">E-mail</label>
                <div class="controls">
                  <input type="text" id="email" name="email" placeholder="example@awesome.com" class="input-xlarge">
                </div>
              </div>
          
              <div class="control-group">
                <!-- Password-->
                <label class="control-label" for="password">Password</label>
                <div class="controls">
                  <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
                  <p class="help-block">Password should be at least 4 characters</p>
                </div>
              </div>
              
              <div class="control-group">
                <!-- Password -->
                <label class="control-label"  for="password_confirm">Password (Confirm)</label>
                <div class="controls">
                  <input type="password" id="password_confirm" name="password_confirm" placeholder="" class="input-xlarge">
                  <p class="help-block">Please confirm password</p>
                </div>
              </div>
              
              <!--The alert if the passwords dont match-->
              <div id="passwordError" class="alert alert-error fade in">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <strong>Error!</strong> The password confirmation did not match.
              </div>
          
              <div class="control-group">
                <!-- Button -->
                <div class="controls">
                  <button class="btn btn-inverse" id="submit">Register</button>
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
    <script src="/javascript/confirmPass.js"></script>
    <script type="text/javascript">
      <!--
      if (screen.width <= 801) {
        document.location = "/redirect/smallscreen.php";
      }
      //-->
    </script>
    <script language=javascript>
    // <!--
      // if ((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPod/i))) {
        // location.replace("http://url-to-send-them/iphone.html");
      // }
    // -->
    </script>
    <script type="text/javascript"> // <![CDATA[
	// if ( (navigator.userAgent.indexOf('Android') != -1) ) {
		// document.location = "http://www.yoururladdress.com/yourpage.html";
	// } // ]]>
    </script>

  <script type="text/javascript">
    // <![CDATA[
      var mobileReject = (/iphone|ipad|ipod|blackberry|mini|windowssce|palm/i.test(navigator.userAgent.toLowerCase()));
      if (mobileReject) {
        document.location = "redirect/mobileother.php";
      }
    // ]]>
  </script>
  <script type="text/javascript">
    // <![CDATA[
      var mobileAccept = (/android/i.test(navigator.userAgent.toLowerCase()));
      if (mobileAccept) {
        document.location = "redirect/mobileandroid.php";
      }
    // ]]>
  </script>
    

  <!-- ... -->

  </body>
</html>
