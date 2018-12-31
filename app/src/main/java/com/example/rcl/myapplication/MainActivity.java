package com.example.rcl.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rcl.myapplication.db.ControladorDB;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ControladorDB controladorDB;
    ListView listViewTareas;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controladorDB = new ControladorDB(this);

        listViewTareas = (ListView) findViewById(R.id.listaTareas);
        actualizarUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.item) {
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Nueva tarea")
                    .setMessage("¿Qué quieres hacer a continuación?")
                    .setView(taskEditText)
                    .setPositiveButton("Añadir", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tarea = taskEditText.getText().toString();
                                    controladorDB.addTarea(tarea);
                                    actualizarUI();
                                }
                            })
                    .setNegativeButton("Cancelar", null)
                    .create();
            dialog.show();

        }
        return true;

    }

    private void actualizarUI() {
        if (controladorDB.numeroTareas() == 0) {
            listViewTareas.setAdapter(null);
        } else {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_tarea,
                    R.id.task_title,
                    controladorDB.obtenerTareas());
            listViewTareas.setAdapter(mAdapter);
        }
    }

    public void borrarTarea(View view) {
        View parent = (View) view.getParent();
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_title);
        String tarea = tareaTextView.getText().toString();
        controladorDB.borrarTarea(tarea);
        actualizarUI();
    }
}
