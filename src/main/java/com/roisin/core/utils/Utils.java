package com.roisin.core.utils;

import com.rapidminer.example.ExampleSet;

public class Utils {

	public static int getNumEjemplosTotalExampleSet(ExampleSet exampleSet) {
		return exampleSet.getExampleTable().size();
	}

	public static boolean isConsecutivo(int a, int b) {
		return b - a == 1;
	}

	public static double truncateValue(double value) {
		return Math.floor(value * Constants.TRUNCATE_VALUE) / Constants.TRUNCATE_VALUE;
	}

}
