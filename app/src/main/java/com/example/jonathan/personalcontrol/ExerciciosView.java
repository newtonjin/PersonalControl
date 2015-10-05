
package com.example.jonathan.personalcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Thainara on 28/09/2015.
 */

public class ExerciciosView extends FrameLayout {
    public ExerciciosView(Context context) {
        super(context);
        init();
    }

    public ExerciciosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        inflate(getContext(), R.layout.view_exercicios, this);

        final ListView listView = (ListView) findViewById(R.id.listViewExercicios);
        final String[] valores = new String[]{"Elevação de pernas",
                "Frontal", "Bike", "Com Inversão", "Remada unilateral", "Crossover"};

        final ArrayAdapter adapter =
                new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, valores);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), valores[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}


//package com.example.jonathan.personalcontrol;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.FrameLayout;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
///**
// * Created by Thainara on 05/10/2015.
// */
//public class ExerciciosView extends FrameLayout {
//    public ExerciciosView(Context context) {
//        super(context);
//    }
//
//    public ExerciciosView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    private void init() {
//        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        inflate(getContext(), R.layout.view_exercicios, this);
//
//        final ListView listView = (ListView) findViewById(R.id.listViewExercicios);
//        String[] valores = new String[]{"Teste1", "Teste2"};
//
//        final ArrayAdapter adapter =
//                new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, valores);
//
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Clicou. Posição: " + position + ". id: " + id, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//}
