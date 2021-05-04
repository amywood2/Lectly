<?php
require "DataBase.php";
$db = new DataBase();


if (isset($_POST['title']) && isset($_POST['description']) && isset($_POST['demonstration'])  && isset($_POST['studentWork']) && isset($_POST['module_id'])) {
    if ($db->dbConnect()) {
        if ($db->savePost("module_posts", $_POST['title'], $_POST['description'], $_POST['demonstration'], $_POST['studentWork'], $_POST['module_id'])) {
          echo "Your post has been successfully posted";
        } else echo "Post Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";


?>
