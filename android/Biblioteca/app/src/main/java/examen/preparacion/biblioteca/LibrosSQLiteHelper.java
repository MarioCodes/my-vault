package examen.preparacion.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario on 26/02/2017.
 */

public class LibrosSQLiteHelper extends SQLiteOpenHelper {
    Context context;
    String sqlCreate = "CREATE TABLE Libro(_id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
            "\t categoria TEXT NOT NULL, titulo TEXT NOT NULL, autor TEXT NOT NULL, idioma TEXT, " +
            "fecha_lectura_ini LONG, fecha_lectura_fin LONG, prestado_a TEXT, valoracion FLOAT, " +
            "formato TEXT, notas TEXT)";

    /**
     * Constructor por defecto.
     * @param context
     */
    public LibrosSQLiteHelper(Context context) {
        super(context, "Biblioteca", null, 1);
        this.context = context;
    }

    /**
     * Constructor para cuando se comience la aplicacion.
     * @param context
     * @param nombre
     * @param factory
     * @param version
     */
    public LibrosSQLiteHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Biblioteca");
    }

    public void insert(String categoria, String titulo, String autor, String idioma, String formato, String fecha_lectura_ini, String fecha_lectura_fin, String prestado_a, float valoracion, String notas) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("categoria", categoria);
        valores.put("titulo", titulo);
        valores.put("autor", autor);
        valores.put("idioma", idioma);
        valores.put("formato", formato);
        valores.put("fecha_lectura_ini", fecha_lectura_ini);
        valores.put("fecha_lectura_fin", fecha_lectura_fin);
        valores.put("prestado_a", prestado_a);
        valores.put("valoracion", valoracion);
        valores.put("notas", notas);

        sqLiteDatabase.insert("Libro", "null", valores);
        sqLiteDatabase.close();
        Toast.makeText(context, "Libro Insertado.", Toast.LENGTH_SHORT).show();
    }

    public void update(int id, String categoria, String titulo, String autor, String idioma, String formato, String prestado, String notas, String fechaInicio, String fechaFin, float valoracion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("categoria", categoria);
        valores.put("titulo", titulo);
        valores.put("autor", autor);
        valores.put("idioma", idioma);
        valores.put("formato", formato);
        valores.put("fecha_lectura_ini", fechaInicio);
        valores.put("fecha_lectura_fin", fechaFin);
        valores.put("prestado_a", prestado);
        valores.put("valoracion", valoracion);
        valores.put("notas", notas);

        db.update("Libro", valores, "_id" + "= ?", new String[]{Integer.toString(id)});
        db.close();

        Toast.makeText(context,"Update Realizado", Toast.LENGTH_LONG).show();
    }

    public List<Libro> cogerDatos(){
        List<Libro> listaLibros = new ArrayList<>();
        String consulta = "SELECT * " +
                "FROM Libro";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(consulta,null);
        if (cursor.moveToFirst()){
            while(cursor.moveToNext()){
                Libro dato = new Libro();
                dato.setId(cursor.getInt(0));
                dato.setCategoria(cursor.getString(1));
                dato.setTitulo(cursor.getString(2));
                dato.setAutor(cursor.getString(3));
                dato.setIdioma(cursor.getString(4));
                dato.setFechaInicio(cursor.getString(5));
                dato.setFechaFin(cursor.getString(6));
                dato.setPrestado(cursor.getString(7));
                dato.setValoracion(cursor.getFloat(8));
                dato.setFormato(cursor.getString(9));
                dato.setNotas(cursor.getString(10));
                listaLibros.add(dato);
            }
        }
        return listaLibros;
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Libro", "_id" +" = ?", new String[]{Integer.toString(id)});
        db.close();
    }
}
