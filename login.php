<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['password'])) {
    if ($db->dbConnect()) {
        if ($db->logIn("lecturers", $_POST['username'], $_POST['password'])) {
            echo "Lecturer Logging in";
        } else if($db->logIn("students", $_POST['username'], $_POST['password'])) {
            echo "Student Logging in";
        } else echo "Username or Password wrong";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>