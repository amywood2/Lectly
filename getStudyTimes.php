<?php
$con=mysqli_connect("localhost","root","root","lectly");

$sql = "SELECT * FROM student_study_times";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
        'student_id'=>$row['student_id'],
        'type'=>$row['type'],
        'timestamp'=>$row['timestamp']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
