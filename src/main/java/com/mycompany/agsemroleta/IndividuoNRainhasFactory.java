/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.agsemroleta;

/**
 *
 * @author Pichau
 */
public class IndividuoNRainhasFactory implements IndividuoFactory{

    private int nRainhas;

    public IndividuoNRainhasFactory(int nRainhas) {
        this.nRainhas = nRainhas;
    }

    @Override
    public Individuo getIndividuo() {
        return new IndividuoNRainhas(nRainhas);
    }

}
