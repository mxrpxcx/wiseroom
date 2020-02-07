<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['nomeColaborador']) && isset($_POST['organizacaoColaborador']) && isset($_POST['emailColborador']) && isset($_POST['administrador']) && isset($_POST['senhaColaborador']))
{
$s1 = $_POST['nomeColaborador'];
$s2 = $_POST['organizacaoColaborador'];
$s3 = $_POST['emailColaborador'];
$s4 = $_POST['administrador'];
$s5 = $_POST['senhaColaborador'];

$db->insereColaborador($s1, $s2, $s3, $s4, $s5);
}
?>

