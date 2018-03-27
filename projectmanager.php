<?php
include "conn/conn.php";
$foreman=$_POST['manager'];
$check=$dbh->prepare("select * from projects  where manager='$foreman' ");
$check->execute();
$count=$check->rowCount();
$fetch=$check->fetchAll();
if($count > 0)
{
	 foreach($fetch as $data)
		$output['success']= intval($data['id']);
		print json_encode($output);
}
else
{
	    $response['success']=0;
		print json_encode($response);
}
?>