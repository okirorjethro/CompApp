<?php
include "conn/conn.php";
$regid=$_POST['regid'];
$telephone=$_POST['telephone'];

$sql=mysql_query("update tenants set gcm='$regid' where tenant='$telephone'");
if($sql)
{
	    $response['success']=1;
		print json_encode($response);
}
else if(!$sql)
{
	 $response['success']=0;
		print json_encode($response);
}
	
?>