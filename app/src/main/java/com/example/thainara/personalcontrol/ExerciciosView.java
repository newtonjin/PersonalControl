
package com.example.thainara.personalcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Thainara on 28/09/2015.
 */

public class ExerciciosView extends FrameLayout {
    //@Bind(R.id.view_exercicios_exercicios_list) ListView exerciciosListView;

    public static final int RESULT_EDIT_EXERCICIO = 1;
    public static final String EXTRA_ID_EXERCICIO = "id_exercicio";

    public ExerciciosView(Context context) {
        super(context);
        init();
    }

    public ExerciciosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        inflate(getContext(), R.layout.view_exercicios, this);
        atualizaLista();
    }

    public void atualizaLista() {
        final List<CharSequence> listaNomes = new ArrayList<CharSequence>();

        final List<Exercicio> exercicios = new Select().all().from(Exercicio.class).queryList();//pega exercícios do banco
        for (Exercicio ex : exercicios) {
            listaNomes.add(ex.nome);//preenche lista só de nomes
        }

        final ArrayAdapter adapter =
                new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, listaNomes);

        final ListView exerciciosListView = (ListView) findViewById(R.id.view_exercicios_exercicios_list);

        exerciciosListView.setAdapter(adapter);
        exerciciosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //método chamado quando clica num item da lista
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ExercicioActivity.class);
                intent.putExtra(EXTRA_ID_EXERCICIO, exercicios.get(position).id);

                ((Activity)getContext()).startActivityForResult(intent, RESULT_EDIT_EXERCICIO);
            }
        });

        findViewById(R.id.fab).setOnClickListener(new OnClickListener() {//botão "+" para adicionar exercício
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExercicioActivity.class);
                ((Activity)getContext()).startActivityForResult(intent, RESULT_EDIT_EXERCICIO);
            }
        });
    }
}