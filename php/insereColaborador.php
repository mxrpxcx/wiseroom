<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['nome']) && isset($_POST['idOrganizacao']) && isset($_POST['email']) && isset($_POST['administrador']) && isset($_POST['senha']))
{
$s1 = $_POST['nome'];
$s2 = $_POST['idOrganizacao'];
$s3 = $_POST['email'];
$s4 = $_POST['administrador'];
$s5 = $_POST['senha'];

$db->addColaborador($s1, $s2, $s3, $s4, $s5);
}
?>

