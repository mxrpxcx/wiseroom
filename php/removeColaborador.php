<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['idColaborador']))
{
$s1 = $_POST['idColaborador'];
$db->delColaborador($s1);
}
?>