<?php
include "conn/conn.php";
$fullname=$_POST['fullname'];
$sender=$_POST['sender'];
$receiver=$_POST['receiver'];
$msg=$_POST['message'];
$ap=$_POST['appointment_date'];
$date=date("d.m.Y");
$status="notseen";
$sql=mysql_query("insert into complaints (fullname,sender,receiver,message,date,appointment_date,status) values ('$fullname','$sender','$receiver',
'$msg','$date','$appointment_date','$status')");

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