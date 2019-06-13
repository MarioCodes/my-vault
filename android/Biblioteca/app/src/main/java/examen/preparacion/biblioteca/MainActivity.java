package examen.preparacion.biblioteca;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LibrosSQLiteHelper librosSQLiteHelper;
    private ListView list_view_principal;
    private final String NOMBRE = "Biblioteca";

    private List<Libro> lista_libros;
    private static LibroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view_principal = (ListView) findViewById(R.id.list_view_principal);
        librosSQLiteHelper = new LibrosSQLiteHelper(getApplicationContext(), NOMBRE, null, 1); //Inicio de la BDD por primera vez.

        lista_libros = librosSQLiteHelper.cogerDatos();
        adapter = new LibroAdapter(this, lista_libros);

        list_view_principal.setAdapter(adapter);

        Collections.sort(lista_libros, new Comparator<Libro>() {
            @Override
            public int compare(Libro uno, Libro dos) {
                return uno.getTitulo().compareToIgnoreCase(dos.getTitulo());
            }
        });

        list_view_principal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Libro libro = (Libro) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), AltaLibro.class);
                intent.putExtra("_id", libro.getId());
                intent.putExtra("categoria", libro.getCategoria());
                intent.putExtra("titulo", libro.getTitulo());
                intent.putExtra("autor", libro.getAutor());
                intent.putExtra("idioma", libro.getIdioma());
                intent.putExtra("fechainicio", libro.getFechaInicio());
                intent.putExtra("fechafin", libro.getFechaFin());
                intent.putExtra("prestado", libro.getPrestado());
                intent.putExtra("valoracion", libro.getValoracion());
                intent.putExtra("formato", libro.getFormato());
                intent.putExtra("notas", libro.getNotas());

                startActivity(intent);
            }
        });

        list_view_principal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final Libro libro = (Libro) adapterView.getItemAtPosition(position);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar")
                        .setMessage("¿Eliminar?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                librosSQLiteHelper.delete(libro.getId());
                                adapter.remove(libro);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Delete Hecho", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;
            }
        });

        //int count = 0;
        //if(count++ == 0) librosSQLiteHelper.insert("Literatura", "El Quijote", "Cervantes", "Español", "Ebook", "12/01/1998", "12/12/2002", "Antonio", 3, "Un Clasico");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void ordenar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ordenar Por...");
        String[] types = {"Titulo", "Autor","Idioma","Categoria","Formato","Valoracion"};
        builder.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int elemento) {
                switch(elemento){
                    case 0:
                        Collections.sort(lista_libros, new Comparator<Libro>() {
                            @Override
                            public int compare(Libro uno, Libro dos) {
                                return uno.getTitulo().compareToIgnoreCase(dos.getTitulo());
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        Collections.sort(lista_libros, new Comparator<Libro>() {
                            @Override
                            public int compare(Libro uno, Libro dos) {
                                return uno.getAutor().compareToIgnoreCase(dos.getAutor());
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        Collections.sort(lista_libros, new Comparator<Libro>() {
                            @Override
                            public int compare(Libro uno, Libro dos) {
                                return uno.getIdioma().compareToIgnoreCase(dos.getIdioma());

                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;

                    case 3:
                        Collections.sort(lista_libros, new Comparator<Libro>() {
                            @Override
                            public int compare(Libro uno, Libro dos) {
                                return uno.getCategoria().compareToIgnoreCase(dos.getCategoria());

                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case 4:
                        Collections.sort(lista_libros, new Comparator<Libro>() {
                            @Override
                            public int compare(Libro uno, Libro dos) {
                                return uno.getFormato().compareToIgnoreCase(dos.getFormato());
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case 5:
                        Collections.sort(lista_libros, new Comparator<Libro>() {
                            @Override
                            public int compare(Libro uno, Libro dos) {
                                return Float.compare(dos.getValoracion(),uno.getValoracion());
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                }
                dialog.dismiss();
            }
        }).show();
    }

     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();

         switch(id) {
             case(R.id.altalibro):
                 Intent intent = new Intent(MainActivity.this, AltaLibro.class);
                 startActivity(intent);
                 break;
             case(R.id.ordenarpor):
                 ordenar();
                 break;
             case(R.id.exportar):
                 exportar();
                 break;
             case(R.id.importar):
                 importar();
                 break;
             case(R.id.acercade):
                 new AcercaDe().show(getFragmentManager(), "Acerca De...");
         }

         return true;
     }

    @Override
    public void onResume() {
        super.onResume();
        librosSQLiteHelper = new LibrosSQLiteHelper(getApplicationContext(), NOMBRE, null, 1); //Inicio de la BDD por primera vez.

        lista_libros = librosSQLiteHelper.cogerDatos();
        adapter = new LibroAdapter(this, lista_libros);

        list_view_principal.setAdapter(adapter);

        Collections.sort(lista_libros, new Comparator<Libro>() {
            @Override
            public int compare(Libro uno, Libro dos) {
                return uno.getTitulo().compareToIgnoreCase(dos.getTitulo());
            }
        });
    }

    private void importar() {
        try {
            File almacenamiento = Environment.getExternalStorageDirectory();
            File datos = Environment.getDataDirectory();
            String currentDBPath = "//data//"+getPackageName()+"//databases//"+NOMBRE;
            String backupDBPath = "backup.db";
            File actual = new File(datos, currentDBPath);
            File copia = new File(almacenamiento, backupDBPath);
            if (actual.exists()) {
                FileChannel fis = new FileInputStream(copia).getChannel();
                FileChannel fos = new FileOutputStream(actual).getChannel();
                fos.transferFrom(fis, 0, fis.size());
                fis.close();
                fos.close();
                Toast.makeText(getApplicationContext(), "Importado", Toast.LENGTH_SHORT).show();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        onResume();
    }

    private void exportar() {
        try {
            File almacenamiento = Environment.getExternalStorageDirectory();
            File datos = Environment.getDataDirectory();
            String currentDBPath = "//data//"+getPackageName()+"//databases//"+NOMBRE;
            String backupDBPath = "backup.db";
            File actual = new File(datos, currentDBPath);
            File copia = new File(almacenamiento, backupDBPath);
            if (actual.exists()) {
                FileChannel fis = new FileInputStream(actual).getChannel();
                FileChannel fos = new FileOutputStream(copia).getChannel();
                fos.transferFrom(fis, 0, fis.size());
                fis.close();
                fos.close();
                Toast.makeText(getApplicationContext(),"Exportado",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
