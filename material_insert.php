<?php
include "conn/conn.php";
$employee=$_POST['employee'];
$date=$_POST['date'];
$material=$_POST['material'];
$quantitye=$_POST['quantity'];
$per_cost=$_POST['unit'];
$extended=$_POST['extended'];
$project=$_POST['project'];

$sql=mysql_query("insert into materials(employee,date,material,quantity,per_unit_cost,extended_cost,project_id)
 values ('$employee','$date','$material','$quantitye','$per_cost','$extended','$project')")or die (mysql_error());
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