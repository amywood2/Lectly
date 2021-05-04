<?php
$con=mysqli_connect("localhost","root","root","lectly");

$username= $_GET["username"];

$sql = "SELECT * FROM students WHERE username='$username' ";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
        'id'=>$row['id']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
