package br.com.buritech.agendaescolar.adapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.buritech.agendaescolar.R;
import br.com.buritech.agendaescolar.activity.ProfessorLista;
import br.com.buritech.agendaescolar.model.bean.Professor;
/**
 * Created by marciopalheta on 05/05/15.
 * Classe responsável pela carga de dados
 * na listagem de Professores
 */
public class ProfessorListaAdapter extends BaseAdapter{
    //Lista de professores
    private final List<Professor> listaDeProfessores;
    //Activity responsável pela tela
    private final ProfessorLista activity;
    //Injeção de dependências via construtor
    public ProfessorListaAdapter(ProfessorLista activity,
                                 List<Professor> listaDeProfessores) {
        this.listaDeProfessores = listaDeProfessores;
        this.activity = activity;
    }

    /**
     * Metodo que devolve a quantidade de itens da lista
     * @return tamanho da coleção de professores
     */
    @Override
    public int getCount() {
        return listaDeProfessores.size();
    }
    /**
     * Retorna o professor de um determinado índice da coleção
     * @param position Índice a ser retornado
     * @return Professor do índice informado
     */
    @Override
    public Object getItem(int position) {
        return listaDeProfessores.get(position);
    }
    /**
     * Retorn o ID de um determinado Professor
     * @param position Índice do professor na coleção
     * @return ID do professor
     */
    @Override
    public long getItemId(int position) {
        return listaDeProfessores.get(position).getId();
    }

    /**
     * Retorna uma View com os dados de um Professor da Lista de Professores
     * @param position Índice do professor a ser retornado
     * @param convertView View antiga a ser reusada, se possível
     * @param parent Componente ao qual a View pode estar anexada
     * @return Componente view a ser exibido na ListView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Infla o layout na view
        View view = activity.getLayoutInflater().inflate(R.layout.itemlistagem,
                null);
        Professor professor = listaDeProfessores.get(position);
        // Definicao de cor de fundo de linhas pares ou impares
        if (position % 2 == 0) {
            view.setBackgroundColor(activity.getResources().getColor(
                    R.color.linhaPar));
        } else {
            view.setBackgroundColor(activity.getResources().getColor(
                    R.color.linhaImpar));
        } //Continua...

        // Configuracao do nome
        TextView nome = (TextView) view.findViewById(R.id.itemLayoutNome);
        nome.setText(professor.getNome());

        // Configuracao da foto
        Bitmap bmp;
        if (professor.getFoto() != null) {
            bmp = BitmapFactory.decodeFile(professor.getFoto());
        } else {
            bmp = BitmapFactory.decodeResource(activity.getResources(),
                    R.drawable.ic_no_image);
        }
        bmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        //Código omitido
        ImageView foto = (ImageView) view.findViewById(R.id.itemLayoutFoto);
        foto.setImageBitmap(bmp);
        //Carregar o telefone
        TextView telefone = (TextView)view.findViewById(R.id.itemLayoutTelefone);
        if(telefone!=null){
            telefone.setText(professor.getTelefone());
        }
        //Carregar o e-mail
        TextView email = (TextView)view.findViewById(R.id.itemLayoutEmail);
        if(email!=null){
            email.setText(professor.getEmail());
        }
        return view;
    }
}
