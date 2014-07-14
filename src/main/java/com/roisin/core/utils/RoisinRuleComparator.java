package com.roisin.core.utils;

import java.util.Comparator;

import com.roisin.core.results.RoisinRule;

public class RoisinRuleComparator implements Comparator<RoisinRule> {

	@Override
	public int compare(RoisinRule arg0, RoisinRule arg1) {
		/*
		 * Si el FPR es igual, se tiene en cuenta el TPR.
		 */
		int res = 0;
		if ((arg0.getFalsePositiveRate() - arg1.getFalsePositiveRate()) == 0.0) {
			if ((arg0.getTruePositiveRate() - arg1.getTruePositiveRate()) >= 0.0) {
				res = 1;
			} else {
				res = -1;
			}
		} else {
			res = (arg0.getFalsePositiveRate() - arg1.getFalsePositiveRate()) > 0.0 ? 1 : -1;
		}
		return res;
	}

}
