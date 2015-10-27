package com.example.thainara.personalcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thainara on 05/10/2015.
 */
public class AtividadesView extends FrameLayout {
    public static final int RESULT_EDIT_ATIVIDADE = 2;
    public static final String EXTRA_ID_ATIVIDADE = "id_atividade";

    public AtividadesView(Context context) {
        super(context);
        init();
    }

    public AtividadesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        inflate(getContext(), R.layout.view_atividades, this);
        atualizaLista();
    }

    public void atualizaLista() {
        final List<CharSequence> listaNomes = new ArrayList<CharSequence>();

        final List<Atividade> atividades = new Select().all().from(Atividade.class).queryList();//pega exercícios do banco
        for (Atividade at : atividades) {

            listaNomes.add(String.format("%s : %s", at.data, at.getHoraAsString()));//preenche lista só de nomes
        }

        final ArrayAdapter adapter =
                new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, listaNomes);

        final ListView atividadesListView = (ListView) findViewById(R.id.view_atividades_atividades_list);

        atividadesListView.setAdapter(adapter);
        atividadesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //método chamado quando clica num item da lista
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), AtividadeActivity.class);
                intent.putExtra(EXTRA_ID_ATIVIDADE, atividades.get(position).id);

                ((Activity) getContext()).startActivityForResult(intent, RESULT_EDIT_ATIVIDADE);
            }
        });

        findViewById(R.id.view_atividades_fab).setOnClickListener(new OnClickListener() {//botão "+" para adicionar exercício
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AtividadeActivity.class);
                ((Activity) getContext()).startActivityForResult(intent, RESULT_EDIT_ATIVIDADE);
            }
        });
    }
}

