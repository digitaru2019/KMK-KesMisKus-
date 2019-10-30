<?php
define('s1', "kasutaja");
define('s2', "parool");
define('s3', "andmebaas");
define('s4', "localhost");

$con = mysqli_connect(s4,s1,s2,s3);

if(mysqli_connect_errno())
{
	echo "Database connection failed: " . mysqli_connect_error();
}

?>