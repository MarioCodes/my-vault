<!-- Comienzo con las bases de PHP.-->
<?php
    //Comentario
    /* Comentario multi linea */

    $edad = 1;

    function cumpleanios($edad) { //El ambito de las variables funciona igual que en Java.
        $edad += 5;
        return $edad;
    }

    //$edad = cumpleanios($edad);
    //print "Tienes: " .$edad ." años.";

    function comparar_cadenas($cadena1, $cadena2) {
        $resultado = strcmp($cadena1, $cadena2);

        print "Comparacion chequeando mayusculas: ";
        if(!$resultado) print "Coinciden.";
        else print "No Coinciden";

        $resultado = strcasecmp($cadena1, $cadena2);
        print "<br>Comparacion sin chequear mayusculas: ";
        if(!$resultado) print "Coinciden.";
        else print "No Coinciden";

    }

    //comparar_cadenas("Mario", "MARio");

    function triple_equal() {
        if(0 == false) print "Es Igual."; //Esto dara true ya que solo compara si son iguales, y en php 0 equivale a false.
        else print "No es igual.";

        if(0 === false) print "Es Igual."; //Esto dara false ya que compara que sean iguales, y ademas que sean el mismo tipo de dato.
        else print "<br>No es igual.";
    }

    //triple_equal();

    function constantes() {
        define("HOLA", "Hola Mundo!"); //Forma de definirlas, tienen ambito global.
        //print HOLA;

        $constant_name = "HOLA"; //Si una variable referencia a una constante, se debera llamar mediante metodo 'constant()'.
        //print constant($constant_name);

        print "Ejecutando linea: " .__LINE__ ." de PHP script: " .__FILE__ ."."; //Constantes predefinidas.
    }

    constantes();
?>