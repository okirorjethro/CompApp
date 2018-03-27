<?php
include "conn/conn.php";
$project_id=$_POST['project'];

$check=$dbh->prepare("select * from material_report where project='$project_id' ");
$check->execute();
$count=$check->rowCount();
$fetch=$check->fetchAll();
if($count > 0)
{
	 foreach($fetch as $data)
		$output[]=$data;
		print json_encode($output);
}//else 

else
{
	    $response['success']=0;
		print json_encode($response);
}
?>