package examen.preparacion.biblioteca;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Mario on 26/02/2017.
 */

public class AltaLibro extends Activity {
    private int id;
    private boolean existe = false; //Para saber si un libro hay que hacer setDatos o insert.
    private LibrosSQLiteHelper librosSQLiteHelper = new LibrosSQLiteHelper(AltaLibro.this);

    private Spinner spinner_tipo, spinner_idioma, spinner_formato;
    private TextView text_view_fecha_ini, text_view_fecha_fin;
    private EditText edit_text_titulo, edit_text_autor, edit_text_prestado_a, edit_text_notas;
    private RatingBar rating_bar_valoracion;
    private Button button_cambiar_fecha_ini, button_cambiar_fecha_fin, button_introducir;
    private ArrayAdapter<String> adaptadorTipos, adaptadorIdiomas, adaptadorFormatos;

    private final String[] tipos = new String[] {"Literatura", "Historia", "Biografia", "Ciencia Ficcion", "Ciencia", "Infantil", "Idiomas"};
    private final String[] idiomas = new String[] {"Español", "Catalan", "Aleman", "Chino", "Ingles", "Gallego", "Frances", "Italiano", "Vasco"};
    private final String[] formatos = new String[] {"Fisico", "Audio", "Digital"};

    /**
     * Codigo a ejecutarse al crearse.
     * @param savedInstanceBundle
     */
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_insert);

        linkeoIni();
        adaptadores();
        setDatos();
    }

    /**
     * Linkeo de todos los elementos XML con su correspondiente Java.
     */
    private void linkeoIni() {
        spinner_tipo = (Spinner) findViewById(R.id.spinner_tipo);
        spinner_idioma = (Spinner) findViewById(R.id.spinner_idioma);
        spinner_formato = (Spinner) findViewById(R.id.spinner_formato);

        text_view_fecha_ini = (TextView) findViewById(R.id.text_view_output_fecha_inic);
        text_view_fecha_fin = (TextView) findViewById(R.id.text_view_output_fecha_fin);

        edit_text_titulo = (EditText) findViewById(R.id.edit_text_input_titulo);
        edit_text_autor = (EditText) findViewById(R.id.edit_text_input_autor);
        edit_text_prestado_a = (EditText) findViewById(R.id.edit_text_input_prestado_a);
        edit_text_notas = (EditText) findViewById(R.id.edit_text_input_notas);

        rating_bar_valoracion = (RatingBar) findViewById(R.id.rating_bar_valoracion);

        button_cambiar_fecha_ini = (Button) findViewById(R.id.button_cambiar_fecha_ini);
        button_cambiar_fecha_fin = (Button) findViewById(R.id.button_cambiar_fecha_fin);
        button_introducir = (Button) findViewById(R.id.button_introducir_registro);
    }

    /**
     * Linkeo y establecimiento de los adaptadores con sus datos propios.
     */
    private void adaptadores() {
        adaptadorTipos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos);
        adaptadorTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adaptadorIdiomas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idiomas);
        adaptadorIdiomas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adaptadorFormatos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, formatos);
        adaptadorFormatos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_tipo.setAdapter(adaptadorTipos);
        spinner_idioma.setAdapter(adaptadorIdiomas);
        spinner_formato.setAdapter(adaptadorFormatos);
    }

    /**
     * Pick y get de la fecha de inicio de lectura.
     * @param view
     */
    public void seleccionarFechaIni(View view) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String texto = " " +day +"/" +(month+1) +"/" +year;
                text_view_fecha_ini.setText(texto);
            }
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    /**
     * Pick y get de la fecha de fin de lectura.
     * @param view
     */
    public void seleccionarFechaFin(View view) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String texto = " " +day +"/" +(month+1) +"/" +year;
                text_view_fecha_fin.setText(texto);
            }
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    /**
     * Almacenamiento. Si se ha entrado en esta ventana con datos en el intent, es que el libro existe y se debe hacer Update.
     * Si el intent es null, es que el libro es nuevo y se debe hacer insert.
     * @param view
     */
    public void guardar(View view) {
        if(existe) {
            librosSQLiteHelper.update(id, spinner_tipo.getSelectedItem().toString(), edit_text_titulo.getText().toString(), edit_text_autor.getText().toString(), spinner_idioma.getSelectedItem().toString(),
                    spinner_formato.getSelectedItem().toString(), edit_text_prestado_a.getText().toString(), edit_text_notas.getText().toString(), text_view_fecha_ini.getText().toString(),
                    text_view_fecha_fin.getText().toString(), rating_bar_valoracion.getRating());
            finish();
        } else {
            if(edit_text_titulo.getText().toString().isEmpty() || edit_text_autor.getText().toString().isEmpty() || text_view_fecha_ini.getText().toString().isEmpty())
                Toast.makeText(this, "Los campos Titulo, Autor y Fecha de Inicio son Obligatorios.", Toast.LENGTH_SHORT).show();
            else {
                librosSQLiteHelper.insert(spinner_tipo.getSelectedItem().toString(), edit_text_titulo.getText().toString(), edit_text_autor.getText().toString(), spinner_idioma.getSelectedItem().toString(),
                        spinner_formato.getSelectedItem().toString(), text_view_fecha_ini.getText().toString(), text_view_fecha_fin.getText().toString(), edit_text_prestado_a.getText().toString(),
                        rating_bar_valoracion.getRating(), edit_text_notas.getText().toString());

                finish();
            }
        }
    }

    /**
     * Chequea si la ventana viene con Bundle. Si hay datos, los extrae y hace output en la ventana.
     */
    private void setDatos() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            existe = true;
            id = extras.getInt("_id");
            String categoria = extras.getString("categoria");
            String formato = extras.getString("formato");
            String idioma = extras.getString("idioma");

            String titulo = extras.getString("titulo");
            String autor = extras.getString("autor");
            String fechainicio = extras.getString("fechainicio");
            String fechafin = extras.getString("fechafin");
            float valoracion = extras.getFloat("valoracion");
            String prestado = extras.getString("prestado");
            String notas = extras.getString("notas");

            spinner_tipo.setSelection(adaptadorTipos.getPosition(categoria));
            spinner_formato.setSelection(adaptadorFormatos.getPosition(formato));
            spinner_idioma.setSelection(adaptadorIdiomas.getPosition(idioma));

            edit_text_titulo.setText(titulo);
            edit_text_autor.setText(autor);
            edit_text_prestado_a.setText(prestado);
            edit_text_notas.setText(notas);

            text_view_fecha_ini.setText(fechainicio);
            text_view_fecha_fin.setText(fechafin);

            rating_bar_valoracion.setRating(valoracion);
        } else {
            existe = false;
        }
    }
}
