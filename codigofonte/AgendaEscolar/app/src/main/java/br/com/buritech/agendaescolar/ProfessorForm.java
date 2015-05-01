package br.com.buritech.agendaescolar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.com.buritech.agendaescolar.bean.Professor;
import br.com.buritech.agendaescolar.helper.ProfessorHelper;


public class ProfessorForm extends ActionBarActivity {
    //Lista de atributos atualizada
    private Button botao;
    //Objeto Helper
    private ProfessorHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professorformlayout);
        Log.d("CICLO DE VIDA", "Executou o método onCreate()");
        //Configuração para exibir o ícone da aplicação
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
                | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle(R.string.tituloProfessorForm);

        botao = (Button)findViewById(R.id.sbSalvar);
        //Criação do Helper
        helper = new ProfessorHelper(this);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Solicitação de serviço do helper
                Professor professor = helper.getProfessor();
                //Impressõe bseada em métodos do professor
                Log.d("SALVAR", professor.getNome());
                Log.d("SALVAR", professor.getTelefone());
                Log.d("SALVAR", professor.getSite());
                Log.d("SALVAR", professor.getEmail());
                Log.d("SALVAR", professor.getEndereco());
            }
        });
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
