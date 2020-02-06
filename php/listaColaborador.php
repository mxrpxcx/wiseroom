<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['email']) && isset($_POST['senha']))
{
$s1 = $_POST['email'];
$s2 = $_POST['senha'];

$db->getColaborador($s1, $s2);
}
?>