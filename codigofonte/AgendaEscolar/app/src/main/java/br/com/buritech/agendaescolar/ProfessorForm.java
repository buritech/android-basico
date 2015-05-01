package br.com.buritech.agendaescolar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class ProfessorForm extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professorformlayout);
        Log.d("CICLO DE VIDA", "Executou o método onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CICLO DE VIDA", "Executou o método onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CICLO DE VIDA", "Executou o método onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CICLO DE VIDA", "Executou o método onPause()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.professorformmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
