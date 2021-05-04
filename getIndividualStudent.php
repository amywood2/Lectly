<?php
$con=mysqli_connect("localhost","root","root","lectly");

$id= $_GET["id"];

$sql = "SELECT * FROM students WHERE id = '$id' ";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
        'fullname'=>$row['fullname'],
        'username'=>$row['username'],
        'email'=>$row['email']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
