<?php

function a($uuid) {
	$query = "SELECT uuid FROM users WHERE uuid=?";
	global $con;
	if ($stmt = $con->prepare($query))
	{
		$stmt->bind_param("s",$uuid);
		$stmt->execute();
		$stmt->store_result();
		$stmt->fetch();
		if($stmt->num_rows == 1) 
		{
			$stmt->close();
			return true;
		}
		$stmt->close();
		
	}
	return false;
}

?>