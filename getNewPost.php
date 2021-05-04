<?php
$con=mysqli_connect("localhost","root","root","lectly");

$title= $_GET["title"];

$sql = "SELECT * FROM module_posts WHERE title='$title'";

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
