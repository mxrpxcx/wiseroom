<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['id']))
{
$s = $_POST['id'];
$db->del($s);
}
?>