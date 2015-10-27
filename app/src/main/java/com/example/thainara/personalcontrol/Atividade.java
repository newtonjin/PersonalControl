package com.example.thainara.personalcontrol;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by thainara on 26/10/15.
 */
@Table(databaseName = PersonalControlDatabase.NAME)
public class Atividade extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "exercicio_id",
                    columnType = Long.class,
                    foreignColumnName = "id")},
            saveForeignKeyModel = false)
    Exercicio exercicio;

    @Column
    String data;

    @Column
    int hora;

    public String getHoraAsString() {
        return String.format("%d:%02d", hora/60, hora%60);
    }

    public void setDuracaoSono(String horario) {
        this.hora = Utils.converteHoraStringParaMinutoInt(horario);
    }
}
