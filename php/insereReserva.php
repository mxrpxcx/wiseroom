<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['descricaoReserva']) && isset($_POST['dataReserva']) && isset($_POST['horaInicioReserva']) && isset($_POST['horaFimReserva']) && isset($_POST['idColaboradorReserva']) && isset($_POST['idSalaReserva']))
{
$s1 = $_POST['descricaoReserva'];
$s2 = $_POST['dataReserva'];
$s3 = $_POST['horaInicioReserva'];
$s4 = $_POST['horaFimReserva'];
$s5 = $_POST['idColaboradorReserva'];
$s6 = $_POST['idSalaReserva'];

$db->addReserva($s1, $s2, $s3, $s4, $s5, $s6);
}
?>

