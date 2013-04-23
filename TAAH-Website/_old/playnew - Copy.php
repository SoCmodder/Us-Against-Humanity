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
      
      .card-group .card-wrapper .card {
          position: relative;
          margin: 0 auto;
          display: table;
          overflow:hidden;
      }
      .card-group .card-wrapper .whitecard-text {
        position: absolute;      
        top: 2px;
        right: 2px;
        left: 5px;
      }
      .card-group .card-wrapper .blackcard-text {
        position: absolute;      
        top: 30px;
        right: 10px;
        left: 10px;
      }
      
    div.new_html_code {
      width:199px;
      height:200px;
      min-width:199px;
      min-height:200px;
      max-width:199px;
      max-height:200px;
      overflow:hidden;
      display:block;
      border:1px solid red;
    }
    .slide-text {
      position: absolute;
      top: 60px;
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
    </div><!--Navbar-->
    <div class="container">
      <div class="row-fluid">
        <div class="span9">
          <div class="row-fluid">
            <div class="span4">
              <ul class="thumbnails">
                <li>
                  <div class="thumbnail card-group">
                    <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                      <div class="card">
                        <img src="http://placehold.it/250x250/000000/ffffff&text=+" alt=""/>
                        <h4 class="blackcard-text" style="color:white">Life for American Indians was forever changed when the White Man introduced them to ____________.</h4><!--Implement card text here-->
                      </div>
                    </a>        
                  </div>
                </li>
              </ul>
            </div><!--Black card-->
            <div class="span8">
              <div class="row-fluid" style="margin-bottom:-25px">
                <ul class="thumbnails">
                  <li class="span2">
                    <div class="thumbnail card-group">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Praying the gay away.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="thumbnail card-group">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Former President George W. Bush.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="thumbnail card-group">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Mouth herpies.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="thumbnail card-group">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Passive-agressive post-it notes.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="thumbnail card-group">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Self loathing.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="thumbnail card-group">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Self loathing.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                </ul>
              </div><!--First played white card row-->
              <div class="row-fluid" style="margin-bottom:-30px;">
                <ul class="thumbnails">
                  <li class="span2">
                    <div class="thumbnail card-group">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Daddy issues.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="card-group thumbnail" height="100px">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Pumping out a baby every nine months.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="card-group thumbnail">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Having shotguns for legs.</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="card-group thumbnail">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h5 class="whitecard-text" style="font-size:15px">Not giving a shit about the Third World.</h5><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="card-group thumbnail">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Roofies</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                  <li class="span2">
                    <div class="card-group thumbnail">
                      <a href="#" class="card-wrapper"><!--Implement what happens on click,if anything-->
                        <div class="card">
                          <img src="http://placehold.it/200x260/ffffff/000000&text=+" alt=""/>
                          <h4 class="whitecard-text" style="font-size:15px">Roofies</h4><!--Implement card text here-->
                        </div>
                      </a>        
                    </div>
                  </li>
                </ul>
              </div><!--Second played Row.-->
            </div><!--played white cards-->
          </div><!--row for black card and played white cards-->
          <div class="row-fluid">
            <!-- Main Area -->
            <div id="main_area">
              <!-- Slider -->
              <div class="span3" id="carousel-bounding-box" style="margin-left:40px">
                <div id="myCarousel" class="carousel slide" style="height:200px; margin: 0 auto">
                        <!-- Carousel items -->
                        <div class="carousel-inner">
                                <div class="active item" data-slide-number="0">
                                  <div class="new_html_code">
                                    <h5 style="font-size:15px" class="slide-text">Not giving a shit about the Third World.</h5><!--Implement card text here-->
                                    <button class="btn btn-inverse">Play Card <i class="icon-plus-sign icon-white"></i></button>
                                  </div>
                                </div>
                                <div class="item" data-slide-number="1"><img src="http://placehold.it/200x200&text=two" /></div>
                                <div class="item" data-slide-number="2"><img src="http://placehold.it/200x200&text=three" /></div>
                                <div class="item" data-slide-number="3"><img src="http://placehold.it/200x200&text=four" /></div>
                                <div class="item" data-slide-number="4"><img src="http://placehold.it/200x200&text=five" /></div>
                                <div class="item" data-slide-number="5"><img src="http://placehold.it/200x200&text=six" /></div>
                                <div class="item" data-slide-number="6"><img src="http://placehold.it/200x200&text=seven" /></div>
                                <div class="item" data-slide-number="7"><img src="http://placehold.it/200x200&text=eight" /></div>
                                <div class="item" data-slide-number="8"><img src="http://placehold.it/200x200&text=nine" /></div>
                                <div class="item" data-slide-number="9"><img src="http://placehold.it/200x200&text=ten" /></div>
                        </div>
                        <!-- Carousel nav -->
                        <!--<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
                        <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>-->
                </div>
              </div>
              <div class="span8">
                <div class="row-fluid" style="margin-bottom:-25px;">
                  <ul class="thumbnails">
                    <li class="span2">
                      <a class="thumbnail" id="carousel-selector-0">
                        <img src="http://placehold.it/200x140&text=one" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-1">
                        <img src="http://placehold.it/200x140&text=two" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-2">
                        <img src="http://placehold.it/200x140&text=three" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-3">
                        <img src="http://placehold.it/200x140&text=four" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-4">
                        <img src="http://placehold.it/200x140&text=five" />
                      </a>
                    </li>
                  </ul>
                </div>
                
                <div class="row-fluid"> 
                  <ul class="thumbnails">            
                    <li class="span2">
                      <a class="thumbnail" id="carousel-selector-5">
                        <img src="http://placehold.it/200x140&text=six" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-6">
                        <img src="http://placehold.it/200x140&text=seven" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-7">
                        <img src="http://placehold.it/200x140&text=eight" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-8">
                        <img src="http://placehold.it/200x140&text=nine" />
                      </a>
                    </li>
                    <li class="span2" style="margin-left:5px;">
                      <a class="thumbnail" id="carousel-selector-9">
                        <img src="http://placehold.it/200x140&text=ten" />
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </div> <!--/Slider-->
          </div><!--row for hand-->
        </div><!--All cards-->
        <div class="span3">
          <table class="table table">
            <thead>
              <tr>
                <th>Player</th>
                <th>Points</th>
                <th>Played<th>
              </tr>
            </thead>
            <tr>
              <td>TheMourningDawn</td>
              <td>6</td>
              <td><span class="label label-inverse">Czar</span></td>
            </tr>
            <tr>
              <td>thisOtherGuy</td>
              <td>2</td>
              <td><i class="icon-ok-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
            <tr>
              <td>SomeAsshole</td>
              <td>1</td>
              <td><i class="icon-ban-circle"></i></td>
            </tr>
          </table>
        </div><!--Score board, always left side of screen -->
      </div><!--Main Row-->
    </div><!--Main container-->
    
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
    <script>
    jQuery(document).ready(function($) {
  
          $('#myCarousel').carousel({
                  interval: 5000
          });
  
          //Handles the carousel thumbnails
          $('[id^=carousel-selector-]').click( function(){
                  var id_selector = $(this).attr("id");
                  var id = id_selector.substr(id_selector.length -1);
                  var id = parseInt(id);
                  $('#myCarousel').carousel(id);
          });
  });
  </script>
  </body>
</html>
