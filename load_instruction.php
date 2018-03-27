<?php
include "conn/conn.php";
 $material=$_POST['Material_Desc'];
 $instruction=$_POST['Instruction'];
 $sender=$_POST['sender'];
 $receiver=$_POST['reciever'];
  $project=$_POST['project'];

$sql=mysql_query("insert into  load_instruction(Material_Desc,Instruction,sender,reciever,project)
 values ('$material','$instruction','$sender','$receiver','$project')")or die (mysql_error());
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