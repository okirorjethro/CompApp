<?php
include "conn/conn.php";

$date=date("d-m-Y");
$material=$_POST['material'];
$remaining=$_POST['remaining'];
$comment=$_POST['comments'];
$project=$_POST['project'];
$sql=mysql_query("insert into material_report(material,remaining,comments,date,project)
 values ('$material','$remaining','$comment','$date','$project')")or die (mysql_error()); 
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