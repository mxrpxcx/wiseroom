<?php
class DB_Functions {
	

public function insereColaborador($s1, $s2, $s3, $s4, $s5){

$link = mysqli_connect("172.30.248.130:3306", "root", "", "dbwiseroom") or die("Not connected.")
$query = "INSERT INTO tbcolaborador (nomeColaborador, organizacaoColaborador, emailColaborador, administrador, senhaColaborador) VALUES ('"$s1', '$s2', '$s3', '$s4', '$s5')";
$result = mysqli_query($link, $query);
$link->close();


}



public function insereSala($s1, $s2, $s3, $s4)
{
	
$link = mysqli_connect("172.30.248.130", "root", "root", "dbWiseroom");
$query = "INSERT INTO tbSala (nome, capacidade, areaDaSala, descricaoSala) VALUES ('$s1', '$s2', '$s3', '$s4')";
$result = mysqli_query($link, $query);
$link->close();

}


public function insereReserva($s1, $s2, $s3, $s4, $s5, $s6)
{
	
$link = mysqli_connect("172.30.248.130", "root", "root", "dbWiseroom");
$query = "INSERT INTO tbReserva (descricaoReserva, dataData, horaInicio, horaFim, idColaboradorReserva, idSalaReserva) VALUES ('$s1', '$s2', '$s3', '$s4', '$s5', '$s6')";
$result = mysqli_query($link, $query);
$link->close();

}


public function atualizaReserva($s1, $s2, $s3, $s4, $s5)
{
$link = mysqli_connect("172.30.248.130", "root", "root", "dbWiseroom");
$query = "UPDATE tbReserva SET descricaoReserva = '$s2', dataData = '$s3', horaInicio = '$s4', horaFim = '$5' WHERE idReserva = '$s1'";
$result = mysqli_query($link, $query);
$link->close();
}

public function listaColaborador()
{
$link = mysqli_connect("172.30.248.130", "root", "root", "dbWiseroom");
$query = "SELECT * from tbColaborador";
$result = mysqli_query($link, $query);
$return_array = array();

while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
{
$row_array['id'] = $row['id'];
$row_array['email'] = $row['email'];
$row_array['nome'] = $row['nome'];
$row_array['senha'] = $row['senha'];
$row_array['idOrganizacao'] = $row['idOrganizacao'];
$row_array['administrador'] = $row['administrador'];

array_push($return_array, $row_array);
}

echo json_encode($return_array);
$link->close();
}

public function listaSala($s1)
{
$link = mysqli_connect("172.30.248.130", "root", "root", "dbWiseroom");
$query = "SELECT * from tbSala WHERE idSala = 's$1'";
$result = mysqli_query($link, $query);
$return_array = array();

while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
{
$row_array['id'] = $row['id'];
$row_array['email'] = $row['email'];
$row_array['nome'] = $row['nome'];
$row_array['senha'] = $row['senha'];
$row_array['idOrganizacao'] = $row['idOrganizacao'];
$row_array['administrador'] = $row['administrador'];

array_push($return_array, $row_array);
}

echo json_encode($return_array);
$link->close();
}

public function removeColaborador($s1){
$link = mysqli_connect("172.30.248.130", "root", "root", "dbWiseroom");
$query = "DELETE FROM tbColaborador WHERE idColaborador = '$s1'";
$result = mysqli_query($link, $query);
$link->close();
}

}
?>