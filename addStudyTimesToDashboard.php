<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['student_id']) && isset($_POST['type']) && isset($_POST['timestamp'])){
    if ($db->dbConnect()) {
        if ($db->addStudyTimesToDashboard("student_study_times", $_POST['student_id'], $_POST['type'], $_POST['timestamp'])) {
          echo "Study time recorded on dashboard";
        } else echo "Failed to record study time";
    } else echo "Error: Database connection";
} else echo "All fields are required";


?>
