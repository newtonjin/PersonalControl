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

public class RelatorioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        int tipoRelatorio = getIntent().getIntExtra(RelatoriosView.EXTRA_TIPO_RELATORIO, 0);
        if (tipoRelatorio <= 0)
            return;

        String nomeColuna = null;
        if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_DURACAO_SONO) {
            nomeColuna = PerfilFisico$Table.ALTERACAODSONO;
        } else if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_DURACAO_JORNADA_TRABALHO) {
            nomeColuna = PerfilFisico$Table.ALTERACAODJTRABALHO;
        } else if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_PESO) {
            nomeColuna = PerfilFisico$Table.ALTERACAOPESO;
        } else if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_TIPO_ALIMENTACAO) {
            nomeColuna = PerfilFisico$Table.ALTERACAOTALIMENTACAO;
        }

        final List<PerfilFisico> perfilFisicos = new Select().from(PerfilFisico.class).where(Condition.column(nomeColuna).eq(true)).queryList();
        if (perfilFisicos.size() <= 0)
            return;

        List<CharSequence> listaNomes = new ArrayList<CharSequence>();

        if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_DURACAO_SONO) {
            for (PerfilFisico pf : perfilFisicos) {
                String s = String.format("Duração Sono: %s em %s", pf.getDuracaoSonoAsString(), pf.dataAlteracao);
                listaNomes.add(s);
            }
        } else if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_TIPO_ALIMENTACAO) {
            for (PerfilFisico pf : perfilFisicos) {
                String s = String.format("Tipo Alimentação: %s em %s", pf.getTipoAlimentacaoAsString(), pf.dataAlteracao);
                listaNomes.add(s);
            }
        } else if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_PESO) {
            for (PerfilFisico pf : perfilFisicos) {
                String s = String.format("Peso: %.2f em %s", pf.peso, pf.dataAlteracao);
                listaNomes.add(s);
            }
        } else if (tipoRelatorio == RelatoriosView.TIPO_REL_ALT_DURACAO_JORNADA_TRABALHO) {
            for (PerfilFisico pf : perfilFisicos) {
                String s = String.format("Jornada Trabalho: %s em %s", pf.getDuracaoJornadaTrabalhoAsString(), pf.dataAlteracao);
                listaNomes.add(s);
            }
        }

        final ArrayAdapter adapter =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, listaNomes);

        final ListView alteracoesListView = (ListView) findViewById(R.id.activity_relatorio_relatorios_list);

        alteracoesListView.setAdapter(adapter);
    }
}
