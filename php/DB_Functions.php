<?php
class DB_Functions {
	

public function addColaborador($s1, $s2, $s3, $s4, $s5)
{
$link = mysqli_connect("localhost", "root", "root", "dbWiseroom");
$query = "INSERT INTO tbColaborador (nome, idOrganizacao, email, administrador, senha) VALUES ('$s1', '$s2', '$s3', '$s4', '$s5')";
$result = mysqli_query($link, $query);
$link->close();
}
}

public function addSala($s1, $s2, $s3, $s4)
{
$link = mysqli_connect("localhost", "root", "root", "dbWiseroom");
$query = "INSERT INTO tbSala (nome, capacidade, areaDaSala, descricaoSala) VALUES ('$s1', '$s2', '$s3', '$s4')";
$result = mysqli_query($link, $query);
$link->close();
}
}

public function addReserva($s1, $s2, $s3, $s4, $s5, $s6)
{
	
	
	
$link = mysqli_connect("localhost", "root", "root", "dbWiseroom");

# select data count() 

$query = "INSERT INTO tbReserva (descricaoReserva, dataData, horaInicio, horaFim, idColaboradorReserva, idSalaReserva) VALUES ('$s1', '$s2', '$s3', '$s4', '$s5', '$s6')";
$result = mysqli_query($link, $query);
$link->close();
}
}

public function atualizaReserva($s1, $s2, $s3, $s4, $s5)
{
$link = mysqli_connect("localhost", "root", "root", "dbWiseroom");
$query = "UPDATE tbReserva SET descricaoReserva = '$s2', dataData = '$s3', horaInicio = '$s4', horaFim = '$5' WHERE idReserva = '$s1'";
$result = mysqli_query($link, $query);
$link->close();
}

public function getColaborador($s1, $s2)
{
$link = mysqli_connect("localhost", "root", "root", "dbWiseroom");
$query = "SELECT * from tbColaborador WHERE email = '$s1' AND senha = '$s2'";
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

public function delColaborador($id)
{
$link = mysqli_connect("localhost", "root", "root", "dbWiseroom");
$query = "DELETE FROM tbColaborador WHERE id = '$id'";
$result = mysqli_query($link, $query);
$link->close();
}

?>