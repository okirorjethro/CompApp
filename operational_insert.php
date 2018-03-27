<?php
include "conn/conn.php";
$employee=$_POST['employee'];
$pay=$_POST['payrole'];
$desc=$_POST['description'];
$pid=$_POST['project'];


$sql=mysql_query("insert into operation_cost(employee,pay_roll,work_description,project)
 values ('$employee','$pay','$desc','$pid')")or die (mysql_error());
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