package com.roisin.core.now;

import java.util.LinkedList;
import java.util.SortedSet;

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
import com.roisin.core.processes.GenericProcesses;
import com.roisin.core.results.RipperResults;
import com.roisin.core.results.RoisinResults;
import com.roisin.core.results.SubgroupResults;
import com.roisin.core.utils.Constants;

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

		try {
			LinkedList<String> atributos = new LinkedList<String>();
			atributos.add("Outlook");
			atributos.add("Temperature");
			atributos.add("Humidity");
			atributos.add("Play");

			SortedSet<Integer> deletedRows = null;
			// SortedSet<Integer> deletedRows = new TreeSet<Integer>();
			// deletedRows.add(2);
			// deletedRows.add(5);
			// deletedRows.add(6);
			// deletedRows.add(7);
			// deletedRows.add(9);
			// deletedRows.add(10);
			// deletedRows.add(13);

			String path = "/Users/felix/03.TFG/DatosDeEjemplo/exportando/prueba-excel-csv.csv";
			String label = "Play";
			// String condition = "Outlook!=overcast";
			String condition = null;

			Process process1 = GenericProcesses.getRipper(Constants.CSV_FORMAT, path, label,
					deletedRows, condition, atributos);

			Process process2 = GenericProcesses.getSubgroupDiscoveryDiscretization(
					Constants.CSV_FORMAT, path, label, deletedRows, condition, atributos);

			Process process3 = GenericProcesses.getDecisionTreeToRules(Constants.CSV_FORMAT, path,
					label, deletedRows, condition, atributos);

			IOContainer container1 = process1.run();
			RuleModel ruleModel1 = (RuleModel) container1.asList().get(0);
			ExampleSet exampleSet1 = (ExampleSet) container1.asList().get(1);
			RoisinResults results1 = new RipperResults(ruleModel1, exampleSet1);
			System.out.println(results1);

			IOContainer container2 = process2.run();
			RuleSet ruleSet2 = (RuleSet) container2.asList().get(Constants.PROCESS_OUTPUT_INDEX);
			ExampleSet exampleSet2 = (ExampleSet) container2.asList().get(
					Constants.EXAMPLE_OUTPUT_INDEX);
			RoisinResults results2 = new SubgroupResults(ruleSet2, exampleSet2);
			System.out.println(results2);

			IOContainer container3 = process3.run();
			RuleModel ruleModel3 = (RuleModel) container3.asList().get(0);
			ExampleSet exampleSet3 = (ExampleSet) container3.asList().get(1);
			RoisinResults results3 = new RipperResults(ruleModel3, exampleSet3);
			System.out.println(results3);

			// Process process4 = SampleProcesses.getRipperRipley();
			// IOContainer container4 = process4.run();
			//
			// RuleModel ruleModel4 = (RuleModel) container4.asList().get(0);
			// ExampleSet exampleSet = (ExampleSet) container4.asList().get(1);
			// ruleModel4.getRules().get(0).getCovered(0)

		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Obtenci—n de proceso
		// Process process = SampleProcesses.getRipper();
		// Process process = SampleProcesses.getRipperGolfFromExcel();
		// Process process = SampleProcesses.getDecisionTreeToRules();
		// Process process = SampleProcesses.getSubgroupDiscretization();
		// Process process1 = SampleProcesses.getSubgroupDiscretizationRipley();
		// Process process2 = SampleProcesses.getRipperSonnar();
		// Process process = GenericProcesses.getRipper();
		// Process process3 = GenericProcesses.getDecisionTreeToRules();
		// Process process =
		// GenericProcesses.getSubgroupDiscoveryDiscretization();

		// LinkedList<String> atributos = new LinkedList<String>();
		// atributos.add("Outlook");
		// atributos.add("Temperature");
		// atributos.add("Humidity");
		// atributos.add("Play");
		//
		// String path =
		// "/Users/felix/03.TFG/DatosDeEjemplo/exportando/prueba-excel-csv.csv";
		// String label = "Play";
		// String condition = "Outlook!=overcast";

		// Process process4 = GenericProcesses.getRipper(Constants.CSV_FORMAT,
		// path, label, condition,
		// atributos);

		// Process process1 =
		// GenericProcesses.getSubgroupDiscoveryDiscretization(
		// Constants.CSV_FORMAT, path, label, condition, atributos);

		// Process process3 =
		// GenericProcesses.getDecisionTreeToRules(Constants.CSV_FORMAT, path,
		// label, condition, atributos);

		// try {
		// IOContainer prueba = process1.run();
		// // Si el resultado es un conjunto de reglas
		// RuleSet ruleSet = (RuleSet) prueba.asList().get(0);
		// ExampleSet exampleSet = (ExampleSet) prueba.asList().get(1);
		// SubgroupResults subgroupResults = new SubgroupResults(ruleSet,
		// Utils.getNumCasosTotalFromExampleSet(exampleSet));
		//
		// IOContainer prueba2 = process2.run();
		// RuleModel ruleModel = (RuleModel) prueba2.asList().get(0);
		// RipperResults ripperResults = new RipperResults(ruleModel);
		//
		// IOContainer prueba3 = process3.run();
		// RuleModel ruleModel2 = (RuleModel) prueba3.asList().get(0);
		// RipperResults ripperResults2 = new RipperResults(ruleModel2);
		//
		// IOContainer prueba4 = process4.run();
		// RuleModel ruleModel4 = (RuleModel) prueba4.asList().get(0);
		// RipperResults ripperResults4 = new RipperResults(ruleModel4);

		// System.out.println("Primer proceso, Subgroup con Golf:");
		// for (int i = 0; i < ruleSet.getNumberOfRules(); i++) {
		// Rule rule = ruleSet.getRule(i);
		// System.out.println(rule);
		// System.out.println("Soporte: " +
		// subgroupResults.getRuleSupport(rule));
		// System.out.println("Precisi—n: " +
		// subgroupResults.getRuleConfidence(rule));
		// }
		//
		// System.out.println("\nSegundo proceso, Ripper con Sonar:");
		// for (int i = 0; i < ruleModel.getRules().size(); i++) {
		// com.rapidminer.operator.learner.rules.Rule rule =
		// ruleModel.getRules().get(i);
		// System.out.println(rule);
		// System.out.println("Soporte: " +
		// ripperResults.getRuleSupport(rule));
		// System.out.println("Precisi—n: " +
		// ripperResults.getRuleConfidence(rule));
		// }
		//
		// System.out.println("\nTercer proceso, TreeToRules con Golf:");
		// for (int i = 0; i < ruleModel2.getRules().size(); i++) {
		// com.rapidminer.operator.learner.rules.Rule rule =
		// ruleModel2.getRules().get(i);
		// System.out.println(rule);
		// System.out.println("Soporte: " +
		// ripperResults2.getRuleSupport(rule));
		// System.out.println("Precisi—n: " +
		// ripperResults2.getRuleConfidence(rule));
		// }
		//
		// System.out.println("\nCuarto proceso, Ripper con Golf:");
		// for (int i = 0; i < ruleModel4.getRules().size(); i++) {
		// com.rapidminer.operator.learner.rules.Rule rule =
		// ruleModel4.getRules().get(i);
		// System.out.println(rule);
		// System.out.println("Soporte: " +
		// ripperResults4.getRuleSupport(rule));
		// System.out.println("Precisi—n: " +
		// ripperResults4.getRuleConfidence(rule));
		// }

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
		//
		// } catch (OperatorException ex) {
		// log.error("Error en la ejecuci—n de un proceso");
		// }
	}
}
