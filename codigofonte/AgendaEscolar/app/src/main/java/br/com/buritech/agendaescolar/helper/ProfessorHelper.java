package br.com.buritech.agendaescolar.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.buritech.agendaescolar.R;
import br.com.buritech.agendaescolar.activity.ProfessorForm;
import br.com.buritech.agendaescolar.model.bean.Professor;

/**
 * Created by marciopalheta on 01/05/15.
 */
public class ProfessorHelper {
    //Atributos que representam componentes de tela
    private EditText nome;
    private EditText telefone;
    private EditText site;
    private EditText email;
    private EditText endereco;
    private ImageView foto;
    //Atributo carregado com os dados da tela
    private Professor professor;

    public ProfessorHelper(ProfessorForm formulario) {
        //Associar atributos de controle a compoentes de tela
        nome = (EditText) formulario.findViewById(R.id.edNome);
        telefone = (EditText) formulario.findViewById(R.id.edFone);
        site = (EditText) formulario.findViewById(R.id.edSite);
        email = (EditText) formulario.findViewById(R.id.edEmail);
        endereco = (EditText) formulario.findViewById(R.id.edEndereco);
        foto = (ImageView) formulario.findViewById(R.id.foto);

        professor = new Professor();
    }

    /**
     * Chamado para recuperar os valores da tela
     *
     * @return Professor com os dados da tela
     */
    public Professor getProfessor() {
        professor.setNome(nome.getText().toString());
        professor.setTelefone(telefone.getText().toString());
        professor.setEndereco(endereco.getText().toString());
        professor.setSite(site.getText().toString());
        professor.setEmail(email.getText().toString());
        return professor;
    }

    /**
     * Chamado para atualização dos componentes de tela
     * @param professor O professor a ser exibido na tela
     */
    public void setProfessor(Professor professor) {
        nome.setText(professor.getNome());
        telefone.setText(professor.getTelefone());
        endereco.setText(professor.getEndereco());
        site.setText(professor.getSite());
        email.setText(professor.getEmail());
        this.professor = professor;
        //Chamada ao método que atuliza a imagem
        if(professor.getFoto()!=null){
            carregarFoto(professor.getFoto());
        }
    }

    public ImageView getFoto() {
        return foto;
    }

    /**
     *  Metodo que carrega uma foto, a partir de um arquivo
     * armazenado no device
     * @param localArquivoFoto Local de armazenamento da foto
     */
    public void carregarFoto(String localArquivoFoto) {
        Log.d("PROFESSOR_HELPER", "Local Arquivo: " + localArquivoFoto);
        // Carregar arquivo de imagem
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        // Gerar imagem reduzida
        Bitmap imagemFotoReduzida =
                Bitmap.createScaledBitmap(imagemFoto, 100,
                        100, true);
        //Atualiza atributo do professor
        professor.setFoto(localArquivoFoto);
        // Atualiza a imagem exibida na tela de formulário
        foto.setImageBitmap(imagemFotoReduzida);
    }
}
