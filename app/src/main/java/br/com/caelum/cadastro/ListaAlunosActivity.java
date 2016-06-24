package br.com.caelum.cadastro;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.support.WebClient;
import task.EnviaAlunosTask;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private List<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Permissao.fazPermissao(this);

//        String[] alunos={"Anderson","Filipe","Guilherme"};

        carregaLista();

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
//                Toast.makeText(ListaAlunosActivity.this, "Posição Selecionada: " + posicao, Toast.LENGTH_SHORT).show();
                Intent edicao = new Intent(ListaAlunosActivity.this, Formulario_Activity.class);
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(posicao);
                edicao.putExtra(Formulario_Activity.ALUNO_SELECIONADO, aluno);
                startActivity(edicao);

            }
        });

        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long l) {

                Aluno aluno = (Aluno) adapter.getItemAtPosition(posicao);
                Toast.makeText(ListaAlunosActivity.this, "Clique Longo: " + aluno.getNome(), Toast.LENGTH_SHORT).show();


                return false;
            }
        });


        registerForContextMenu(listaAlunos);

        FloatingActionButton botaoAdd = (FloatingActionButton) findViewById(R.id.lista_alunos_add);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, Formulario_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        carregaLista();


    }

    private void carregaLista() {

        DAO dao = new DAO(this);

        alunos = dao.getLista();
        dao.close();

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);

//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alunos);
//        final ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        final ListaAlunosAdapter adapter = new ListaAlunosAdapter(this,alunos);

        listaAlunos.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        MenuItem menuSite = menu.add("Navegar no site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = alunoSelecionado.getSite();

        if(!site.startsWith("http://")){
            site="http://"+ site;
        }
        intentSite.setData(Uri.parse(site));
        menuSite.setIntent(intentSite);


        MenuItem endereco = menu.add("Achar no Mapa");
        Intent intentEndereco = new Intent(Intent.ACTION_VIEW);
        intentEndereco.setData(Uri.parse("geo:0, 0?z=14&q= " + alunoSelecionado.getEndereco()));
        endereco.setIntent(intentEndereco);

        MenuItem sms = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms: " + alunoSelecionado.getTelefone()));
        intentSMS.putExtra("sms_body: ","Meu primeiro envio de SMS");
        sms.setIntent(intentSMS);

        MenuItem ligar = menu.add("Ligar");
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:"+alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);



        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DAO dao = new DAO(ListaAlunosActivity.this);
                                dao.deleta(alunoSelecionado);
                                dao.close();

                                carregaLista();
                            }
                        })
                        .setNegativeButton("Nao", null).show();

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        String resposta = new String();

        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                DAO dao = new DAO(this);
                List<Aluno> alunos = dao.getLista();
                dao.close();

                String json = new AlunoConverter().toJSON(alunos);

//                WebClient client = new WebClient();
//                try {onOpt
//                    resposta = client.post(json);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                EnviaAlunosTask envia = new EnviaAlunosTask(json, this);
                envia.execute();

                return true;
            case R.id.menu_receber_provas:
                Intent provas = new Intent(this,ProvasActivity.class);
                startActivity(provas);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
