<?php
include "conn/conn.php";
$name=$_POST['name'];
$from=$_POST['sender'];
$to=$_POST['receiver'];
$date=$_POST['date'];
$time=$_POST['time'];


$sql=mysql_query("insert into appointments(sender,receiver,message,date,time)
 values ('$from','$to','$name','$date','$time')")or die (mysql_error());
if($sql)
{
	$output['success']=1;
}
else
{
	$output['success']=0;
}
print json_encode($output);
?>