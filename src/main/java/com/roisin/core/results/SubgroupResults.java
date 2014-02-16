package com.roisin.core.results;

import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;

import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.learner.subgroups.RuleSet;
import com.rapidminer.operator.learner.subgroups.hypothesis.Rule;
import com.rapidminer.operator.learner.subgroups.utility.UtilityFunction;

import exception.RoisinRuleException;

/**
 * Implementación de todos los métodos necesarios para la obtención de
 * resultados a partir del algoritmo subgroup discovery.
 * 
 * @author Félix Miguel Sanjuán Segovia <fmsanse@gmail.com>
 * 
 */
public class SubgroupResults extends AbstractRoisinResults {

	/**
	 * Constructor público.
	 * 
	 * @param ruleSet
	 * @param exampleSet
	 */
	public SubgroupResults(RuleSet ruleSet, ExampleSet exampleSet) {
		super();
		for (int i = 0; i < ruleSet.getNumberOfRules(); i++) {
			Rule rule = ruleSet.getRule(i);
			try {
				rules.add(new RoisinRuleImpl(getPremise(rule), getLabel(rule), getPrecision(rule),
						getSupport(rule, exampleSet), getRuleStats(rule, exampleSet),
						getCoveredExamples(rule, exampleSet)));
			} catch (RoisinRuleException e) {
				Log.error("Imposible crear la regla");
			}
		}
		// Solapamiento
		applyOverlappingProcedure();
	}

	/**
	 * Este método devuelve el número total de aciertos de la regla que se pasa
	 * como parámetro.
	 * 
	 * @param rule
	 *            regla
	 * @return aciertos número total de aciertos
	 */
	private int getAciertos(Rule rule) {
		// Si la conclusión es positiva, devuelve la frecuencia positiva. En
		// casos contrario, devuelve la negativa.
		return rule.getConclusion().getValue() > 0 ? (int) rule.getPositiveWeight() : (int) rule
				.getNegativeWeight();
	}

	/**
	 * Devuelve el antecedente de la regla que se pasa como parámetro.
	 * 
	 * @param rule
	 *            regla
	 * @return
	 */
	private String getPremise(Rule rule) {
		return rule.getPremise().toString();
	}

	/**
	 * Devuelve la clase de la regla que se pasa como parámetro.
	 * 
	 * @param rule
	 *            regla
	 * @return
	 */
	private String getLabel(Rule rule) {
		return rule.getConclusion().getValueAsString();
	}

	/**
	 * Devuelve la precisión de la regla que se pasa como parámetro.
	 * 
	 * @param rule
	 *            regla
	 * @return
	 */
	private double getPrecision(Rule rule) {
		return rule.getUtility(UtilityFunction.getUtilityFunctionClass(UtilityFunction.PRECISION));
	}

	/**
	 * Devuelve el soporte de la regla que se pasa como parámetro.
	 * 
	 * @param rule
	 *            regla
	 * @return soporte
	 */
	private double getSupport(Rule rule, ExampleSet exampleSet) {
		int aciertos = getAciertos(rule);
		int numEjemplosClase = 0;
		for (Example example : exampleSet) {
			if (example.getLabel() == rule.getConclusion().getValue()) {
				numEjemplosClase++;
			}
		}
		return new Double(aciertos) / new Double(numEjemplosClase);
	}

	/**
	 * Devuelve un array con cuatro enteros cuyos valores indican los tp, tn, fp
	 * y fn de la regla que se pasa como parámetro.
	 * 
	 * @param rule
	 *            regla
	 * @param exampleSet
	 *            conjunto de datos de ejemplo
	 * @return stats array de enteros
	 */
	private int[] getRuleStats(Rule rule, ExampleSet exampleSet) {
		// Donde 0-tp, 1-tn, 2-fp, 3-fn
		int[] stats = new int[4];
		// True positives
		stats[0] = getAciertos(rule);
		// False positives
		stats[2] = rule.getConclusion().getValue() > 0 ? (int) rule.getNegativeWeight()
				: (int) rule.getPositiveWeight();
		for (Example example : exampleSet) {
			if (!rule.getPremise().applicable(example)) {
				if (example.getLabel() != rule.getConclusion().getValue()) {
					// True negatives
					stats[1]++;
				} else {
					// False negatives
					stats[3]++;
				}
			}
		}
		return stats;
	}

	/**
	 * Devuelve una lista que contiene todos los ejemplos cubiertos por la
	 * regla. Para la creación de esta lista se tiene en cuenta si el ejemplo ya
	 * ha sido cubierto por otra regla. En tal caso, no se añade (solapamiento).
	 * 
	 * @param rule
	 *            regla
	 * @param exampleSet
	 *            conjunto de datos de ejemplo
	 * @return coveredExamples lista de ejemplos cubiertos por la regla
	 */
	private List<Example> getCoveredExamples(Rule rule, ExampleSet exampleSet) {
		List<Example> coveredExamples = new ArrayList<Example>();
		for (Example example : exampleSet) {
			if (rule.applicable(example)) {
				coveredExamples.add(example);
			}
		}
		return coveredExamples;
	}

}
