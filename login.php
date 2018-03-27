<?php
include "conn/conn.php";
$telephone=$_POST['telephone'];
$pin=$_POST['pin'];

$check=$dbh->prepare("select * from users where telephone=:tel and pin=:pin");
$check->bindParam(":tel",$telephone);
$check->bindParam(":pin",$pin);
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