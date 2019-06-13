<?php
function connectDB() {

    $server = "localhost";
    $user = "root";
    $pass = "";
    $bd = "proyecto";

    $conexion = mysqli_connect($server, $user, $pass, $bd);
	/*
	    if ($conexion) {
	        echo 'La conexion de la base de datos se ha hecho satisfactoriamente
	            ';
	    } else {
	        echo 'Ha sucedido un error inexperado en la conexion de la base de datos
	            ';
	    }
	*/
    return $conexion;
}

function disconnectDB($conexion) {
    $close = mysqli_close($conexion);
	/*
	    if ($close) {
	        echo 'La desconexion de la base de datos se ha hecho satisfactoriamente
	            ';
	    } else {
	        echo 'Ha sucedido un error inexperado en la desconexion de la base de datos
	            ';
	    }

*/    return $close;
}

function deleteAlojamiento($id, $nombre) {
    //Creamos la conexión con la función anterior
    $conexion = connectDB();

    //generamos la consulta
    mysqli_set_charset($conexion, "utf8"); //formato de datos utf8

	
	$sql = "DELETE FROM ALOJAMIENTO WHERE ID_ALOJAMIENTO = '$id' AND NOMBRE LIKE '%$nombre%';";
				 
    if (!$result = mysqli_query($conexion, $sql))
        die(); //si la conexión cancelar programa
	
    disconnectDB($conexion); //desconectamos la base de datos
}

deleteAlojamiento($_REQUEST['id'], $_REQUEST['nombre']);
?>