package br.com.buritech.agendaescolar.task;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.buritech.agendaescolar.converter.ProfessorConverter;
import br.com.buritech.agendaescolar.integracao.WebClient;
import br.com.buritech.agendaescolar.model.bean.Professor;
import br.com.buritech.agendaescolar.model.dao.ProfessorDAO;
public class ProfessorTask extends AsyncTask<Object, Object, String>{
    //Servidor para teste JSON: http://www.jsontest.com/
    //private final String url = "http://ip.jsontest.com/";
    private final String url =
            "http://192.168.1.129:8080/AlunoWeb/receber-json";

    // Contexto (tela) para exibicao de mensagens
    private Context context;

    // Barra de progresso
    private ProgressDialog progress;

    //Construtor que recebe o contexto da App
    public ProfessorTask(Context context) {
        this.context = context;
    }

    /**
     * Método de callback invocado ANTES da execução da tarefa
     */
    protected void onPreExecute() {
        //Executando a barra de progresso
        progress = ProgressDialog.show(context, "Aguarde...",
                "Enviando dados para o servidor web", true, true);
    }
    /**
     * Método de callback invocado após a conclusão da tarefa
     * @param result
     */
    protected void onPostExecute(String result) {
        //Encerra a exibicao da barra de progresso
        progress.dismiss();
        //Exibindo a resposta do servidor
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    /**
     * Método de execução da tarefa - Envio de dados de professores
     * @param params
     * @return String com a resposta do servidor
     */
    protected String doInBackground(Object... params) {
        //Lista de alunos
        ProfessorDAO dao = new ProfessorDAO(context);
        List<Professor> lista = dao.listar();
        dao.close();
        //Conversão da lista para JSON
        String json = new ProfessorConverter().toJSON(lista);
        //Envio de dados para o servidor remoto
        String jsonResposta = new WebClient(url).post(json);
        //Devolvendo a resposta do servidor
        return jsonResposta;
    }
}
