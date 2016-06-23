package br.com.caelum.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class Formulario_Activity extends AppCompatActivity {

    private FormularioHelper helper;
    public static final String ALUNO_SELECIONADO = "alunoSelecionado";
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== TIRA_FOTO){
            if(resultCode== Activity.RESULT_OK){
            helper.carregaImagem(this.localArquivoFoto);
            }else{
                this.localArquivoFoto=null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_);

        this.helper = new FormularioHelper(this);

        Intent intent = this.getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra(ALUNO_SELECIONADO);

        if(aluno != null){
            helper.colocaNoFormulario(aluno);
        }

        FloatingActionButton foto = helper.getFotoButton();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() +
                        ".jpg";

                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri localFoto = Uri.fromFile(new File(localArquivoFoto ));
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT,localFoto);
                startActivityForResult(irParaCamera, TIRA_FOTO);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                //Toast.makeText(this, "Ok clicado", Toast.LENGTH_SHORT).show();
                if (helper.temNome()) {
                    Aluno aluno = helper.pegaAlunoDoFormulario();
                    //Toast.makeText(Formulario_Activity.this, "Objeto aluno criado: " + aluno.getNome(), Toast.LENGTH_SHORT).show();

                    DAO dao = new DAO(this);

                    if (aluno.getId() == null){
                        Toast.makeText(this, "Insere", Toast.LENGTH_SHORT).show();
                        dao.insere(aluno);
                    }else{
                        Toast.makeText(this, "Altera", Toast.LENGTH_SHORT).show();
                        dao.altera(aluno);
                    }

                    dao.close();
                    finish();
                    return false;
                } else {
                    helper.mostraErro();
                }
        default:
        return super.onOptionsItemSelected(item);
    }
}
}

