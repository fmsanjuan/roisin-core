package com.roisin.core.results;

import java.util.List;

import org.jfree.util.Log;

import com.google.common.collect.Lists;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.learner.rules.Rule;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.operator.learner.tree.SplitCondition;

import exception.RoisinRuleException;

/**
 * Implementaci�n de todos los m�todos necesarios para la obtenci�n de
 * resultados a partir del algoritmo Ripper.
 * 
 * @author F�lix Miguel Sanju�n Segovia <fmsanse@gmail.com>
 * 
 */
public class RipperResults extends AbstractRoisinResults {

	/**
	 * Conjunto de datos de ejemplo.
	 */
	private ExampleSet exampleSet;

	/**
	 * Informaci�n sobre la clase.
	 */
	private Attribute label;

	/**
	 * Constructor público.
	 * 
	 * @param ruleModel
	 * @param exampleSet
	 */
	public RipperResults(RuleModel ruleModel, ExampleSet exampleSet) {
		super();
		this.exampleSet = exampleSet;
		this.label = ruleModel.getLabel();
		// Populate rules.
		try {
			// Se le resta uno al tamaño para eliminar la última regla.
			for (int i = 0; i < ruleModel.getRules().size() - 1; i++) {
				Rule rule = ruleModel.getRules().get(i);
				rules.add(new RoisinRuleImpl(getPremise(rule), rule.getLabel(), getPrecision(rule),
						getSupport(rule), getRuleStats(rule), getCoveredExamples(rule)));
			}
		} catch (RoisinRuleException e) {
			Log.error("Imposible crear la regla");
		}
		// Solapamiento
		applyOverlappingProcedure();
		// Cálculo del área bajo la curva
		this.auc = calculateRulesAuc();
	}

	/**
	 * Este m�todo devuelve una cadena que contiene todas las condiciones de la
	 * regla.
	 * 
	 * @param rule
	 *            regla
	 * @return
	 */
	private String getPremise(Rule rule) {
		String premise = new String();
		for (SplitCondition condition : rule.getTerms()) {
			if (rule.getTerms().indexOf(condition) < rule.getTerms().size() - 1) {
				premise += condition + " & ";
			} else {
				premise += condition;
			}
		}
		return premise;
	}

	/**
	 * Este m�todo devuelve el n�mero total de aciertos de la regla que se pasa
	 * como par�metro.
	 * 
	 * @param rule
	 *            regla
	 * @return aciertos n�mero total de aciertos
	 */
	private int getAciertos(Rule rule) {
		ExampleSet coveredExamples = rule.getCovered(this.exampleSet);
		int aciertos = 0;
		for (Example example : coveredExamples) {
			if (example.getValueAsString(this.label).equals(rule.getLabel())) {
				aciertos++;
			}
		}
		return aciertos;
	}

	/**
	 * Devuelve la precisi�n de la regla que se pasa como par�metro.
	 * 
	 * @param rule
	 *            regla
	 * @return
	 */
	private double getPrecision(Rule rule) {
		return new Double(getAciertos(rule)) / new Double(rule.getCovered(this.exampleSet).size());
	}

	/**
	 * Devuelve el soporte de la regla que se pasa como par�metro.
	 * 
	 * @param rule
	 *            regla
	 * @return soporte
	 */
	private double getSupport(Rule rule) {
		int numEjemplosClase = 0;
		for (Example example : this.exampleSet) {
			if (example.getValueAsString(this.label).equals(rule.getLabel())) {
				numEjemplosClase++;
			}
		}
		return new Double(getAciertos(rule)) / new Double(numEjemplosClase);
	}

	/**
	 * Devuelve un array con cuatro enteros cuyos valores indican los tp, tn, fp
	 * y fn de la regla que se pasa como par�metro.
	 * 
	 * @param rule
	 *            regla
	 * @return stats array de enteros
	 */
	private int[] getRuleStats(Rule rule) {
		int[] stats = new int[4];
		// Donde 0-tp, 1-tn, 2-fp, 3-fn

		// Obtenemos el los ejemplos cubiertos por la regla
		ExampleSet coveredExamples = rule.getCovered(this.exampleSet);
		for (Example example : coveredExamples) {
			if (example.getValueAsString(this.label).equals(rule.getLabel())) {
				stats[0]++;
			} else {
				stats[2]++;
			}
		}
		// Obtenemos el los ejemplos NO cubiertos por la regla
		ExampleSet nonCoveredExamples = rule.removeCovered(this.exampleSet);
		for (Example example : nonCoveredExamples) {
			if (example.getValueAsString(this.label).equals(rule.getLabel())) {
				stats[3]++;
			} else {
				stats[1]++;
			}
		}
		return stats;
	}

	/**
	 * Devuelve una lista que contiene todos los ejemplos cubiertos por la
	 * regla. Para la creaci�n de esta lista se tiene en cuenta si el ejemplo ya
	 * ha sido cubierto por otra regla. En tal caso, no se a�ade (solapamiento).
	 * 
	 * @param rule
	 *            regla
	 * @return coveredExamples conjunto de ejemplos cubiertos por la regla
	 */
	private List<Example> getCoveredExamples(Rule rule) {
		return Lists.newArrayList(rule.getCovered(exampleSet).iterator());
	}
}
