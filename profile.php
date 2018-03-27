<?php
include "conn/conn.php";
$landlord=$_POST['telephone'];

$check=$dbh->prepare("select * from users where telephone=:land ");
$check->bindParam(":land",$landlord);
$check->execute();
$count=$check->rowCount();
$fetch=$check->fetchAll();
if($count > 0)
{
	foreach($fetch as $data)
	{
		$output[]=$data;
		print json_encode($output);
	}

}//else 

else
{
	    $response['success']=0;
		print json_encode($response);
}
?>