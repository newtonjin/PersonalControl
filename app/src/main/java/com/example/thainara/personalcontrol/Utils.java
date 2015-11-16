package com.example.thainara.personalcontrol;

/**
 * Created by thainara on 19/10/15.
 */

public class Utils {



    public static int converteHoraStringParaMinutoInt(String horas) {
        int minutos = 0;
        String[] info = horas.replace(" h", "").split(":");
        if (info.length > 0) {
            minutos = Integer.parseInt(info[0])*60;
            if (info.length > 1) {
                minutos += Integer.parseInt(info[1]);
            }
        }

        return minutos;
    }


}
