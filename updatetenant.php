<?php
include "conn/conn.php";
$telephone=$_POST['telephone'];
$status=$_POST['status'];

	$change=mysql_query("update tenants set status='$status' where tenant='$telephone'");
	if($change)
	{
		 $response['success']=1;
		print json_encode($response);
	}
 

else
{
	    $response['success']=0;
		print json_encode($response);
}
?>