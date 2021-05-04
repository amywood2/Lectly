<?php
$con=mysqli_connect("localhost","root","root","lectly");

$post_id= $_GET["post_id"];

$sql = "UPDATE total_saves SET no_of_saves = no_of_saves + 1 WHERE post_id= '$post_id' ";

$r = mysqli_query($con,$sql);

$result = array();

if ($conn->query($sql) === TRUE) {
  echo "Record updated successfully";
} else {
  echo "Error updating record: " . $conn->error;
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
