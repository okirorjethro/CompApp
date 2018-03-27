<?php
include "conn/conn.php";
$tenant=$_POST['telephone'];
$check=$dbh->prepare("select * from complaints where receiver=:tenant group by sender");

$check->bindParam(":tenant",$tenant);
$check->execute();
$count=$check->rowCount();
$fetch=$check->fetchAll();
foreach($fetch as $data)
	$output[]=$data;
	print json_encode($output);

?>