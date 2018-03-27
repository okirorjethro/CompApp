<?php
include "conn/conn.php";
 $employee=$_POST['employee'];
 $car=$_POST['car'];
 $condition=$_POST['condition'];
 $fuel=$_POST['fuel'];

$sql=mysql_query("insert into fleet_condition(employee,car,fleet_condition,fuel)
 values ('$employee','$car','$condition','$fuel')")or die (mysql_error());
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