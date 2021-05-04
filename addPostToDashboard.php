<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['post_id']) && isset($_POST['no_of_saves'])) {
    if ($db->dbConnect()) {
        if ($db->addPostToDashboard("total_saves", $_POST['post_id'], $_POST['no_of_saves']) ) {
          echo "Post added to dashboard";
        } else echo "Failed to add to dashboard";
    } else echo "Error: Database connection";
} else echo "All fields are required";


?>
