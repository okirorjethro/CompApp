<?php
include "conn/conn.php";
$telephone=$_POST['telephone'];
$pin=$_POST['oldpin'];
$new=$_POST['newpin'];

$check=$dbh->prepare("select * from users where telephone=:tel and pin=:pin");
$check->bindParam(":tel",$telephone);
$check->bindParam(":pin",$pin);
$check->execute();
$count=$check->rowCount();
$fetch=$check->fetchAll();
if($count > 0)
{
	$change=mysql_query("update users set pin='$new' where telephone='$telephone'");
	if($change)
	{
		 $response['success']=1;
		print json_encode($response);
	}

}//else 

else
{
	    $response['success']=0;
		print json_encode($response);
}
?>