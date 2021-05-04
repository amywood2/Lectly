<?php
$con=mysqli_connect("localhost","root","root","lectly");

$id= $_GET["id"];

$sql = "SELECT * FROM modules WHERE id = '$id' ";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
      'id'=>$row['id'],
      'module_name'=>$row['module_name'],
      'course'=>$row['course'],
      'year'=>$row['year'],
      'module_lecturer_id'=>$row['module_lecturer_id']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
