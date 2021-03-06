package com.roisin.core.results;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.rapidminer.example.Example;
import com.rapidminer.example.table.DataRow;
import com.roisin.core.utils.Utils;

import exception.RoisinRuleException;

/**
 * Implementaci�n del tipo de dato RoisinRule.
 * 
 * @author F�lix Miguel Sanju�n Segovia <fmsanse@gmail.com>
 * 
 */
public class RoisinRuleImpl implements RoisinRule {

	/**
	 * Premisa (antecedente).
	 */
	private String premise;

	/**
	 * Conclusi�n (clase).
	 */
	private String conclusion;

	/**
	 * Precisi�n.
	 */
	private double precision;

	/**
	 * Soporte.
	 */
	private double support;

	/**
	 * True positive rate.
	 */
	private double tpr;

	/**
	 * False positive rate.
	 */
	private double fpr;

	/**
	 * True positives.
	 */
	private int tp;

	/**
	 * True negatives.
	 */
	private int tn;

	/**
	 * False positives.
	 */
	private int fp;

	/**
	 * False negatives.
	 */
	private int fn;

	/**
	 * Area under the curve.
	 */
	private double auc;

	/**
	 * Examples that are covered by the rule.
	 */
	private List<Example> coveredExamples;

	/**
	 * Constructor público.
	 * 
	 * @param premise
	 * @param conclusion
	 * @param precision
	 * @param support
	 * @param stats
	 * @throws RoisinRuleException
	 */
	public RoisinRuleImpl(String premise, String conclusion, double precision, double support,
			int[] stats, List<Example> coveredExamples) throws RoisinRuleException {
		super();
		if (!(premise != null && conclusion != null)) {
			throw new RoisinRuleException("Error en la creación de reglas");
		}
		this.premise = premise;
		this.conclusion = conclusion;
		this.precision = precision;
		this.support = support;
		this.tp = stats[0];
		this.tn = stats[1];
		this.fp = stats[2];
		this.fn = stats[3];
		this.tpr = new Double(tp) / new Double(tp + fn);
		this.fpr = new Double(fp) / new Double(tn + fp);
		this.coveredExamples = coveredExamples;
		this.auc = calculateAuc();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getPremise()
	 */
	@Override
	public String getPremise() {
		return premise;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getConclusion()
	 */
	@Override
	public String getConclusion() {
		return conclusion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getPrecision()
	 */
	@Override
	public double getPrecision() {
		return precision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getSupport()
	 */
	@Override
	public double getSupport() {
		return support;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getTruePositiveRate()
	 */
	@Override
	public double getTruePositiveRate() {
		return tpr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getFalsePositiveRate()
	 */
	@Override
	public double getFalsePositiveRate() {
		return fpr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getTruePositives()
	 */
	@Override
	public int getTruePositives() {
		return tp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getTrueNegatives()
	 */
	@Override
	public int getTrueNegatives() {
		return tn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getFalsePositives()
	 */
	@Override
	public int getFalsePositives() {
		return fp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getFalseNegatives()
	 */
	@Override
	public int getFalseNegatives() {
		return fn;
	}

	private double calculateAuc() {
		// El cálculo del área bajo la curva se debe de realizar teniendo en
		// cuenta tpr (y) y fpr (x).
		double auc = 0.0;
		// Área del primer triángulo
		auc += Math.abs(((getFalsePositiveRate() * getTruePositiveRate()) / 2.0));
		// Área del segundo triángulo (el del trapecio).
		auc += Math.abs((((1.0 - getFalsePositiveRate()) * (1.0 - getTruePositiveRate())) / 2.0));
		// Área del rectánculo del trapecio.
		auc += Math.abs((1.0 - getFalsePositiveRate()) * getTruePositiveRate());

		return auc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getAuc()
	 */
	@Override
	public double getAuc() {
		return this.auc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getCoveredExamples()
	 */
	@Override
	public List<Example> getCoveredExamples() {
		return coveredExamples;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getCoveredDataRows()
	 */
	@Override
	public Set<DataRow> getCoveredDataRows() {
		Set<DataRow> coveredDataRows = Sets.newHashSet();
		for (Example example : coveredExamples) {
			coveredDataRows.add(example.getDataRow());
		}
		return coveredDataRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#truncateValues()
	 */
	@Override
	public void truncateValues() {
		this.precision = Utils.truncateValue(precision);
		this.support = Utils.truncateValue(support);
		this.tpr = Utils.truncateValue(tpr);
		this.fpr = Utils.truncateValue(fpr);
		this.auc = Utils.truncateValue(auc);
	}

	public String toString() {
		String res = new String();
		res += "Antecedente: " + getPremise();
		res += "\nClase: " + getConclusion();
		res += "\nPrecisi�n: " + getPrecision();
		res += "\nSoporte: " + getSupport();
		res += "\nTrue Positives: " + getTruePositives();
		res += "\nTrue Negatives: " + getTrueNegatives();
		res += "\nFalse Positives: " + getFalsePositives();
		res += "\nFalse Negatives: " + getFalseNegatives();
		res += "\nTPR: " + getTruePositiveRate();
		res += "\nFPR: " + getFalsePositiveRate();
		res += "\n�rea bajo la curva: " + getAuc();
		res += "\nEjemplos que cumplen la regla: ";
		for (Example example : getCoveredExamples()) {
			res += "\nEjemplo: " + example.toString();
		}
		return res;
	}

}
