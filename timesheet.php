<?php
include "conn/conn.php";
$date=date("d-m-Y");
$name=$_POST['name'];
$days=$_POST['days'];
$situation=$_POST['situation'];
$project=$_POST['project'];
$sql=mysql_query("insert into timesheet(name,days,situation,project_id)
 values ('$name','$days','$situation','$project')")or die (mysql_error()); 
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