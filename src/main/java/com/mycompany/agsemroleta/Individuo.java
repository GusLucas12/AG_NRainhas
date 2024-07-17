/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.agsemroleta;
import java.util.List;
/**
 *
 * @author Pichau
 */
public interface Individuo {
    
	List<Individuo> recombinar(Individuo ind);
	
	Individuo mutar();
	
	double getAvaliacao();

}
