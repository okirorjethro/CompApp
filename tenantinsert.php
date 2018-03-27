<?php
include "conn/conn.php";
$fullname=$_POST['fullname'];
$telephone=$_POST['telephone'];
$pin=$_POST['pin'];
$role=$_POST['role'];
$landlord=$_POST['landlord'];
$house=$_POST['house'];
$email=$_POST['email'];

$sql=mysql_query("insert into users (fullname,telephone,pin,role,email) values ('$fullname','$telephone','$pin','landlord','$email')");

if($sql)
{
	$sql2=mysql_query("insert into tenants (fullname,tenant,landlord,house,email) values ('$fullname','$telephone','$landlord','$house','$email')");
	
	if($sql2)
	{
		$response['success']=1;
		print json_encode($response);
	}
	
	else
	{
		$response['success']=1;
		print json_encode($response);
	}
	
}
	
?>