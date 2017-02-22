
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

/**
 * Test para ver como manipular Main.
 * @author Mario Codes Sánchez
 * @since 21/02/2017
 */
public class Main {

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
////        insert();
//        getDatos();
//    }
    
    /**
     * Forma de realizar un Insert.
     */
    public static void insert() {
        ODB odb = ODBFactory.open("neodatis.test2");// Abrir BD
        
        Jugador j1 = new Jugador("Maria", "voleibol", "Madrid", 14); // Crear instancias para almacenar en BD
        Jugador j2 = new Jugador("Miguel", "tenis", "Madrid", 15);
        Jugador j3 = new Jugador("Mario", "baloncesto", "Guadalajara", 15);
        Jugador j4 = new Jugador("Alicia", "tenis", "Madrid", 14);
        
        odb.store(j1); 
        odb.store(j2);
        odb.store(j3);
        odb.store(j4);

        odb.close();
    }
    
    /**
     * Forma de hacer un SELECT ALL y mostrarlo.
     */
    public static void getDatos() {
        ODB odb = ODBFactory.open("neodatis.test2");// Abrir BD
        
        Objects<Jugador> objects = odb.getObjects(Jugador.class);
        System.out.printf("%d Jugadores: %n", objects.size());

        int i = 1;
        // visualizar los objetos		
        while(objects.hasNext()){
           Jugador jug = objects.next();
           System.out.printf("%d: %s, %s, %s %n",
                     i++, jug.getNombre(), jug.getDeporte(),
                     jug.getCiudad(), jug.getEdad());      
        }		
        odb.close(); // Cerrar BD				
    }
}

