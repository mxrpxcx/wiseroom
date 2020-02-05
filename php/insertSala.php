<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['nome']) && isset($_POST['capacidade']) && isset($_POST['areaDaSala']) && isset($_POST['descricaoSala']))
{
$s1 = $_POST['nome'];
$s2 = $_POST['capacidade'];
$s3 = $_POST['areaDaSala'];
$s4 = $_POST['descricaoSala'];

$db->addSala($s1, $s2, $s3, $s4);
}
?>

