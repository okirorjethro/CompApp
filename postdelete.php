<?php
include "conn/conn.php";
$post=$_POST['post'];
$sql=mysql_query("delete from posts where id ='$post'");
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