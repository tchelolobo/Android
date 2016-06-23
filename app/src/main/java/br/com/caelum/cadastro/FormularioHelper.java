package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;


/**
 * Created by android6406 on 21/06/16.
 */
public class FormularioHelper {

    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoSite;
    private EditText campoTelefone;
    private RatingBar campoNota;

    private Aluno aluno;

    private ImageView foto;
    private FloatingActionButton fotoButton;

    public FormularioHelper(Formulario_Activity activity) {

        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);

        aluno = new Aluno();

        foto = (ImageView) activity.findViewById(R.id.formulario_foto);
        fotoButton = (FloatingActionButton) activity.findViewById(R.id.formulario_foto_botao);

    }

    public FloatingActionButton getFotoButton() {
        return fotoButton;
    }

    public Aluno pegaAlunoDoFormulario() {

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setNota((double) campoNota.getRating());
        aluno.setCaminhoFoto((String)foto.getTag());
        return aluno;
    }

    public boolean temNome() {

        return !campoNome.getText().toString().isEmpty();
    }

    public void mostraErro() {
        campoNome.setError("Insira o nome!");
    }

    public void colocaNoFormulario(Aluno aluno) {

        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEndereco.setText(aluno.getEndereco());
        campoSite.setText(aluno.getSite());
        campoNota.setRating((aluno.getNota().floatValue()));

        if(aluno.getCaminhoFoto() != null) {
            carregaImagem(aluno.getCaminhoFoto());
        }
        this.aluno = aluno;

    }

    public void carregaImagem(String localArquivoFoto) {
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto,imagemFoto.getWidth(),300,true);

        foto.setImageBitmap(imagemFotoReduzida);
        foto.setTag(localArquivoFoto);
        foto.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
