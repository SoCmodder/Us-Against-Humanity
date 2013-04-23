<!DOCTYPE html>
<html lang="en">
  <head>
    <?php
      
    ?>
    <meta charset="utf-8">
    <title>Bootstrap, from Twitter</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../appstrap/css/bootstrap.css" rel="stylesheet" media="screen">
    <style>
      body {
        padding-top: 65px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
      .whiteRowOne {
        margin-bottom:0px;
        margin-top:0px;
      }
      .whiteRowTwo {
        margin-bottom:0px;
        margin-top:0px;
      }
    </style>
    <link href="../appstrap/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../appstrap/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../appstrap/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../appstrap/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../appstrap/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../appstrap/ico/apple-touch-icon-57-precomposed.png">
    <link rel="shortcut icon" href="../appstrap/ico/favicon.png">
  </head>

  <body>
  
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">App Against Humanity</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="#">Games</a></li>
              <li><a href="#about">Users</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
          <!--TODO: Implement the logout and go to user buttons! & Add username to the text-->
          <div class="pull-right">
                <ul class="nav pull-right">
                    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Welcome, User <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="/user/preferences"><i class="icon-user"></i> User Account</a></li>
                            <li class="divider"></li>
                            <li><a href="/auth/logout"><i class="icon-off"></i> Logout</a></li>
                        </ul>
                    </li>
                </ul>
              </div>
        </div>
      </div>
    </div>
  
    <div class="container">
      <div class="row-fluid">
        <div class="span3" align="left">
          <div class="row-fluid">
            <ul class="thumbnails">
              <li>
                <a class="thumbnail">
                  <img src="http://placehold.it/300x300" alt=""/>
                </a>
              </li>
            </ul>
          </div>
          <table class="table table">
            <thead>
              <tr>
                <th>Player</th>
                <th># Points</th>
              </tr>
            </thead>
            <tr>
              <td>TheMourningDawn</td>
              <td>6</td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
            </tr>
          </table>
        </div>
        <div class="span9">
          <div class="row-fluid" style="margin-bottom:-25px">
            <ul class="thumbnails">
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
            </ul>
          </div><!--First white card row-->
          <!--<img data-src="http://placehold.it/300x300" alt="">-->
          <div class="row-fluid" style="margin-bottom:-30px;">
            <ul class="thumbnails">
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
              <li class="span3">
                <a class="thumbnail">
                  <img src="http://placehold.it/200x140" alt=""/>
                </a>
              </li>
            </ul>
          </div><!--Second row.-->
          
          <h3 align="center">Hand</h3>
          
          <div class="row-fluid" style="margin-bottom:-25px;">
            <ul class="thumbnails">
              <li class="span2" style="margin-left:60px;">
                <a class="thumbnail" id="popover" data-toggle="popover"">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
            </ul>
          </div><!--Hand row...-->
          <div class="row-fluid">
            <ul class="thumbnails">
              <li class="span2" style="margin-left:60px;">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
              <li class="span2">
                <a class="thumbnail">
                  <img src="http://placehold.it/125x110" alt=""/>
                </a>
              </li>
            </ul>
          </div><!--Hand row...-->
        </div>
      </div>
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="../appstrap/js/jquery.js"></script>
    <script src="../appstrap/js/bootstrap-transition.js"></script>
    <script src="../appstrap/js/bootstrap-alert.js"></script>
    <script src="../appstrap/js/bootstrap-modal.js"></script>
    <script src="../appstrap/js/bootstrap-dropdown.js"></script>
    <script src="../appstrap/js/bootstrap-scrollspy.js"></script>
    <script src="../appstrap/js/bootstrap-tab.js"></script>
    <script src="../appstrap/js/bootstrap-tooltip.js"></script>
    <script src="../appstrap/js/bootstrap-popover.js"></script>
    <script src="../appstrap/js/bootstrap-button.js"></script>
    <script src="../appstrap/js/bootstrap-collapse.js"></script>
    <script src="../appstrap/js/bootstrap-carousel.js"></script>
    <script src="../appstrap/js/bootstrap-typeahead.js"></script>
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
  </body>
</html>
if homo then pizza*homo
homo=1
pizza=2
