package com.roisin.core.results;

import java.util.ArrayList;

import org.jfree.util.Log;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.learner.rules.Rule;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.operator.learner.tree.SplitCondition;

import exception.RoisinRuleException;

/**
 * Implementación de todos los métodos necesarios para la obtención de
 * resultados a partir del algoritmo Ripper.
 * 
 * @author Félix Miguel Sanjuán Segovia <fmsanse@gmail.com>
 * 
 */
public class RipperResults extends AbstractResults {

	private ExampleSet exampleSet;

	private Attribute label;

	public RipperResults(RuleModel ruleModel, ExampleSet exampleSet) {
		this.rules = new ArrayList<RoisinRule>();
		this.exampleSet = exampleSet;
		this.label = ruleModel.getLabel();
		// Populate rules.
		for (Rule rule : ruleModel.getRules()) {
			try {
				rules.add(new RoisinRuleImpl(getPremise(rule), rule.getLabel(), getPrecision(rule),
						getSupport(rule), getRuleStats(rule)));
			} catch (RoisinRuleException e) {
				Log.error("Imposible crear la regla");
			}
		}
	}

	private String getPremise(Rule rule) {
		String premise = new String();
		for (SplitCondition condition : rule.getTerms()) {
			premise += condition;
		}
		return premise;
	}

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

	private double getPrecision(Rule rule) {
		return new Double(getAciertos(rule)) / new Double(rule.getCovered(this.exampleSet).size());
	}

	private double getSupport(Rule rule) {
		int numEjemplosClase = 0;
		for (Example example : this.exampleSet) {
			if (example.getValueAsString(this.label).equals(rule.getLabel())) {
				numEjemplosClase++;
			}
		}
		return new Double(getAciertos(rule)) / new Double(numEjemplosClase);
	}

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

}
