package br.com.caelum.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.caelum.cadastro.R;

/**
 * Created by android6android.support.design.R.layout406 on 24/06/16.
 */
public class ListaProvasFragment extends Fragment {

    private ListView listViewProvas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layoutProvas = inflater.inflate(R.layout.fragment_lista_provas, container, false);
        this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_listview);

        Prova prova1 = new Prova("20/06/2015","Matematica");
        prova1.setTopicos(Arrays.asList("Algebra linear","Calculo","Estatistica"));

        Prova prova2 = new Prova("25/07/2015","Portugues");
        prova1.setTopicos(Arrays.asList("Complemento Nominal","Oracoes subordinadas","Analise sintatica"));

        List<Prova> provas = Arrays.asList(prova1,prova2);

        this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(),android.R.layout.simple_list_item_1,provas));

        this.listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {

                Prova selecionada = (Prova) adapter.getItemAtPosition(posicao);

                Toast.makeText(getActivity(), "Prova selecionada: " + selecionada, Toast.LENGTH_LONG).show();
            }
        });

        return layoutProvas;
    }
}
