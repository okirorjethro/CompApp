<?php
include "conn/conn.php";
$date=date("F l d-m-Y");
$name=$_POST['name'];
$status=$_POST['status'];
$project=$_POST['project'];
$sql=mysql_query("insert into attendance(name,status,date,project)
 values ('$name','$status','$date','$project')")or die (mysql_error()); 
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