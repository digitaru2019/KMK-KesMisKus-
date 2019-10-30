<?php

$response = array();
include '01.php';
include '02.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input['uuid']))
{
	$uuid = $input['uuid'];
	
	if (a($uuid))
	{
		$insertQuery = "SELECT f_name,l_name,c FROM users WHERE uuid = ?";
		if ($stmt = $con->prepare($insertQuery))
		{
			$stmt->bind_param("s",$uuid);
			$stmt->execute();
			$stmt->bind_result($f,$l,$c);
			if ($stmt->fetch())
			{
				$response["status"] = 0;
				$response["message"] = "Login successful";
				$response["f"] = $f;
				$response["l"] = $l;
				$response["c"] = $c;
			} 
			else
			{
				$response["status"] = 2;
				$response["message"] = "Something went wrong";
			}	
		}
		$stmt->close();
	} 
	else 
	{
		$response["status"] = 1;
		$response["message"] = "UUID Doesn't exist";
	}
}

echo json_encode($response);
?>