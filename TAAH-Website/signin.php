<?php
// function signIn() {
  session_start();//Start a session so we can have global variables
  // $serverURL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/";
  $serverURL = "http://r01sjbkcc.device.mst.edu:3000/api/v1/";
  $_SESSION['serverURL'] = $serverURL;
  //Check if curl is installed
  if(!function_exists("curl_init")) die("cURL extension is not installed");
  //The URL for the server + sessions
  $url = $serverURL . "sessions";
  //Make an array of the POSTed email and password
  $data = array("user" => array("email" => $_POST["email"], "password" => $_POST["password"])); 
  //Turn the array into a json object
  $content = json_encode($data);
  //Create a curl object and set what needs to be set
  $curl = curl_init($url);
  curl_setopt($curl, CURLOPT_HEADER, false);
  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
  curl_setopt($curl, CURLOPT_HTTPHEADER,
          array("Content-Type: application/json","Accept:application/json"));
  curl_setopt($curl, CURLOPT_POST, true);
  curl_setopt($curl, CURLOPT_POSTFIELDS, $content);
  //Execute the curl POST and get the JSON response
  $json_response = curl_exec($curl);
  //Grab the status of the POST
  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
  //If the status is not 200, print out a lot of information of what went wrong
  // if ( $status != 200 ) {
      // die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
  // }
  //Close the curl instance
  if($status == 200) {
    //Grab the JSON response from the curl request
    $response = json_decode($json_response, true);
    // echo "the thing " . print_r($response) . "<br>";
    $_SESSION['val_login'] = true; 
    $_SESSION['auth_token'] = $response['data']['auth_token'];
    header("Location: /auth/lobby.php");
  }
  else if($status == 401) {
    $_SESSION['val_login'] = false; 
    header("Location: /index.php");
  }
  else {
    echo "Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl);
  }
  // echo "at least something is happening?";
  // $ch = curl_init("http://infinite-woodland-5610.herokuapp.com");    // initialize curl handle
    // curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    // $data = curl_exec($ch);
    // print($data);
  // curl_close($curl);
// }
?>