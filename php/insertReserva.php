<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
if (isset($_POST['descricaoReserva']) && isset($_POST['dataData']) && isset($_POST['horaInicio']) && isset($_POST['horaFim']) && isset($_POST['idColaboradorReserva']) && isset($_POST['idSalaReserva']))
{
$s1 = $_POST['descricaoReserva'];
$s2 = $_POST['dataData'];
$s3 = $_POST['horaInicio'];
$s4 = $_POST['horaFim'];
$s5 = $_POST['idColaboradorReserva'];
$s6 = $_POST['idSalaReserva'];

$db->addColaborador($s1, $s2, $s3, $s4, $s5, $s6);
}
?>

