package com.example.thainara.personalcontrol;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Select;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Thainara on 28/09/2015.
 */

public class PerfilView extends FrameLayout {
    @Bind(R.id.view_perfil_nome_edit) EditText nomeEdit;
    @Bind(R.id.view_perfil_data_nasc_edit) EditText dataNascEdit;
    @Bind(R.id.view_perfil_peso_edit) EditText pesoEdit;
    @Bind(R.id.view_perfil_altura_edit) EditText alturaEdit;
    @Bind(R.id.view_perfil_duracao_jornada_trabalho_edit) EditText duracaoJornadaTrabalhoEdit;
    @Bind(R.id.view_perfil_horas_sono_diarias_edit) EditText horasSonoDiariasEdit;
    @Bind(R.id.view_perfil_tipo_alimentacao_radio_group) RadioGroup tipoAlimentacaoRadioGroup;

    public enum TIPO_ALIMENTACAO {
        NAO_SAUDAVEL,
        POUCO_SAUDAVEL,
        SAUDAVEL,
        MUITO_SAUDAVEL
    }

    public PerfilView(Context context) {
        super(context);
        init();
    }

    public PerfilView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        inflate(getContext(), R.layout.view_perfil, this);
        ButterKnife.bind(this);

        Perfil perfil = new Select().from(Perfil.class).querySingle();
        if (perfil != null) {
            nomeEdit.setText(perfil.nome);
            dataNascEdit.setText(perfil.dataNascimento);
        }

