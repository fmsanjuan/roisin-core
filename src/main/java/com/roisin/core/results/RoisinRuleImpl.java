package com.roisin.core.results;

import java.util.List;

import com.rapidminer.example.Example;

import exception.RoisinRuleException;

public class RoisinRuleImpl implements RoisinRule {

	/**
	 * Premisa (antecedente).
	 */
	private String premise;

	/**
	 * Conclusi—n (clase).
	 */
	private String conclusion;

	/**
	 * Precisi—n.
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
	 * Examples that are covered by the rule.
	 */
	private List<Example> coveredExamples;

	/**
	 * Constructor pœblico.
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
			throw new RoisinRuleException("Error en la creaci—n de reglas");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.roisin.core.results.RoisinRule#getCoveredExamples()
	 */
	@Override
	public List<Example> getCoveredExamples() {
		return coveredExamples;
	}

	public String toString() {
		String res = new String();
		res += "Antecedente: " + getPremise();
		res += "\nClase: " + getConclusion();
		res += "\nPrecisi—n: " + getPrecision();
		res += "\nSoporte: " + getSupport();
		res += "\nTrue Positives: " + getTruePositives();
		res += "\nTrue Negatives: " + getTrueNegatives();
		res += "\nFalse Positives: " + getFalsePositives();
		res += "\nFalse Negatives: " + getFalseNegatives();
		res += "\nTPR: " + getTruePositiveRate();
		res += "\nFPR: " + getFalsePositiveRate();
		res += "\nEjemplos que cumplen la regla: ";
		for (Example example : getCoveredExamples()) {
			res += "\nEjemplo nœmero " + new Integer(getCoveredExamples().indexOf(example) + 1)
					+ " : " + example;
		}
		return res;
	}

}
