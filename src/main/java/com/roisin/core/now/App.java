package com.roisin.core.now;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.rules.RuleModel;
import com.rapidminer.operator.learner.subgroups.RuleSet;
import com.rapidminer.operator.learner.subgroups.hypothesis.Rule;
import com.roisin.core.processes.GenericProcesses;
import com.roisin.core.processes.SampleProcesses;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.SubgroupResults;
import com.roisin.core.utils.Constants;
import com.roisin.core.utils.Utils;

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
			log.info("Er rapidminer dise que no arranca, que no tiene gana.");
		}
		Log.info("Rapidminer iniciado");

		// Obtenci—n de proceso
		// Process process = SampleProcesses.getRipper();
		// Process process = SampleProcesses.getRipperGolfFromExcel();
		// Process process = SampleProcesses.getDecisionTreeToRules();
		// Process process = SampleProcesses.getSubgroupDiscretization();
		Process process1 = SampleProcesses.getSubgroupDiscretizationRipley();
		Process process2 = SampleProcesses.getRipperSonnar();
		// Process process = GenericProcesses.getRipper();
		Process process3 = GenericProcesses.getDecisionTreeToRules();
		// Process process =
		// GenericProcesses.getSubgroupDiscoveryDiscretization();

		LinkedList<String> atributos = new LinkedList<String>();
		atributos.add("Outlook");
		atributos.add("Temperature");
		atributos.add("Humidity");
		atributos.add("Play");
		Process process4 = GenericProcesses.getRipper(Constants.CSV_FORMAT,
				"/Users/felix/03.TFG/DatosDeEjemplo/exportando/prueba-excel-csv.csv", "Play",
				"Outlook!=overcast", atributos);

		try {
			IOContainer prueba = process1.run();

			// Si el resultado es un conjunto de reglas
			RuleSet ruleSet = (RuleSet) prueba.asList().get(0);
			ExampleSet exampleSet = (ExampleSet) prueba.asList().get(1);

			SubgroupResults subgroupResults = new SubgroupResults(ruleSet,
					Utils.getNumCasosTotalFromExampleSet(exampleSet));

			IOContainer prueba2 = process2.run();
			RuleModel ruleModel = (RuleModel) prueba2.asList().get(0);
			RipperResults ripperResults = new RipperResults(ruleModel);

			IOContainer prueba3 = process3.run();
			RuleModel ruleModel2 = (RuleModel) prueba3.asList().get(0);
			RipperResults ripperResults2 = new RipperResults(ruleModel2);

			IOContainer prueba4 = process4.run();
			RuleModel ruleModel3 = (RuleModel) prueba4.asList().get(0);
			RipperResults ripperResults3 = new RipperResults(ruleModel3);

			System.out.println("Primer proceso, Subgroup con Ripley:");
			for (int i = 0; i < ruleSet.getNumberOfRules(); i++) {
				Rule rule = ruleSet.getRule(i);
				System.out.println(rule);
				System.out.println("Soporte: " + subgroupResults.getRuleSupport(rule));
				System.out.println("Precisi—n: " + subgroupResults.getRuleConfidence(rule));
			}

			System.out.println("\nSegundo proceso, Ripper con Sonar:");
			for (int i = 0; i < ruleModel.getRules().size(); i++) {
				com.rapidminer.operator.learner.rules.Rule rule = ruleModel.getRules().get(i);
				System.out.println(rule);
				System.out.println("Soporte: " + ripperResults.getRuleSupport(rule));
				System.out.println("Precisi—n: " + ripperResults.getRuleConfidence(rule));
			}

			System.out.println("\nTercer proceso, TreeToRules con Golf:");
			for (int i = 0; i < ruleModel2.getRules().size(); i++) {
				com.rapidminer.operator.learner.rules.Rule rule = ruleModel2.getRules().get(i);
				System.out.println(rule);
				System.out.println("Soporte: " + ripperResults2.getRuleSupport(rule));
				System.out.println("Precisi—n: " + ripperResults2.getRuleConfidence(rule));
			}

			System.out.println("\nCuarto proceso, Ripper con Golf:");
			for (int i = 0; i < ruleModel3.getRules().size(); i++) {
				com.rapidminer.operator.learner.rules.Rule rule = ruleModel3.getRules().get(i);
				System.out.println(rule);
				System.out.println("Soporte: " + ripperResults3.getRuleSupport(rule));
				System.out.println("Precisi—n: " + ripperResults3.getRuleConfidence(rule));
			}

			// ////////////////////////////////////////////////////////
			// //////////// Importando/Exportando /////////////////////
			// ////////////////////////////////////////////////////////

			// Process conversion = DataTransformation.convertExcelToArff(
			// "/Users/felix/03.TFG/DatosDeEjemplo/golf.xlsx",
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava/heythere.arff");
			// conversion.run();
			//
			// Process conversion2 = DataTransformation.convertArffToExcel(
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava/heythere.arff",
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava/heytherethisworks.xls");
			// conversion2.run();
			//
			// Process conversion3 = DataTransformation.convertExcelToXrff(
			// "/Users/felix/03.TFG/DatosDeEjemplo/golf.xlsx",
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava2/golf.xrff");
			// conversion3.run();
			//
			// Process conversion4 = DataTransformation.convertXrffToExcel(
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava2/golf.xrff",
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava2/thisisworking.xls");
			// conversion4.run();

			// Process conversion5 = DataTransformation.convertExcelToCsv(
			// "/Users/felix/03.TFG/DatosDeEjemplo/golf.xlsx",
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava3/hellothere.csv");
			// conversion5.run();
			//
			// Process conversion6 = DataTransformation.convertCsvToExcel(
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava3/hellothere.csv",
			// "/Users/felix/03.TFG/DatosDeEjemplo/heytherefromjava3/thisisworking.xls");
			// conversion6.run();

		} catch (OperatorException ex) {
			log.error("Error en la ejecuci—n de un proceso");
		}
	}
}
