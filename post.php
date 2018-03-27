<?php
include "conn/conn.php";
$msg=$_POST['message'];
$date=date("l d/m/Y");
$sql=mysql_query("insert into posts (post,date) values ('$msg','$date')");

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