<?php

$response = array();
include '01.php';
include '02.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if(b) {
	if(isset($input['uuid']) && isset($input['f']) && isset($input['l']) && isset($input['c'])) {	
		$uuid = $input['uuid'];
		$f = $input['f'];
		$l = $input['l'];
		$c = $input['c'];
	
		if(!a($uuid)) 
		{
			$insertQuery = "INSERT INTO users(uuid, f_name, l_name, c) VALUES (?,?,?,?)";
			if($stmt = $con->prepare($insertQuery))
			{
				$stmt->bind_param("ssss",$uuid,$f,$l,$c);
				$stmt->execute();
				$response["status"] = 0;
				$response["message"] = "User created";
				$stmt->close();
			}
		}	
	
		else
		{
			$response["status"] = 1;
			$response["message"] = "User exists";
		}
	}
	else 
	{
	$response["status"] = 2;
	$response["message"] = "Missing data";
	}
} 
else 
{
	$response["status"] = 3;
	$response["message"] = "Registration ended";	
}

echo json_encode($response);
?>