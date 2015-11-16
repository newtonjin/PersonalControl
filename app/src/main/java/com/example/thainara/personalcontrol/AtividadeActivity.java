package com.example.thainara.personalcontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AtividadeActivity extends AppCompatActivity {
    @Bind(R.id.activity_atividade_data_edit) EditText dataEdit;
    @Bind(R.id.activity_atividade_hora_edit) EditText horaEdit;
    @Bind(R.id.activity_atividade_add_ativ_view) View addAtividadeView;

    private Atividade atividade_atual;
    private ExercicioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        final List<Exercicio> exercicios = new Select().all().from(Exercicio.class).queryList();//pega exercícios do banco

        adapter = new ExercicioAdapter(this, exercicios);

        final ListView exerciciosListView = (ListView) findViewById(R.id.activity_atividade_exercicios_list);

        exerciciosListView.setAdapter(adapter);
        exerciciosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //método chamado quando clica num item da lista
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercicio exercicio = (Exercicio) adapter.getItem(position);
                if (exercicio.selected) {
                    exercicio.selected = false;
                } else {
                    adapter.unselectAll();
                    exercicio.selected = true;
                }
                adapter.notifyDataSetChanged();
            }
        });


        atividade_atual = null;
        long idAtividade = getIntent().getLongExtra(AtividadesView.EXTRA_ID_ATIVIDADE, 0);
        if (idAtividade <= 0)
            return;

        Atividade atividade = new Select().from(Atividade.class).where(Condition.column(Atividade$Table.ID).eq(idAtividade)).querySingle();
        atividade_atual = atividade;
        dataEdit.setText(atividade.data);
        horaEdit.setText(atividade.getHoraAsString());

        if (atividade.exercicio != null) {
            for (Exercicio exercicio : exercicios) {
                if (exercicio.id == atividade.exercicio.id) {
                    exercicio.selected = true;
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }


    }

    @OnClick(R.id.activity_atividade_salvar_button)
    public void onClickSalvar() {
        if (!isFormValid())
            return;

        Atividade novaAtividade = null;
        if (atividade_atual == null)
            novaAtividade = new Atividade();
        else
            novaAtividade = atividade_atual;

        String data = dataEdit.getText().toString();
        novaAtividade.data = data;
        novaAtividade.hora = Utils.converteHoraStringParaMinutoInt(horaEdit.getText().toString());
        novaAtividade.exercicio = adapter.getSelected();

        novaAtividade.save();

        setResult(RESULT_OK);
        finish();
    }

    private boolean isFormValid() {
        if (dataEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(addAtividadeView, "Informe a data da atividade", Snackbar.LENGTH_SHORT)
                    .show();
            dataEdit.requestFocus();
            return false;
        }

        if (horaEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(addAtividadeView, "Informe a hora da atividade", Snackbar.LENGTH_SHORT)
                    .show();
            horaEdit.requestFocus();
            return false;
        }

        if (adapter != null) {
            Exercicio ex = adapter.getSelected();
            if (ex == null) {
                Snackbar
                        .make(addAtividadeView, "Selecione um exercício", Snackbar.LENGTH_SHORT)
                        .show();
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}
