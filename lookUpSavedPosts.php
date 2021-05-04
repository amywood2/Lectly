<?php
$con=mysqli_connect("localhost","root","root","lectly");

$student_id= $_GET["student_id"];

$sql = "SELECT * FROM saved_posts WHERE student_id='$student_id'";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
        'post_id'=>$row['post_id']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
