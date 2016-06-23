package br.com.caelum.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android6406 on 21/06/16.
 */
public class DAO extends SQLiteOpenHelper{

    private static final int VERSAO = 2;
    private static final String TABELA ="Aluno";
    private static final String DATABASE="CadastroCaelum";

    public DAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    public void onCreate(SQLiteDatabase database){

        String ddl = "CREATE TABLE " + TABELA
                + "(id INTEGER PRIMARY KEY,"
                + "nome TEXT NOT NULL,"
                + "telefone TEXT,"
                + "endereco TEXT,"
                + "site TEXT,"
                + " nota REAL); ";
        database.execSQL(ddl);
    }

    public void onUpgrade(SQLiteDatabase database,int versaoAntiga,int versaoNova){

         String sql = new String();

        switch (versaoAntiga){
            case 1:
                sql ="ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;";
        }

//        String sql ="DROP TABLE IF EXISTS " + TABELA;
        if (!sql.isEmpty())
        {
            database.execSQL(sql);
        }
//        onCreate(database);
    }

    public void insere(Aluno aluno){

        ContentValues values = new ContentValues();

        values.put("nome",aluno.getNome());
        values.put("telefone",aluno.getTelefone());
        values.put("endereco",aluno.getEndereco());
        values.put("site",aluno.getSite());
        values.put("nota",aluno.getNota());
        values.put("caminhoFoto",aluno.getCaminhoFoto());

        getWritableDatabase().insert(TABELA,null,values);
    }

    public List<Aluno> getLista(){

        List<Aluno> alunos = new ArrayList<Aluno>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABELA + ";",null);

        while(c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);

        }


        c.close();
        return alunos;

    }

    public void deleta(Aluno alunoSelecionado) {
        getWritableDatabase().delete(TABELA,"id=?",new String[]{alunoSelecionado.getId().toString()});
    }

    public void altera(Aluno aluno){

        ContentValues values = new ContentValues();
        values.put("nome",aluno.getNome());
        values.put("nota",aluno.getNota());
        values.put("site",aluno.getSite());
        values.put("endereco",aluno.getEndereco());
        values.put("telefone",aluno.getTelefone());
        values.put("caminhoFoto",aluno.getCaminhoFoto());

        getWritableDatabase().update(TABELA,values,"id=?",new String[]{aluno.getId().toString()});

    }

    public boolean isAluno(String telefone){
        String[] parametros = {telefone};

        Cursor rawQuery = getReadableDatabase().rawQuery("select telefone from " + TABELA + " where telefone = ?",
                parametros);

        int total = rawQuery.getCount();
        rawQuery.close();

        return total>0;
    }
}
