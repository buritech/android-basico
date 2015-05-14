package br.com.buritech.agendaescolar.converter;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.buritech.agendaescolar.model.bean.Professor;
public class ProfessorConverter {
    public String toJSON(List<Professor> listaProfessores) {
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object().key("listaatualizada").array();
            for (Professor professor : listaProfessores) {
                jsonStringer.object()
                        .key("id").value(professor.getId())
                        .key("nome").value(professor.getNome())
                        .key("telefone").value(professor.getTelefone())
                        .key("endereco").value(professor.getEndereco())
                        .key("site").value(professor.getSite())
                        .key("email").value(professor.getEmail())
                        .endObject();
            }
            jsonStringer.endArray().endObject();
            return jsonStringer.toString();
        } catch (JSONException e) {
            Log.i("PROFESSOR-CONVERTER", e.getMessage());
            return null;
        }
    }
}
