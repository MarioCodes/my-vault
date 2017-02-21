package neodatis;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

/**
 * Test para ver como manipular NeoDatis.
 * @author Mario Codes Sánchez
 * @since 21/02/2017
 */
public class NeoDatis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        getDatos();
    }
    
    /**
     * Forma de realizar un Insert.
     */
    public static void insert() {
        ODB odb = ODBFactory.open("neodatis.test");// Abrir BD
        
        Jugadores j1 = new Jugadores("Maria", "voleibol", "Madrid", 14); // Crear instancias para almacenar en BD
        Jugadores j2 = new Jugadores("Miguel", "tenis", "Madrid", 15);
        Jugadores j3 = new Jugadores("Mario", "baloncesto", "Guadalajara", 15);
        Jugadores j4 = new Jugadores("Alicia", "tenis", "Madrid", 14);
        
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
        ODB odb = ODBFactory.open("neodatis.test");// Abrir BD
        
        Objects<Jugadores> objects = odb.getObjects(Jugadores.class);
        System.out.printf("%d Jugadores: %n", objects.size());

        int i = 1;
        // visualizar los objetos		
        while(objects.hasNext()){
           Jugadores jug = objects.next();
           System.out.printf("%d: %s, %s, %s %n",
                     i++, jug.getNombre(), jug.getDeporte(),
                     jug.getCiudad(), jug.getEdad());      
        }		
        odb.close(); // Cerrar BD				
    }
}

/**
 * Inner Class representacion de la Entidad. Creo que tiene que estar aqui obligatoriamente.
 * @author Mario Codes Sánchez
 */
class Jugadores {
    private String nombre;
    private String deporte;
    private String ciudad;
    private int edad;

    public Jugadores() {}
    public Jugadores(String nombre, String deporte,
                     String ciudad, int edad) {
       this.nombre = nombre;
       this.deporte = deporte;
       this.ciudad = ciudad;
       this.edad = edad;
    }
	
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getNombre() {return nombre;}
    public void setDeporte(String deporte) {this.deporte = deporte;}
    public String getDeporte() {return deporte;}
    public void setCiudad(String ciudad) {this.ciudad = ciudad;}
    public String getCiudad () {return ciudad;}
    public void setEdad(int edad) {this.edad = edad;}
    public int getEdad() {return edad;}
}

