package com.roisin.core.processes;

import java.io.File;
import java.io.IOException;

import com.rapidminer.Process;
import com.rapidminer.tools.XMLException;

public class SampleProcesses {

	public static Process getDecisionTreeToRules() {
		Process process = null;
		try {
			process = new Process(new File("roisin-processes/golf-decision-tree-to-rules.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}
		return process;
	}

	public static Process getSubgroupDiscretization() {
		Process process = null;
		try {
			process = new Process(new File("roisin-processes/golf-subgroup-discretization.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}
		return process;
	}

	public static Process getRipper() {
		Process process = null;
		try {
			process = new Process(new File("roisin-processes/ripper-golf.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}
		return process;
	}

	public static Process getRipperGolfFromExcel() {
		Process process = null;
		try {
			process = new Process(new File("roisin-processes/ripper-golf-from-excel.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}
		return process;
	}

	public static Process getRipperSonnar() {
		Process process = null;
		try {
			process = new Process(new File("roisin-processes/ripper-sonar.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}
		return process;
	}

	public static Process getSubgroupDiscretizationRipley() {
		Process process = null;
		try {
			process = new Process(new File("roisin-processes/ripley-subgroup-discretization.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		}
		return process;
	}

}
