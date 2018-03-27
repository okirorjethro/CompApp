<?php
include "conn/conn.php";
 $matname=$_POST['material_name'];
 $desc=$_POST['description'];
 $delqual=$_POST['delivery_quantity'];
 $delsender=$_POST['delivery_sender'];
 $delreceiver=$_POST['delivery_receiver'];

$sql=mysql_query("insert into  delivery_note(material_name,description,delivery_quantity,delivery_sender,delivery_receiver)
 values ('$matname','$desc','$delqual','$delsender','$delreceiver')")or die (mysql_error());
if($sql)
{
	$output['success']=1;
}
else
{
	$output['success']=0;
}
print json_encode($output);
?>