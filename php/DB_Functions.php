<?php
class DB_Functions {
	
public function add($s1, $s2)
{
$link = mysqli_connect("localhost", "root", "root", "task_database");
$query = "INSERT INTO task_table (_task, _cause) VALUES ('$s1', '$s2')";
$result = mysqli_query($link, $query);
$link->close();
}
}


public function get()
{
$link = mysqli_connect("localhost", "root", "root", "task_database");
$query = "SELECT * from task_table";
$result = mysqli_query($link, $query);
$return_array = array();
while ($row = mysqli_fetch_array($result, MYSQLI_ASSOC))
{
$row_array['id'] = $row['_id'];
$row_array['task'] = $row['_task'];
$row_array['cause'] = $row['_cause'];
array_push($return_array, $row_array);
}
echo json_encode($return_array);
$link->close();
}

public function del($id)
{
$link = mysqli_connect("localhost", "root", "root", "task_database");
$query = "DELETE FROM task_table WHERE _id = '$id'";
$result = mysqli_query($link, $query);
$link->close();
}

?>