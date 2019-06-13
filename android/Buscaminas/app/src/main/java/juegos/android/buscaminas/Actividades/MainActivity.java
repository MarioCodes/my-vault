package juegos.android.buscaminas.Actividades;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import juegos.android.buscaminas.Controlador.Singleton;
import juegos.android.buscaminas.Juego.Tablero;
import juegos.android.buscaminas.R;

/**
 * 20 de marzo.
 *
 * Implementacion en Android del Juego del Buscaminas (Hipotenochas).
 * @author Mario Codes Sánchez
 * @since 17/01/2017
 * @see 'https://www.mkyong.com/android/android-prompt-user-input-dialog-example/'
 * @see 'http://www.flaticon.com/free-icon/mine-symbol_27983#term=mines&page=1&position=32'
 *
 * todo: Siguiente Paso: Avanzar con la logica del juego, añadir numeros a las casillas.
 *
 * todo: Como he cambiado el color de los botones, habra que cambiar el color o la animacion que hace al clickear. No se ve.
 * todo: Idea de juego, en vez de pedir parametros al usuario, poner niveles de dificultad (facil, medio, dificil, locura) y pasar yo las opciones por constructor.
 */
public class MainActivity extends AppCompatActivity {
    private final Context context = this; //Necesario para el menu pop-up.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_principal);
    }


    /**
     * Accion al pulsar el boton para ejecutar dificultad Facil.
     *  Paso como parametro entre actividades, las dimensiones del Tablero.
     * @param view Necesario para el boton.
     */
    public void botonAccionJuegoFacil(View view) {
        Singleton.getTablero(2, 1); //fixme: mas adelante equilibrar mas el numero de casillas / minas.

        Intent intent = new Intent(view.getContext(), ActividadJuego.class);
        //Bundle extras = intent.getExtras(); //Forma de pasar parametros entre Actividades.
        //extras.putInt("Dimensiones", 3);
        //extras.putInt("Minas", 1);

        //Para recogerlos en la otra:
        //Bundle b = getIntent().getExtras();
        //int index = b.getInt("index");

        startActivityForResult(intent, 0);
    }

    /**
     * Accion al pulsar el boton para ejecutar dificultad Medio.
     * @param view Necesario para el boton.
     */
    public void botonAccionJuegoMedio(View view) {
        Singleton.getTablero(3, 1);

        Intent intent = new Intent(view.getContext(), ActividadJuego.class);
        startActivityForResult(intent, 0);
    }

    /**
     * Accion al pulsar el boton para ejecutar dificultad Dificil.
     * @param view Necesario para el boton.
     */
    public void botonAccionJuegoDificil(View view) {
        Singleton.getTablero(3, 4);

        Intent intent = new Intent(view.getContext(), ActividadJuego.class);
        startActivityForResult(intent, 0);
    }

    public void botonAcercaDe(View view) {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.layout_acerca_de, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder.setCancelable(true);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Accion a ejecutar al pulsar el boton 'JUGAR'.
     *  Creara un menu pop-up para pasar parametros. Si 'Aceptar' instancia Tablero y llama a la nueva actividad.
     * @param view Contexto.
     */
    public void botonAccionJugar(View view) {
        //Esto es necesario para el pop-up cuando pulsamos el boton.
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.layout_input_parametros, null); //@todo: mirar como meter aqui el LandsScape.

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        //Atento a la forma de enlazar con la View por ID. NO es como siempre.
        //final EditText editTextTamanioTablero = (EditText) promptsView.findViewById(R.id.input_tamanio_tablero);
        //final EditText editTextNumeroMinas = (EditText) promptsView.findViewById(R.id.input_numero_minas);

        alertDialogBuilder.setCancelable(true);
        /* todo: mas adelante rescatarlo para hacer una opcion de buscaminas 'custom'.
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //Accion del boton cuando aceptamos.
                Singleton.getTablero(dimensiones, maximoMinas);

                Intent intent = new Intent(view.getContext(), ActividadJuego.class);
                startActivityForResult(intent, 0);
            }
        }); */

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Accion a ejecutar al pulsar el boton 'SALIR'.
     * @param view Contexto.
     */
    public void botonAccionSalir(View view) {
        System.exit(0);
    }
}
