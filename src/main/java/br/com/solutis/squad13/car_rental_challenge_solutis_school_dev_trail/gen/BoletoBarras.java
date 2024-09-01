package br.com.solutis.squad13.car_rental_challenge_solutis_school_dev_trail.gen;

import java.util.Random;

public class BoletoBarras {
    public static String gerarCodigoDeBarras() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        // Gerar um código de barras fictício com 44 dígitos
        for (int i = 0; i < 44; i++) {
            codigo.append(random.nextInt(10));
        }

        return codigo.toString();
    }
}