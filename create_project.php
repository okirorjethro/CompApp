<?php
include "conn/conn.php";
$date=date("d-m-Y");
$name=$_POST['name'];
$manager=$_POST['manager'];
$foreman=$_POST['foreman'];
$check=$dbh->prepare("select * from projects where manager='$manager' ");
$check->execute();
$count=$check->rowCount();
$sql=mysql_query("insert into projects(name,manager,foreman,date)
 values ('$name','$manager','$foreman','$date')")or die (mysql_error()); 
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