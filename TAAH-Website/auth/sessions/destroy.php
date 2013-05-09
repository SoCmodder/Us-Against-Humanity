<?php
  session_start();//Start a session so we can have global variables
  $auth_token = $_SESSION['auth_token'];
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $serverURL . "sessions?auth_token=" . $auth_token;
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER, array("Content-Type: application/json","Accept: application/json","Authorization: Token token=$auth_token"));
  // curl_setopt($curl, CURLOPT_POST, true);
  curl_setopt($curl, CURLOPT_CUSTOMREQUEST, 'DELETE');
  curl_setopt($curl, CURLOPT_POSTFIELDS, $content);
  //Execute the curl POST
  $json_response = curl_exec($curl);
  //Grab the status of the POST
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  if ( $status != 200 ) {
      die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  }
  //Close the curl instance
  curl_close($curl);
  $response = json_decode($json_response, true);
  if($status == 200) {
    session_unset();
    session_destroy();
    header("Location: /index.php");//Make this relative
  }
?>