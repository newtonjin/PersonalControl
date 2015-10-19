package com.example.thainara.personalcontrol;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.builder.Condition;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//Tela de edição de exercício

public class ExercicioActivity extends AppCompatActivity {
    @Bind(R.id.activity_exercicio_nome_edit) EditText nomeEdit;
    @Bind(R.id.activity_exercicio_descricao_edit) MultiAutoCompleteTextView descricaoEdit;
    @Bind(R.id.activity_exercicio_duracao_edit) EditText duracaoEdit;
    @Bind(R.id.activity_exercicio_add_exerc_view) View addExercicioView;

    private Exercicio exercicio_atual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicio);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        exercicio_atual = null;

        long idExercicio = getIntent().getLongExtra(ExerciciosView.EXTRA_ID_EXERCICIO, 0);
        if (idExercicio <= 0)
            return;

        Exercicio exercicio = new Select().from(Exercicio.class).where(Condition.column(Exercicio$Table.ID).eq(idExercicio)).querySingle();
        exercicio_atual = exercicio;
        nomeEdit.setText(exercicio.nome);
        descricaoEdit.setText(exercicio.descricao);
        duracaoEdit.setText(exercicio.duracao);
    }

    @OnClick(R.id.activity_exercicio_salvar_button)
    public void onClickSalvar() {
        if (!isFormValid())
            return;

        Exercicio novoExercicio = null;
        if (exercicio_atual == null)
            novoExercicio = new Exercicio();
        else
            novoExercicio = exercicio_atual;

        String nome = nomeEdit.getText().toString();
        novoExercicio.nome = nome;
        String descricao = descricaoEdit.getText().toString();
        novoExercicio.descricao = descricao;
        String duracao = duracaoEdit.getText().toString();
        novoExercicio.duracao = duracao;
        novoExercicio.save();

        setResult(RESULT_OK);
        finish();
    }

    private boolean isFormValid() {
        if (nomeEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(addExercicioView, "Informe o nome do exercício", Snackbar.LENGTH_SHORT)
                    .show();
            nomeEdit.requestFocus();
            return false;
        }

        if (duracaoEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(addExercicioView, "Informe a duração do exercício", Snackbar.LENGTH_SHORT)
                    .show();
            duracaoEdit.requestFocus();
            return false;
        }

        return true;
    }
}
