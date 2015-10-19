package com.example.thainara.personalcontrol;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by thainara on 19/10/15.
 */
@Table(databaseName = PersonalControlDatabase.NAME)
public class Perfil extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String nome;

    @Column
    String dataNascimento;
}
