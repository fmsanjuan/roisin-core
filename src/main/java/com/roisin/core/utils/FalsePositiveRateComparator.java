package com.roisin.core.utils;

import java.util.Comparator;

import com.roisin.core.results.RoisinRule;

public class FalsePositiveRateComparator implements Comparator<RoisinRule> {

	@Override
	public int compare(RoisinRule arg0, RoisinRule arg1) {
		/*
		 * Si el fpr es igual, se tiene en cuenta el fpr.
		 */
		int res = 0;
		if ((int) (arg0.getFalsePositiveRate() - arg1.getFalsePositiveRate()) == 0) {
			if ((int) (arg0.getTruePositiveRate() - arg1.getTruePositiveRate()) == 0) {
				res = 1;
			} else {
				res = (int) (arg0.getTruePositiveRate() - arg1.getTruePositiveRate());
			}
		} else {
			res = (int) (arg0.getFalsePositiveRate() - arg1.getFalsePositiveRate());
		}
		return res;
	}

}
