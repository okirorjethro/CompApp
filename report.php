<?php
include "conn/conn.php";
$date=date("l F d-m-Y");
$report=$_POST['report'];
$project=$_POST['project'];

$sql=mysql_query("insert into report(report,project,date)
 values ('$report','$project','$date')")or die (mysql_error());
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