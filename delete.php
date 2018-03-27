<?php
include "conn/conn.php";
$msg=$_POST['message'];
$sql=mysql_query("delete from complains where id ='$msq')");
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