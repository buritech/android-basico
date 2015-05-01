package br.com.buritech.agendaescolar.helper;

import android.widget.EditText;
import android.widget.ImageView;

import br.com.buritech.agendaescolar.ProfessorForm;
import br.com.buritech.agendaescolar.R;
import br.com.buritech.agendaescolar.bean.Professor;

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
}
