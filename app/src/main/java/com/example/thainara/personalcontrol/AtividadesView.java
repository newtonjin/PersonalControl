package com.example.thainara.personalcontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thainara on 05/10/2015.
 */
public class AtividadesView extends FrameLayout {
    public static final int RESULT_EDIT_ATIVIDADE = 2;
    public static final String EXTRA_ID_ATIVIDADE = "id_atividade";
    private List<CharSequence> listaNomes;
    private List<Atividade> atividades;

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
        listaNomes = new ArrayList<CharSequence>();

        atividades = new Select().all().from(Atividade.class).queryList();//pega exercícios do banco
        for (Atividade at : atividades) {

            listaNomes.add(String.format("%s : %s : %s", at.exercicio.nome, at.data, at.getHoraAsString()));//preenche lista só de nomes
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

        atividadesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Excluir atividade")
                        .setMessage("Tem certeza que quer excluir esta atividade?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (listaNomes != null && atividades != null) {
                                    new Delete()
                                            .from(Atividade.class)
                                            .where(Condition.column(Atividade$Table.ID).is(atividades.get(position).id))
                                            .query();

                                    listaNomes.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
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

