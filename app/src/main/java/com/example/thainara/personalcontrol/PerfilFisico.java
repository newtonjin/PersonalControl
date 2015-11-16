package com.example.thainara.personalcontrol;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by thainara on 19/10/15.
 */
@Table(databaseName = PersonalControlDatabase.NAME)
public class PerfilFisico extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "perfil_id",
                    columnType = Long.class,
                    foreignColumnName = "id")},
            saveForeignKeyModel = false)
    Perfil perfil;

    @Column
    float peso;

    @Column
    float altura;

    @Column
    int duracaoJornadaTrabalho;

    @Column
    int duracaoSono;

    @Column
    int tipoAlimentacao;

    @Column
    String dataAlteracao;

    @Column
    boolean alteracaoPeso;

    @Column
    boolean alteracaoDJTrabalho;

    @Column
    boolean alteracaoDSono;

    @Column
    boolean alteracaoTAlimentacao;

    public String getTipoAlimentacaoAsString() {
        String ret = null;

        switch (PerfilView.TIPO_ALIMENTACAO.values()[tipoAlimentacao]) {
            case NAO_SAUDAVEL:
                ret = "Não saudável";
                break;
            case POUCO_SAUDAVEL:
                ret = "Pouco saudável";
                break;
            case SAUDAVEL:
                ret = "Saudável";
                break;
            case MUITO_SAUDAVEL:
                ret = "Muito saudável";
                break;
        }

        return ret;
    }

    public String getDuracaoJornadaTrabalhoAsString() {
        return String.format("%d:%02d", duracaoJornadaTrabalho/60, duracaoJornadaTrabalho%60);
    }

    public void setDuracaoJornadaTrabalho(String duracaoJornadaTrabalho) {
        this.duracaoJornadaTrabalho = Utils.converteHoraStringParaMinutoInt(duracaoJornadaTrabalho);
    }

    public String getDuracaoSonoAsString() {
        return String.format("%d:%02d", duracaoSono/60, duracaoSono%60);
    }

    public void setDuracaoSono(String duracaoSono) {
        this.duracaoSono = Utils.converteHoraStringParaMinutoInt(duracaoSono);
    }
}
