package com.example.thainara.personalcontrol;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExercicioActivity extends AppCompatActivity {
    @Bind(R.id.activity_exercicio_nome_edit) EditText nomeEdit;
    @Bind(R.id.activity_exercicio_descricao_edit) MultiAutoCompleteTextView descricaoEdit;
    @Bind(R.id.activity_exercicio_duracao_edit) EditText duracaoEdit;
    @Bind(R.id.activity_exercicio_add_exerc_view) View addExercicioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicio);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_exercicio_salvar_button)
    public void onClickSalvar() {
        if (!isFormValid())
            return;

        //String nome = nomeEdit.getText().toString();

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
