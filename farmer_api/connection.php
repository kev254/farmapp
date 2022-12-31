<?php

$servername = "127.0.0.1";
$user = "endelev1_kevin";
$pass = "8829@Noma.kevin";
$database = "endelev1_fam";
 

$conn = new mysqli($servername, $user, $pass, $database);
 

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}



?>