        PerfilFisico perfilFisico = new Select().from(PerfilFisico.class).orderBy(false, "id").querySingle();
        if (perfilFisico != null) {
            pesoEdit.setText(String.valueOf(perfilFisico.peso));
            alturaEdit.setText(String.valueOf(perfilFisico.altura));
            duracaoJornadaTrabalhoEdit.setText(perfilFisico.getDuracaoJornadaTrabalhoAsString());
            horasSonoDiariasEdit.setText(perfilFisico.getDuracaoSonoAsString());
            switch (TIPO_ALIMENTACAO.values()[perfilFisico.tipoAlimentacao]) {
                case NAO_SAUDAVEL:
                    tipoAlimentacaoRadioGroup.check(R.id.alimentacaoNaoSaudavelRadio);
                    break;
                case POUCO_SAUDAVEL:
                    tipoAlimentacaoRadioGroup.check(R.id.alimentacaoPoucoSaudavelRadio);
                    break;
                case SAUDAVEL:
                    tipoAlimentacaoRadioGroup.check(R.id.alimentacaoSaudavelRadio);
                    break;
                case MUITO_SAUDAVEL:
                    tipoAlimentacaoRadioGroup.check(R.id.alimentacaoMuitoSaudavelRadio);
                    break;
            }
        }
    }

    @OnClick(R.id.view_perfil_salvar_button)
    public void onClickSalvar() {
        if (!isFormValid())
            return;

        Perfil perfil = new Select().from(Perfil.class).querySingle();
        if (perfil == null) {
            perfil = new Perfil();
        }

        perfil.nome = nomeEdit.getText().toString();
        perfil.dataNascimento = dataNascEdit.getText().toString();
        perfil.save();

        PerfilFisico perfilFisico = new Select().from(PerfilFisico.class).orderBy(false, "id").querySingle();

        if (possuiDiferencaInfo(perfilFisico)) {
            perfilFisico = new PerfilFisico();
            perfilFisico.perfil = perfil;

            perfilFisico.peso = Float.valueOf(pesoEdit.getText().toString());
            perfilFisico.altura = Float.valueOf(alturaEdit.getText().toString());

            TIPO_ALIMENTACAO ta = TIPO_ALIMENTACAO.NAO_SAUDAVEL;
            switch (tipoAlimentacaoRadioGroup.getCheckedRadioButtonId()) {
                case R.id.alimentacaoPoucoSaudavelRadio:
                    ta = TIPO_ALIMENTACAO.POUCO_SAUDAVEL;
                    break;
                case R.id.alimentacaoSaudavelRadio:
                    ta = TIPO_ALIMENTACAO.SAUDAVEL;
                    break;
                case R.id.alimentacaoMuitoSaudavelRadio:
                    ta = TIPO_ALIMENTACAO.MUITO_SAUDAVEL;
                    break;
            }

            perfilFisico.tipoAlimentacao = ta.ordinal();
            perfilFisico.duracaoJornadaTrabalho = Utils.converteHoraStringParaMinutoInt(duracaoJornadaTrabalhoEdit.getText().toString());
            perfilFisico.duracaoSono = Utils.converteHoraStringParaMinutoInt(horasSonoDiariasEdit.getText().toString());
            perfilFisico.save();

            Snackbar.make(this, "Informações salvas com sucesso...", Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean possuiDiferencaInfo(PerfilFisico perfilFisico) {
        if (perfilFisico == null)
            return true;

        if(perfilFisico.peso != Float.valueOf(pesoEdit.getText().toString())) { //peso
            return true;
        }

        if (perfilFisico.altura != Float.valueOf(alturaEdit.getText().toString()))//altura
            return true;

        TIPO_ALIMENTACAO ta = TIPO_ALIMENTACAO.NAO_SAUDAVEL;
        switch (tipoAlimentacaoRadioGroup.getCheckedRadioButtonId()) {
            case R.id.alimentacaoPoucoSaudavelRadio:
                ta = TIPO_ALIMENTACAO.POUCO_SAUDAVEL;
                break;
            case R.id.alimentacaoSaudavelRadio:
                ta = TIPO_ALIMENTACAO.SAUDAVEL;
                break;
            case R.id.alimentacaoMuitoSaudavelRadio:
                ta = TIPO_ALIMENTACAO.MUITO_SAUDAVEL;
                break;
        }

        if (TIPO_ALIMENTACAO.values()[perfilFisico.tipoAlimentacao] != ta) //tipo alimentacao
            return true;

        int duracaoJornadaTrabalho = Utils.converteHoraStringParaMinutoInt(duracaoJornadaTrabalhoEdit.getText().toString());
        if (perfilFisico.duracaoJornadaTrabalho != duracaoJornadaTrabalho) //duracao jornada trabalho
            return true;

        int duracaoSono = Utils.converteHoraStringParaMinutoInt(horasSonoDiariasEdit.getText().toString());
        if (perfilFisico.duracaoSono != duracaoSono) //duracao sono
            return true;

        return false;
    }

    private boolean isFormValid() {
        if (nomeEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(this, "Informe seu nome", Snackbar.LENGTH_LONG)
                    .show();
            nomeEdit.requestFocus();
            return false;
        }

        if (dataNascEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(this, "Informe sua data de nascimento", Snackbar.LENGTH_LONG)
                    .show();
            dataNascEdit.requestFocus();
            return false;
        }

        if (pesoEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(this, "Informe seu peso", Snackbar.LENGTH_LONG)
                    .show();
            pesoEdit.requestFocus();
            return false;
        }

        if (alturaEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(this, "Informe sua altura", Snackbar.LENGTH_LONG)
                    .show();
            alturaEdit.requestFocus();
            return false;
        }

        if (duracaoJornadaTrabalhoEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(this, "Informe a duração de sua jornada de trabalho diária", Snackbar.LENGTH_LONG)
                    .show();
            duracaoJornadaTrabalhoEdit.requestFocus();
            return false;
        }

        if (horasSonoDiariasEdit.getText().toString().isEmpty()) {
            Snackbar
                    .make(this, "Informe a duração de seu sono diariamente", Snackbar.LENGTH_LONG)
                    .show();
            horasSonoDiariasEdit.requestFocus();
            return false;
        }

        if (tipoAlimentacaoRadioGroup.getCheckedRadioButtonId() < 0) {
            Snackbar
                    .make(this, "Informe o tipo de sua alimentação", Snackbar.LENGTH_LONG)
                    .show();
            tipoAlimentacaoRadioGroup.requestFocus();
            return false;
        }

        return true;
    }
}
