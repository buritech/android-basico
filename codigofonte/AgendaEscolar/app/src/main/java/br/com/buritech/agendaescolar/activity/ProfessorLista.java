package br.com.buritech.agendaescolar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.buritech.agendaescolar.R;
import br.com.buritech.agendaescolar.model.bean.Professor;
import br.com.buritech.agendaescolar.model.dao.ProfessorDAO;

public class ProfessorLista extends ActionBarActivity {
    // Atributos de controle
    private ListView lvProfessores;
    //Coleção de professores;
    private List<Professor> listaDeProfessores;

    /**
     * Carrega a lista de professores
     */
    private void carregarLista() {
        // Componentes que converte Strings em Views
        ArrayAdapter<String> adapter;
        // Layout da listagem
        int adapterLayout = android.R.layout.simple_list_item_1;
        List<String> listaTemporaria = new ArrayList<>();

        //Carregar a lista a partir do banco de dados
        ProfessorDAO dao = new ProfessorDAO(this);
        listaDeProfessores = dao.listar();
        dao.close();
        //Atualizar a listagem temporária de nomes
        for (Professor professor : listaDeProfessores) {
            listaTemporaria.add(professor.getNome());
        }
        //Criação do adaptador
        adapter = new ArrayAdapter<>(this, adapterLayout,
                listaTemporaria);
        //Associação do adapter ao listView
        lvProfessores.setAdapter(adapter);
    }

    /**
     * Configura os clicks Longo e Curto da ListView
     */
    private void configurarClicksDaListagem() {
        //Configuração do evento de click CURTO
        lvProfessores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ProfessorLista.this, "[Click simples] " + listaDeProfessores.get(position), Toast.LENGTH_LONG).show();
            }
        });

        //Configuração do evnto de click LONGO
        lvProfessores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                Toast.makeText(ProfessorLista.this, "[Click longo] " + listaDeProfessores.get(position), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Vínculo com a tela de listagem de professores
        setContentView(R.layout.professorlistalayout);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
                | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle(R.string.tituloProfessorLista);
        //Bind dos componentes de tela com atributos de controle
        lvProfessores = (ListView) findViewById(R.id.lvListagem);
        //Chama a configuração de clicks da ListView
        configurarClicksDaListagem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.professorlistamenu, menu);
        return true;
    }

    /**
     * Processa o item do menu selecionado pelo usuário
     *
     * @param item selecionado do menu
     * @return true se o item foi consumido com sucesso e false do contrário
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Variável para transição de telas
        Intent intent;
        //Recupera o id do item do menu selecionado
        int id = item.getItemId();
        //Click no botão para cadastro de professores
        if (id == R.id.menuItemNovo) {
            // Cria a intenção de transição da origem(this)
            // para o destino (ProfessorForm)
            intent = new Intent(this, ProfessorForm.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
