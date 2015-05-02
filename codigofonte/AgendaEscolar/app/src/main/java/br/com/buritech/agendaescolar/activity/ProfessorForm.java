package br.com.buritech.agendaescolar.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.buritech.agendaescolar.R;
import br.com.buritech.agendaescolar.helper.ProfessorHelper;
import br.com.buritech.agendaescolar.model.bean.Professor;
import br.com.buritech.agendaescolar.model.dao.ProfessorDAO;


public class ProfessorForm extends ActionBarActivity {
    //Lista de atributos atualizada
    private Button botao;
    //Objeto Helper
    private ProfessorHelper helper;

    //Path para o arquivo de foto do professor
    private String localArquivoFoto;
    //Constante usada como requestCode
    private static final int FAZER_FOTO_PROFESSOR = 12345;

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

        botao = (Button) findViewById(R.id.sbSalvar);
        //Criação do Helper
        helper = new ProfessorHelper(this);
        //Recuperando o professor passado como parâmetro
        Professor professor = (Professor)getIntent().getSerializableExtra(
                "PROFESSOR_SELECIONADO");
        //Verifica se é necessário atualizar a tela com dados do professor
        if(professor!=null){
            helper.setProfessor(professor);
        }
        //Configuração de click da imagem
        helper.getFoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localArquivoFoto = Environment.getExternalStorageDirectory()
                        + "/" + System.currentTimeMillis() + ".jpg";
                File arquivo = new File(localArquivoFoto);
                //URI que informa onde o arquivo resultado deve ser salvo
                Uri localFoto = Uri.fromFile(arquivo);
                Intent irParaCamera = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                startActivityForResult(irParaCamera, FAZER_FOTO_PROFESSOR);
            }
        });
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Solicitação de serviço do helper
                Professor professor = helper.getProfessor();
                // Criação do Objeto DAO e início da conexão com o BD
                ProfessorDAO dao = new ProfessorDAO(ProfessorForm.this);
                if(professor.getId()==null){
                    // Chamada do método de cadastro de professor
                    dao.cadastrar(professor);
                }else{
                    // Atualizar dados do professor
                    dao.alterar(professor);
                }
                // Encerramento da conexão com o BD
                dao.close();
                //Feedback para o usuário com a mensagem de sucesso
                Toast.makeText(ProfessorForm.this, "Professor(a) salvo(a): " +
                        professor.getNome(), Toast.LENGTH_LONG).show();
                //Fecha a tela atual
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FAZER_FOTO_PROFESSOR) {
            if (resultCode == Activity.RESULT_OK) {
                helper.carregarFoto(localArquivoFoto);
            }else{
                localArquivoFoto = null;
            }
        }
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


    /**
     * Chamado para armazenar o estado da Activity,
     * antes que entre em background
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Inclusão de chave-valor no mapa
        outState.putSerializable("localArquivoFoto", localArquivoFoto);
    }

    /**
     * Chamado para recuperar o estado anterior da Activity
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Verifica se o mapa está nulo
        if (savedInstanceState != null) {
            localArquivoFoto = (String) savedInstanceState
                    .getSerializable("localArquivoFoto");
        }
        //Carrega a foto, se necessário
        if (localArquivoFoto != null) {
            helper.carregarFoto(localArquivoFoto);
        }
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
