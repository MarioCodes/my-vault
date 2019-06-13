package examen.preparacion.biblioteca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Adaptador.
 * Created by Mario on 26/02/2017.
 */

public class LibroAdapter extends ArrayAdapter<Libro>{
    private List<Libro> libros;
    private ImageView image_view_libro, image_view_notas, image_view_finalizado, image_view_prestado;
    private TextView text_view_titulo, text_view_autor, text_view_fecha;
    private RatingBar rating_bar;
    private ImageButton image_button_formato;

    /**
     * Constructor por defecto.
     * @param context
     * @param libros
     */
    public LibroAdapter(Context context, List libros) {
        super(context, R.layout.activity_libro, libros);
        this.libros = libros;
    }

    /**
     * Linkeo de los elementos XML con sus correspondientes Java.
     * @param item
     */
    private void linkeoItems(View item) {
        image_view_libro = (ImageView) item.findViewById(R.id.image_view_libro);
        image_view_notas = (ImageView) item.findViewById(R.id.image_view_notas);
        image_view_finalizado = (ImageView) item.findViewById(R.id.image_view_finalizado);
        image_view_prestado = (ImageView) item.findViewById(R.id.image_view_prestado);

        text_view_titulo = (TextView) item.findViewById(R.id.text_view_titulo);
        text_view_autor = (TextView) item.findViewById(R.id.text_view_autor);
        text_view_fecha = (TextView) item.findViewById(R.id.text_view_fecha);

        rating_bar = (RatingBar) item.findViewById(R.id.rating_bar);

        image_button_formato = (ImageButton) item.findViewById(R.id.image_button_formato);
    }

    /**
     * Cargado de datos intrinsecos del Libro a los campos de la ventana.
     * @param libro
     */
    private void outputVentana(Libro libro) { //Con el libro actual.
        text_view_titulo.setText(libro.getTitulo());
        text_view_autor.setText(libro.getAutor());
        text_view_fecha.setText(libro.getFechaInicio());
        rating_bar.setRating(libro.getValoracion());

        if(!(libro.getFechaFin().isEmpty() || libro.getFechaFin().matches(""))) image_view_finalizado.setImageResource(R.drawable.checked);
        else image_view_finalizado.setImageResource(R.drawable.unchecked);

        if(!(libro.getNotas().isEmpty() || libro.getNotas().matches(""))) image_view_notas.setImageResource(R.drawable.checked);
        else image_view_notas.setImageResource(R.drawable.unchecked);

        if(!(libro.getPrestado().isEmpty() || libro.getPrestado().matches(""))) image_view_prestado.setImageResource(R.drawable.checked);
        else image_view_notas.setImageResource(R.drawable.unchecked);

        switch(libro.getFormato()) {
            case "Fisisco":
                image_button_formato.setImageResource(R.drawable.book);
                break;
            case "Audio":
                image_button_formato.setImageResource(R.drawable.audio);
                break;
            case "Digital":
                image_button_formato.setImageResource(R.drawable.ebook);
                break;
            default:
                image_button_formato.setImageResource(R.drawable.book);
                break;
        }

        switch(libro.getIdioma()) {
            case "Aleman":
                image_view_libro.setImageResource(R.drawable.libroale);
                break;
            case "Catalan":
                image_view_libro.setImageResource(R.drawable.librocat);
                break;
            case "Chino":
                image_view_libro.setImageResource(R.drawable.librochi);
                break;
            case "Ingles":
                image_view_libro.setImageResource(R.drawable.libroeng);
                break;
            case "Español":
                image_view_libro.setImageResource(R.drawable.libroesp);
                break;
            case "Frances":
                image_view_libro.setImageResource(R.drawable.librofra);
                break;
            case "Gallego":
                image_view_libro.setImageResource(R.drawable.librogal);
                break;
            case "Italiano":
                image_view_libro.setImageResource(R.drawable.libroita);
                break;
            case "Vasco":
                image_view_libro.setImageResource(R.drawable.librovas);
                break;
            default:
                image_view_libro.setImageResource(R.drawable.libroale);
                break;
        }
    }

    /**
     * Iterator que va pasando por cada libro para cargar sus datos y hacer una entrada nueva por libro.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View item = layoutInflater.inflate(R.layout.activity_libro, null);

        linkeoItems(item);

        Libro libro = libros.get(position);
        outputVentana(libro);

        return item;
    }
}
