package juegos.android.buscaminas.Actividades;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import juegos.android.buscaminas.Controlador.Singleton;
import juegos.android.buscaminas.Juego.Casilla;
import juegos.android.buscaminas.Juego.Juego;
import juegos.android.buscaminas.Juego.Tablero;
import juegos.android.buscaminas.R;

/**
 * Actividad encargada de la representacion del Juego.
 * @author Mario Codes Sánchez
 * @since 17/01/2017
 */
public class ActividadJuego extends AppCompatActivity {
    private Drawable drawableToggleButtonDefaultBackground = null;
    private TextView textViewNumeroMinas, textViewNumeroFlags;
    private GridLayout gridLayoutJuego; //GridView principal de Juego.
    private Context context = this;
    private int numeroFlagsCorrectas = 0;
    private int numeroFlagsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_juego);

        iniGridViewJuego();
    }

    /**
     * Manipulacion para añadir o quitar Flags del contador Base.
     * @param aniadir Si true suma +1 al numero de flags. Si false resta -1.
     */
    private void manipulacionAniadirFlag(boolean aniadir) {
        int numeroPrevioFlags = Integer.parseInt(textViewNumeroFlags.getText().toString());

        if(aniadir) numeroPrevioFlags++;
        else numeroPrevioFlags--;

        textViewNumeroFlags.setText(Integer.toString(numeroPrevioFlags));
    }

    private void recorridoCasillas(Casilla[][] casillas, Tablero tablero) {
        for (int i = 0; i < tablero.getDIMENSIONES(); i++) {
            for (int j = 0; j < tablero.getDIMENSIONES(); j++) {
                if(casillas[i][j].isPulsada()) casillas[i][j].getBoton().setText(Integer.toString(casillas[i][j].getNumeroPropio()));
            }
        }
    }

    private void setCoordenadasBien(Casilla[][] casillas, Tablero tablero) {
        for (int i = 0; i < tablero.getDIMENSIONES(); i++) {
            for (int j = 0; j < tablero.getDIMENSIONES(); j++) {
                int[] coordenadas = new int[2];
                coordenadas[0] = i;
                coordenadas[1] = j;
                casillas[i][j].setCOORDENADAS(coordenadas);
            }
        }
    }

    private void adecuacionDimensionesTableroGrafico(Tablero tablero, ToggleButton boton) {
        //Atributos Comunes
        boton.setText("");
        boton.setTextOn("");
        boton.setTextOff("");

        GridLayout gridLayoutJuego = (GridLayout) findViewById(R.id.grid_layout_principal_juego);

        //A. especificos.
        switch(tablero.getDIMENSIONES()) {
            case 2:
                gridLayoutJuego.setPadding(80, 290, 0, 0);
                boton.setHeight(150);
                boton.setWidth(150);
                break;
            case 3:
                gridLayoutJuego.setPadding(0, 300, 0, 0);
                boton.setHeight(50);
                boton.setWidth(50);
                break;
        }
    }

    /**
     * Inicializacion de la GridView.
     * @todo Cambiarlo para que en vez de String sea ocupado por botones de 2 estados, cuando esten implementadas las Casillas.
     */
    private void iniGridViewJuego() {
        final Tablero tablero = Singleton.getTablero();
        int dimensionesTablero = tablero.getDIMENSIONES();
        Juego.setTablero(tablero);

        textViewNumeroMinas = (TextView) findViewById(R.id.text_view_minas_output);
        textViewNumeroFlags = (TextView) findViewById(R.id.text_view_flags_output);

        textViewNumeroMinas.setText(Integer.toString(tablero.getMAXIMO_MINAS()));
        textViewNumeroFlags.setText("0");

        numeroFlagsLeft = tablero.getMAXIMO_MINAS();

        final Casilla[][] casillas = tablero.getCASILLAS();
        final ToggleButton[] casillasGraficas = new ToggleButton[dimensionesTablero*dimensionesTablero];

        gridLayoutJuego = (GridLayout) findViewById(R.id.grid_layout_principal_juego);
        gridLayoutJuego.setColumnCount(tablero.getDIMENSIONES());

        for(int indexFila = 0, indexDatos = 0; indexFila < casillas.length; indexFila++) {
            for(int indexCasilla = 0; indexCasilla < casillas[indexFila].length; indexCasilla++, indexDatos++) {
                casillasGraficas[indexDatos] = new ToggleButton(this);

                adecuacionDimensionesTableroGrafico(tablero, casillasGraficas[indexDatos]);
                casillas[indexFila][indexCasilla].setBoton(casillasGraficas[indexDatos]);

                if(casillas[indexFila][indexCasilla].isMina()) casillasGraficas[indexDatos].setBackgroundResource(R.drawable.mina_explotar);
                else casillasGraficas[indexDatos].setBackgroundResource(R.drawable.button);

                setCoordenadasBien(casillas, tablero);
                final int finalIndexFila = indexFila; //Hace falta que sean 'final' para poder acceder desde la inner class.
                final int finalIndexCasilla = indexCasilla;
                final int finalIndexDatos = indexDatos;
                final Tablero finalTablero = tablero;
                final int finalNumeroFlagsLeft = numeroFlagsLeft;

                casillasGraficas[indexDatos].setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Casilla casilla = casillas[finalIndexFila][finalIndexCasilla];
                        //if(casilla.getCOORDENADAS()[0] == 1 && casilla.getCOORDENADAS()[1] == 2) Toast.makeText(context, "SI", Toast.LENGTH_SHORT).show();
                        if(!casilla.isMina()) {
                            if(!casilla.isFlag()) {
                                casillasGraficas[finalIndexDatos].setText("" +casilla.getNumeroPropio());
                                casillasGraficas[finalIndexDatos].setClickable(false); //Hace falta hacer set de los 2 para que realmente funcione.
                                casillasGraficas[finalIndexDatos].setLongClickable(false); //@fixme: Ahora mismo solo se pondra clickeable = false si !mina && ! flag.
                            }
                            if(casilla.getNumeroPropio() == 0) {
                                Juego.checkCasillaCeros(casillas, casilla);
                                recorridoCasillas(casillas, tablero);
                            }
                        }
                        else Toast.makeText(context, "¡PAM!", Toast.LENGTH_SHORT).show();

                        //Toast.makeText(context, "Eje x: " +casilla.getCOORDENADAS()[0] +", Eje y:" +casilla.getCOORDENADAS()[1] +", es Mina: " +casilla.isMina(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, "" +casilla.getNumeroPropio(), Toast.LENGTH_SHORT).show();
                    }
                });

                casillasGraficas[indexDatos].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) { //todo: añadir aqui la imagen de la Flag.
                        Casilla casilla = casillas[finalIndexFila][finalIndexCasilla];
                        if(casilla.isFlag()) {
                            manipulacionAniadirFlag(false);
                            casilla.setFlag(false);
                            numeroFlagsLeft++;
                            if(casilla.isMina()) {
                                casillasGraficas[finalIndexDatos].setBackgroundResource(R.drawable.mina_explotar);
                                numeroFlagsCorrectas--;
                            }
                            else casillasGraficas[finalIndexDatos].setBackgroundResource(R.drawable.button);
                        } else {
                            if(numeroFlagsLeft > 0) {
                                manipulacionAniadirFlag(true);
                                numeroFlagsLeft--;
                                casilla.setFlag(true);
                                casillasGraficas[finalIndexDatos].setBackgroundResource(R.drawable.bandera);
                                if (casilla.isMina()) numeroFlagsCorrectas++;
                                if (numeroFlagsCorrectas == finalTablero.getMAXIMO_MINAS()) Toast.makeText(context, "HAS GANADO", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(context, "No quedan Banderas. Quita Alguna.", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
                gridLayoutJuego.addView(casillasGraficas[indexDatos]);
            }
        }
    }
}
