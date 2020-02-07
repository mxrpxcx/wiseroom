<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['idReserva']) && isset($_POST['descricaoReserva']) && isset($_POST['dataReserva']) && isset($_POST['horaInicioReserva']) && isset($_POST['horaFimReserva']))
{
$s1 = $_POST['idReserva'];
$s2 = $_POST['descricaoReserva'];
$s3 = $_POST['dataReserva'];
$s4 = $_POST['horaInicioReserva'];
$s5 = $_POST['horaFimReserva'];

$db->atualizaReserva($s1, $s2, $s3, $s4, $s5);
}
?>