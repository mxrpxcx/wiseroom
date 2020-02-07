<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['idSala']))
{
$s1 = $_POST['idSala'];
$db->getSala($s1);
}
?>