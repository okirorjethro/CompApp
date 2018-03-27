<?php
include "conn/conn.php";
$project_id=$_POST['project_id'];

$check=$dbh->prepare("select * from  fleet_condition where project='$project_id' ");
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