package br.com.caelum.cadastro;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by android6406 on 23/06/16.
 */
public class ListaAlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter(Activity activity,List<Aluno> alunos) {
        this.alunos = alunos;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int posicao) {
        return alunos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {

        String camminhoFoto = new String();

        View view = activity.getLayoutInflater().inflate(R.layout.item,parent,false);
        Aluno aluno = alunos.get(posicao);

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto) ;
        camminhoFoto=aluno.getCaminhoFoto();

        if(camminhoFoto != null){
            Picasso.with(activity).load("file:///"+aluno.getCaminhoFoto()).into(foto);
        }

        if(posicao%2==0){
            view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par));
        }else{
            view.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar));
        }

        return view;
    }
}
