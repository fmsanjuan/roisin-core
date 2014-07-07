package com.roisin.core.now;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.roisin.core.processes.SampleProcesses;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.RoisinResults;

public class App {

	/**
	 * Trial Log
	 */
	public static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		// Iniciando rapidminer
		try {
			log.info("Iniciando rapidminer");
			RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
			RapidMiner.init();
		} catch (Exception e) {
			log.error("No ha sido posible iniciar Rapidminer. Revise la configuración.");
		}
		Log.info("Rapidminer iniciado");

		try {

			Process process1 = SampleProcesses.getRipper();

			IOContainer container1 = process1.run();
			RuleModel ruleModel1 = (RuleModel) container1.asList().get(0);
			ExampleSet exampleSet1 = (ExampleSet) container1.asList().get(1);
			RoisinResults results1 = new RipperResults(ruleModel1, exampleSet1);
			System.out.println(results1);

			Process process2 = SampleProcesses.getRipperRipley();

			IOContainer container2 = process2.run();
			RuleModel ruleModel2 = (RuleModel) container2.asList().get(0);
			ExampleSet exampleSet2 = (ExampleSet) container2.asList().get(1);
			RoisinResults results2 = new RipperResults(ruleModel2, exampleSet2);
			System.out.println(results2);

		} catch (OperatorException e) {
			e.printStackTrace();
		}
	}
}
