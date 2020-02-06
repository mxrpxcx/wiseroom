<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['idReserva']) && isset($_POST['descricaoReserva']) && isset($_POST['dataData']) && isset($_POST['horaInicio']) && isset($_POST['horaFim']))
{
$s1 = $_POST['idReserva'];
$s2 = $_POST['descricaoReserva'];
$s3 = $_POST['dataData'];
$s4 = $_POST['horaInicio'];
$s5 = $_POST['horaFim'];

$db->atualizaReserva($s1, $s2, $s3, $s4, $s5);
}
?>