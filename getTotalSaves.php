<?php
$con=mysqli_connect("localhost","root","root","lectly");

$post_id= $_GET["post_id"];

$sql = "SELECT * FROM total_saves WHERE post_id = '$post_id'";

$r = mysqli_query($con,$sql);

$result = array();

while($row = mysqli_fetch_array($r)){
    array_push($result,array(
        'no_of_saves'=>$row['no_of_saves']
    ));
}

echo json_encode(array('result'=>$result));

mysqli_close($con);

?>
