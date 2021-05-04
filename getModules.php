<?php
$con=mysqli_connect("localhost","root","root","lectly");

$sql = "SELECT * FROM modules";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
        'module_name'=>$row['module_name'],
        'course'=>$row['course'],
        'year'=>$row['year']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
