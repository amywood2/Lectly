<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $fullname, $email, $username, $password)
    {
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (fullname, username, password, email) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function savePost($table, $title, $description, $demonstration, $studentWork, $module_id)
    {
        $title = $this->prepareData($title);
        $description = $this->prepareData($description);
        $demonstration = $this->prepareData($demonstration);
        $studentWork = $this->prepareData($studentWork);
        $smodule_id = $this->prepareData($module_id);

        $this->sql =
            "INSERT INTO " . $table . " (title, description, demonstration, studentWork, module_id) VALUES ('" . $title . "','" . $description . "','" . $demonstration . "','" . $studentWork . "','" . $module_id . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function addToSavedPosts($table, $student_id, $post_id)
    {
        $student_id = $this->prepareData($student_id);
        $post_id = $this->prepareData($post_id);

        $this->sql =
            "INSERT INTO " . $table . " (student_id, post_id) VALUES ('" . $student_id . "','" . $post_id . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function addPostToDashboard($table, $post_id, $no_of_saves)
    {
        $post_id = $this->prepareData($post_id);
        $no_of_saves = $this->prepareData($no_of_saves);

        $this->sql =
            "INSERT INTO " . $table . " (post_id, no_of_saves) VALUES ('" . $post_id . "','" . $no_of_saves . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }


    function addStudyTimesToDashboard($table, $student_id, $type, $timestamp)
    {
        $student_id = $this->prepareData($student_id);
        $type = $this->prepareData($type);
        $timestamp = $this->prepareData($timestamp);

        $this->sql =
            "INSERT INTO " . $table . " (student_id, type, timestamp) VALUES ('" . $student_id . "','" . $type . "','" . $timestamp . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }



}

?>
