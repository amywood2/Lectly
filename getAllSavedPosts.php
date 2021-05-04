<?php
$con=mysqli_connect("localhost","root","root","lectly");

$id= $_GET["id"];

$sql = "SELECT * FROM module_posts WHERE id = '$id'";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
        'id'=>$row['id'],
        'title'=>$row['title'],
        'description'=>$row['description'],
        'demonstration'=>$row['demonstration'],
        'studentWork'=>$row['studentWork'],
        'module_id'=>$row['module_id']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
