<?php
include "conn/conn.php";
$tenant=$_POST['tenant'];
$landlord=$_POST['landlord'];

$check=$dbh->prepare("select * from complaints where sender=:tenant and receiver=:land or sender=:land and receiver=:tenant ");
$check->bindParam(":tenant",$tenant);
$check->bindParam(":land",$landlord);
$check->execute();
$count=$check->rowCount();
$fetch=$check->fetchAll();

	foreach($fetch as $data)
	$output[]=$data;
		print json_encode($output);

?>