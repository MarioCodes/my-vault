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

function getArraySQL($sql) {
    //Creamos la conexión con la función anterior
    $conexion = connectDB();

    //generamos la consulta
    mysqli_set_charset($conexion, "utf8"); //formato de datos utf8

    if (!$result = mysqli_query($conexion, $sql))
        die(); //si la conexión cancelar programa

    $alojamientos = array(); //creamos un array

    while ($row = mysqli_fetch_array($result)) {
        //header("Content-type: " . $row["imageType"]);
		//echo $row['department_name'];
		$id=$row['ID_ALOJAMIENTO'];
		$nombre=$row['NOMBRE'];
		$direccion_social=$row['DIRECCION_SOCIAL'];
		$razon_social=$row['RAZON_SOCIAL'];
		$telefono_contacto=$row['TELEFONO_CONTACTO'];
		$descripcion=$row['DESCRIPCION'];
		$valoracion=$row['VALORACION'];
		$fecha_apertura=$row['FECHA_APERTURA'];
		$num_habitaciones=$row['NUM_HABITACIONES'];
		$provincia=$row['Provincia'];
		
		$alojamientos[] = array('ID_ALOJAMIENTO'=> $id, 'NOMBRE'=> $nombre, 'DIRECCION_SOCIAL'=> $direccion_social, 'RAZON_SOCIAL'=> $razon_social, 'TELEFONO_CONTACTO'=> $telefono_contacto, 'DESCRIPCION'=> $descripcion, 'VALORACION'=> $valoracion, 'FECHA_APERTURA'=> $fecha_apertura, 'NUM_HABITACIONES'=> $num_habitaciones, 'PROVINCIA'=> $provincia);
    }

    disconnectDB($conexion); //desconectamos la base de datos

    return $alojamientos; //devolvemos el array
}

function getListaAlojamientos($idBuscar) {
    //echo 'Consiguiendo todos los departamentos';
    $sql = "Select * from Alojamiento where id_alojamiento = $idBuscar;";
    $myArray = getArraySQL($sql);
    //echo 'JSON:';
    echo json_encode($myArray);
}

getListaAlojamientos($_REQUEST['idBuscar']);
?>