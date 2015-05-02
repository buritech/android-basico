package br.com.buritech.agendaescolar.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.buritech.agendaescolar.model.bean.Professor;

/**
 * Created by marciopalheta on 01/05/15.
 */
public class ProfessorDAO extends SQLiteOpenHelper {

    //Constantes para controle de versão e padronização
    private static final int VERSAO = 1;
    private static final String TABELA = "Professor";
    private static final String BANCO_DE_DADOS = "AgendaEscolar";

    //Constante para tag do LOGCAT
    private static final String TAG_LOGCAT = "PROFESSOR-DAO";

    public ProfessorDAO(Context context) {
        //Chamada ao construtor que sabe acessar o BD
        super(context, BANCO_DE_DADOS, null, VERSAO);
        Log.i(TAG_LOGCAT, "método construtor");
    }

    /**
     * Chamado quando o BD é criado pela primeira vez
     *
     * @param db Bando de dados
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Intrução DDL a ser executada
        String ddl = "CREATE TABLE " + TABELA + " ( " +
                " id INTEGER PRIMARY KEY, " +
                " nome TEXT, telefone TEXT, endereco TEXT, " +
                " site TEXT, email TEXT, foto TEXT)";
        //Execução da instrução no SQLite
        db.execSQL(ddl);
        Log.i(TAG_LOGCAT, "Criação da tabela: " + TABELA);
    }

    /**
     * Chamado quando a versão do BD é incrementada
     *
     * @param db         Banco de dados
     * @param oldVersion versão antiga do BD
     * @param newVersion nova versão do BD
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Intrução DDL para destruir a tabela
        String ddl = "DROP TABLE IF EXISTS " + TABELA;
        //Execução da instrução no SQLite
        db.execSQL(ddl);
        //Chamada do metodo de reconstrução da tabela
        onCreate(db);
        Log.i(TAG_LOGCAT, "Atualização da versão da tabela: " + TABELA);
    }

    /**
     * Chamado para cadastro de professores
     *
     * @param professor O professor a ser cadastrado
     */
    public void cadastrar(Professor professor) {

        // Objeto para armazenar os valores dos campos
        ContentValues values = new ContentValues();

        // Definicao de valores dos campos da tabela
        values.put("nome", professor.getNome());
        values.put("telefone", professor.getTelefone());
        values.put("endereco", professor.getEndereco());
        values.put("site", professor.getSite());
        values.put("email", professor.getEmail());
        values.put("foto", professor.getFoto());

        // Inserir dados do Professor no BD
        getWritableDatabase().insert(TABELA, null, values);
        Log.i(TAG_LOGCAT, "Professor Cadastrado: " +
                professor.getNome());
    }

    /**
     * Método de listagem de professores
     * @return Lista de professores
     */
    public List<Professor> listar() {
        // Definicao da colecao de professores
        List<Professor> lista = new ArrayList<>();

        // Definicao da instrucao SQL
        String sql = "Select * from " + TABELA + " order by nome";

        // Objeto que recebe os registros do banco de dados
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        //continua...

        try {
            //Percorre os resgistros do banco de dados
            while (cursor.moveToNext()) {
                // Criacao de nova referencia para Professor
                Professor professor = new Professor();
                // Carregar os atributos de Professor com campos da tabela
                professor.setId(cursor.getLong(0));
                professor.setNome(cursor.getString(1));
                professor.setTelefone(cursor.getString(2));
                professor.setEndereco(cursor.getString(3));
                professor.setSite(cursor.getString(4));
                professor.setEmail(cursor.getString(5));
                professor.setFoto(cursor.getString(6));
                // Adicionar novo Professor à lista
                lista.add(professor);
            }
        } catch (SQLException e) {
            Log.e(TAG_LOGCAT, e.getMessage());
        } finally {
            //Fecha a lista de registros do banco de dados
            cursor.close();
        }
        return lista;
    }

    /**
     * Chamado para exclusão de um professor
     * @param professor O professor a ser excluído
     */
    public void excluir(Professor professor) {
        // Definicao de array de parametros
        String[] args = { professor.getId().toString() };

        // Exclusão do Professor
        getWritableDatabase().delete(TABELA, "id=?", args);

        Log.i(TAG_LOGCAT, "Professor excluído: " + professor.getNome());
    }

    /**
     * Chamado para atualização de dados do professor
     * @param professor O professor a ser atualizado
     */
    public void alterar(Professor professor) {
        ContentValues values = new ContentValues();
        values.put("nome", professor.getNome());
        values.put("telefone", professor.getTelefone());
        values.put("endereco", professor.getEndereco());
        values.put("site", professor.getSite());
        values.put("email", professor.getEmail());
        values.put("foto", professor.getFoto());

        // Colecao de valores de parametros do SQL
        String[] args = { professor.getId().toString() };

        // Altera dados do Professor no BD
        getWritableDatabase().update(TABELA, values, "id=?", args);
        Log.i(TAG_LOGCAT, "Professor alterado: " + professor.getNome());
    }
}

