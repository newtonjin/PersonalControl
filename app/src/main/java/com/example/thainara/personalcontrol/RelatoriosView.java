package com.example.thainara.personalcontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
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
public class RelatoriosView extends FrameLayout {

    public static final String EXTRA_TIPO_RELATORIO = "tipo_relatorio";
    public static final int TIPO_REL_ALT_DURACAO_SONO = 1;
    public static final int TIPO_REL_ALT_TIPO_ALIMENTACAO = 2;
    public static final int TIPO_REL_ALT_DURACAO_JORNADA_TRABALHO = 3;
    public static final int TIPO_REL_ALT_PESO = 4;

    public RelatoriosView(Context context) {
        super(context);
        init();
    }

    public RelatoriosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        inflate(getContext(), R.layout.view_relatorios, this);
        atualizaLista();
    }

    public void atualizaLista() {
        List<CharSequence> listaNomes = new ArrayList<CharSequence>();
        listaNomes.add("Duração de Sono");
        listaNomes.add("Tipo de Alimentação");
        listaNomes.add("Jornada de Trabalho");
        listaNomes.add("Peso");

        final ArrayAdapter adapter =
                new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, listaNomes);

        final ListView relatoriosListView = (ListView) findViewById(R.id.view_relatorios_relatorios_list);

        relatoriosListView.setAdapter(adapter);
        relatoriosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //método chamado quando clica num item da lista
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), RelatorioActivity.class);
                intent.putExtra(EXTRA_TIPO_RELATORIO, (position+1));

                ((Activity) getContext()).startActivity(intent);
            }
        });
    }
}
