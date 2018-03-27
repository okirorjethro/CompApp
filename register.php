<?php
include "conn/conn.php";

$fullname=$_POST['fullname'];
$telephone=$_POST['telephone'];
$pin=$_POST['pin'];
$role=$_POST['role'];
$email=$_POST['email'];

$sql=mysql_query("insert into users (fullname,telephone,pin,role,email) values ('$fullname','$telephone','$pin','$role','$email')")or die(mysql_error());

if($sql)

	{
		$response['success']=1;
		print json_encode($response);
	}
	
	else
	{
		$response['success']=1;
		print json_encode($response);
	}

	
?>