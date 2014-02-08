package com.roisin.core.results;

import java.util.List;

import com.rapidminer.example.Example;

public interface RoisinRule {

	/**
	 * Devuelve el antecedente.
	 * 
	 * @return premise antecedente
	 */
	String getPremise();

	/**
	 * Devuelve la clase de la regla.
	 * 
	 * @return conclusion clase
	 */
	String getConclusion();

	/**
	 * Devuelve la precisi�n de la regla.
	 * 
	 * @return precision precisi�n de la regla
	 */
	double getPrecision();

	/**
	 * Devuelve el soporte de la regla.
	 * 
	 * @return soporte
	 */
	double getSupport();

	/**
	 * Devuelve el TPR.
	 * 
	 * @return tpr tpr
	 */
	double getTruePositiveRate();

	/**
	 * Devuelve el FPR.
	 * 
	 * @return fpr fpr
	 */
	double getFalsePositiveRate();

	/**
	 * Devuelve el n�mero total de verdaderos positivos de la regla.
	 * 
	 * @return tp n�mero total de tp de la regla
	 */
	int getTruePositives();

	/**
	 * Devuelve el n�mero total de verdaderos negativos de la regla.
	 * 
	 * @return tn n�mero total de verdaderos negativos
	 */
	int getTrueNegatives();

	/**
	 * Devuelve el n�mero total de falsos positivos de la regla.
	 * 
	 * @return fp n�mero total de falsos positivos
	 */
	int getFalsePositives();

	/**
	 * Devuelve el n�mero total de falsos negativos de la regla.
	 * 
	 * @return fn n�mero total de falsos negativos
	 */
	int getFalseNegatives();

	/**
	 * Devuelve una lista con todos los ejemplos cubiertos por la regla.
	 * 
	 * @return coveredExamples lista de ejemplos
	 */
	List<Example> getCoveredExamples();
}
