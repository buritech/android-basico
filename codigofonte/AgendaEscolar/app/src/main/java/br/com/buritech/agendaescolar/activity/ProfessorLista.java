package br.com.buritech.agendaescolar.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.buritech.agendaescolar.R;
import br.com.buritech.agendaescolar.adapter.ProfessorListaAdapter;
import br.com.buritech.agendaescolar.model.bean.Professor;
import br.com.buritech.agendaescolar.model.dao.ProfessorDAO;
import br.com.buritech.agendaescolar.task.ProfessorTask;

public class ProfessorLista extends ActionBarActivity {
    // Atributos de controle
    private ListView lvProfessores;
    //Coleção de professores;
    private List<Professor> listaDeProfessores;
    //Professor selecionado no click da ListView
    private Professor professorSelecionado;

    /**
     * Carrega a lista de professores
     */
    private void carregarLista() {
        // Componentes que converte Professores em Views
        ProfessorListaAdapter adapter;
        //Carregar a lista a partir do banco de dados
        ProfessorDAO dao = new ProfessorDAO(this);
        listaDeProfessores = dao.listar();
        dao.close();
        //Criação do adaptador customizado
        adapter = new ProfessorListaAdapter(this, listaDeProfessores);
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
                //Pega a referência para o professor selecionado
                professorSelecionado = listaDeProfessores.get(position);
                Intent form = new Intent(ProfessorLista.this,
                        ProfessorForm.class);
                //Passagem de parâmetros
                form.putExtra("PROFESSOR_SELECIONADO", professorSelecionado);
                //Iniciar a nova Activity
                startActivity(form);
            }
        });

        //Configuração do evnto de click LONGO
        lvProfessores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                professorSelecionado = listaDeProfessores.get(position);
                return false;
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
        //Informa que a ListView possui Menu de Contexto
        registerForContextMenu(lvProfessores);
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
        switch (item.getItemId()) {
            case R.id.menuItemNovo:
                intent = new Intent(this, ProfessorForm.class);
                startActivity(intent);
                return false;
            case R.id.menuItemEnviar:
                new ProfessorTask(this).execute();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Chamado para a contrução de menu de contexto da View
     *
     * @param menu     O menu de contexto que será carregado
     * @param view     Objeto para o qual o menu de contexto está sendo contruído
     * @param menuInfo Informação extra a cerca do item para o qual o menu é construído
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        //Infla professormenucontexto.xml no menu de contexto
        getMenuInflater().inflate(R.menu.professormenucontexto, menu);
    }

    /**
     * Chamado quando um item do menu de contexto for selecionado
     *
     * @param item Objeto selecionado
     * @return false para peritir o processamento do item clicado
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.itemMenuContextoExcluir:
                excluir();
                break;
            case R.id.itemMenuContextoLigar:
                //Intent implicita para chamada telefônica
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + professorSelecionado.getTelefone()));
                startActivity(intent);
                break;
            case R.id.itemMenuContextoEnviarSMS:
                intent = new Intent(Intent.ACTION_VIEW);
                //Marcação para envio de SMS
                intent.setData(Uri.parse("sms:" + professorSelecionado.getTelefone()));
                intent.putExtra("sms_body", "Mensagem de boas vindas :-)");
                startActivity(intent);
                break;
            //Continua...
            case R.id.itemMenuContextoVerNoMapa:
                intent = new Intent(Intent.ACTION_VIEW);
                //Marcação para exibição de mapa
                intent.setData(Uri.parse("geo:0,0?z=14&q="
                        + professorSelecionado.getEndereco()));
                startActivity(intent);
                break;
            case R.id.itemMenuContextoNavagarSite:
                intent = new Intent(Intent.ACTION_VIEW);
                //Marcação para navegação na web
                intent.setData(Uri.parse("http:" + professorSelecionado.getSite()));
                startActivity(intent);
                break;
            // continua...
            case R.id.itemMenuContextoEnviarEmail:
                intent = new Intent(Intent.ACTION_SEND);
                //Marcação para envio de email
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{professorSelecionado.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Falando sobre o curso");
                intent.putExtra(Intent.EXTRA_TEXT, "O curso foi muito legal");
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Exclui o professor do banco de dados e
     * atualiza a listagem
     */
    private void excluir() {
        //Criação do componente de confirmação de exclusão
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Configuração da mensagem
        builder.setMessage("Confirma a exclusão de: " + professorSelecionado.getNome() + "?");
        //Configuração do botão de confirmação da exclusão
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProfessorDAO dao = new ProfessorDAO(ProfessorLista.this);
                //Execução do método de exclusão
                dao.excluir(professorSelecionado);
                dao.close();
                //Atualização da listagem
                carregarLista();
                professorSelecionado = null;
            }
        });
        //Configuração da opção de cancelamento da exclusão
        builder.setNegativeButton("Não", null);
        //Criação da caixa de diálogo
        AlertDialog dialog = builder.create();
        dialog.setTitle("Confirmação de operação");
        //Exibição da caixa de diálogo
        dialog.show();
    }
}
