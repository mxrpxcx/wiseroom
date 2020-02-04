<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['task']) && isset($_POST['cause']))
{
$s1 = $_POST['task'];
$s2 = $_POST['cause'];
$db->addTask($s1, $s2);
}
?>