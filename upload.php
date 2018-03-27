<?php
include "conn/conn.php";

	$stamp=time();
	$images_arr = array();
	$describe=$_POST['description'];
	$project=$_POST['project'];
	
		$image_name = $_FILES['images']['name'];
		$tmp_name 	= $_FILES['images']['tmp_name'];
		$size 		= $_FILES['images']['size'];
		$type 		= $_FILES['images']['type'];
		$error 		= $_FILES['images']['error'];
		$uploaddir = 'uploads/';
        $uploadfile = $uploaddir . basename($stamp."of".$image_name);
		move_uploaded_file($tmp_name,$uploadfile);
		
	    mysql_query("insert into uploads(project,description,photo)values('$project','$describe','$uploadfile')");
?>