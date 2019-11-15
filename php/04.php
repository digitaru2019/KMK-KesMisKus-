<?php

$response = array();
include '01.php';
include '02.php';

$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, TRUE);

if (isset($input["uuid"]))
{
	$uuid = $input["uuid"];
	
	if (a($uuid))
	{
		$insertQuery = "SELECT id,f_name,l_name,c FROM users WHERE uuid = ?";
		if ($stmt = $con->prepare($insertQuery))
		{
			$stmt->bind_param("s",$uuid);
			$stmt->execute();
			$stmt->bind_result($id,$f,$l,$c);
			if ($stmt->fetch())
			{
				$response["status"] = 0;
				$response["message"] = "Login successful";
				$response["f"] = $f;
				$response["l"] = $l;
				$response["c"] = $c;
				$response["id"] = (int) $id;
			} 
			else
			{
				$response["status"] = 2;
				$response["message"] = "Something went wrong";
			}	
		}
		$stmt->close();
		
		$query = "SELECT team,role FROM teams_members WHERE user = ?";
		if ($stmt = $con->prepare($query)) {
			$stmt->bind_param("i",$id);
			$stmt->execute();
			$stmt->bind_result($tid,$role);
			if ($stmt->fetch()) {
				$response["teamid"] = (int) $tid;
				$response["role"] = (int) $role;
			} else {
				$response["teamid"] = -1;
			}
		}
		$stmt->close();
		
		if ($response["id"] != -1) {
			$query = "SELECT n FROM teams WHERE id = ?";
			if ($stmt = $con->prepare($query)) {
				$stmt->bind_param("i",$tid);
				$stmt->execute();
				$stmt->bind_result($n);
				if ($stmt->fetch()) {
					$response["teamname"] = $n;
				} else {
					$response["teamname"] = "";
				}
			}
			$stmt->close();
		}
	} 
	else 
	{
		$response["status"] = 1;
		$response["message"] = "UUID Doesn't exist";
	}
}

echo json_encode($response);
?>