<?php
include "conn/conn.php";
$check=$dbh->prepare("select * from posts order by id desc");
$check->execute();
$count=$check->rowCount();
$fetch=$check->fetchAll();
foreach($fetch as $data)
	$output[]=$data;
	print json_encode($output);

?>