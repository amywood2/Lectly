<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_POST['student_id']) && isset($_POST['post_id'])) {
    if ($db->dbConnect()) {
        if ($db->addToSavedPosts("saved_posts", $_POST['student_id'], $_POST['post_id']) ) {
          echo "Your post has been successfully saved";
        } else echo "Failed to save post";
    } else echo "Error: Database connection";
} else echo "All fields are required";


?>
