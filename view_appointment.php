<?php
include "conn/conn.php";
$receiver=$_POST['receiver'];
$check=$dbh->prepare("select * from appointments where receiver='$receiver' ");
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