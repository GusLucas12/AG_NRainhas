/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.agsemroleta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Pichau
 */
public class IndividuoNRainhas implements Individuo {

    private int nRainhas;
    private int[] vars;
    private double avaliacao = -1;
    private Random random = new Random();

    public IndividuoNRainhas(int nRainhas) {
        this(nRainhas, true);
    }

    private IndividuoNRainhas(int nRainhas, boolean r) {
        this.nRainhas = nRainhas;
        vars = new int[nRainhas];
        if (r) {
            for (int i = 0; i < nRainhas; i++) {
                vars[i] = random.nextInt((nRainhas - 1));
            }
            //com valore entre 0 e nRainhas -1;
        }
    }

    @Override
    public List<Individuo> recombinar(Individuo ind) {
        List<Individuo> filhos = new ArrayList<>();
        if (ind instanceof IndividuoNRainhas) {
            IndividuoNRainhas outro = (IndividuoNRainhas) ind;
            IndividuoNRainhas f1 = new IndividuoNRainhas(this.nRainhas, false);
            IndividuoNRainhas f2 = new IndividuoNRainhas(this.nRainhas, false);

            //f1 recebe parte dos genes do p1 e parte dos genes do p2;
            //f2 recebe parte dos genes do p2 e parte dos genes do p1;
            int pontoCorte = random.nextInt(nRainhas);
            for (int i = 0; i < pontoCorte; i++) {
                f1.vars[i] = this.vars[i];
                f2.vars[i] = outro.vars[i];
            }
            for (int i = pontoCorte; i < nRainhas; i++) {
                f1.vars[i] = outro.vars[i];
                f2.vars[i] = this.vars[i];
            }
            filhos.add(f1);
            filhos.add(f2);
        }
        return filhos;
    }

    @Override
    public Individuo mutar() {
        IndividuoNRainhas m = new IndividuoNRainhas(this.nRainhas, false);
        //Taxa de mutação
        double txm = 0.2;
        for (int i = 0; i < this.vars.length; i++) {
            double r = Math.random();
            if (r < txm) {
                //m.vars[i] recebe um valor aleatorio inteiro entre 0 e nRainhas -1 
                //excluindo o gene que se encontra no this.vars[i].
                int novoValor;
                do {
                    novoValor = random.nextInt(nRainhas);
                } while (novoValor == this.vars[i]);
                m.vars[i] = novoValor;
            } else {
                m.vars[i] = this.vars[i];
            }
        }

        //Se mutante for identico ao pai (this) escolho um gene qualquer e forço a mutação.
        boolean identico = true;
        for (int i = 0; i < this.vars.length; i++) {
            if (m.vars[i] != this.vars[i]) {
                identico = false;
                break;
            }
        }
        if (identico) {
            int pos = random.nextInt(nRainhas);
            int novoValor;
            do {
                novoValor = random.nextInt(nRainhas);
            } while (novoValor == this.vars[pos]);
            m.vars[pos] = novoValor;
        }

        return m;
    }

    @Override

    public double getAvaliacao() {
       /* if (this.avaliacao < 0) {
            int conflitos = 0;

            // Verificação de conflitos
            for (int i = 0; i < nRainhas; i++) {
                for (int j = i + 1; j < nRainhas; j++) {
                    if (vars[i] == vars[j] || Math.abs(vars[i] - vars[j]) == j - i) {
                        conflitos++;
                    }
                }
            }
            this.avaliacao = -conflitos; // Menos conflitos, melhor a avaliação
        }
        return this.avaliacao;*/
      double resultado = 0;

        for (int i = 0; i < vars.length - 1; i++) {
            for (int j = i + 1; j < vars.length; j++) {
                if ((vars[j] == vars[i] + (j - i)) || (vars[j] == vars[i] - (j - i)) || (vars[i] == vars[j])) {
                    resultado++;
                }
            }
        }
        return resultado;

    }
    
     public void imprimirTabuleiro() {
        for (int i = 0; i < nRainhas; i++) {
            for (int j = 0; j < nRainhas; j++) {
          
                if (vars[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
