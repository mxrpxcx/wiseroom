<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['nomeSala']) && isset($_POST['capacidadeSala']) && isset($_POST['areaSala']) && isset($_POST['descricaoSala']))
{
$s1 = $_POST['nomeSala'];
$s2 = $_POST['capacidadeSala'];
$s3 = $_POST['areaSala'];
$s4 = $_POST['descricaoSala'];

$db->addSala($s1, $s2, $s3, $s4);
}
?>

