package com.roisin.core.processes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rapidminer.Process;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.learner.meta.Tree2RuleConverter;
import com.rapidminer.operator.learner.rules.RuleLearner;
import com.rapidminer.operator.learner.subgroups.SubgroupDiscovery;
import com.rapidminer.operator.learner.tree.DecisionTreeLearner;
import com.rapidminer.operator.nio.ExcelExampleSource;
import com.rapidminer.operator.ports.metadata.CompatibilityLevel;
import com.rapidminer.operator.preprocessing.discretization.BinDiscretization;
import com.rapidminer.operator.preprocessing.filter.ChangeAttributeRole;
import com.rapidminer.tools.OperatorService;
import com.roisin.core.utils.Constants;

public class GenericProcesses {

	private static Logger log = Logger.getLogger(GenericProcesses.class);

	public static Process getRipper() {
		Process process = new Process();
		try {
			/* Reading from Excel */
			String fileSrc = "/Users/felix/03.TFG/DatosDeEjemplo/golf.xlsx";
			ExcelExampleSource excelDataReader = OperatorService
					.createOperator(ExcelExampleSource.class);
			excelDataReader.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, fileSrc);

			/* Setting roles */
			ChangeAttributeRole setRuleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRuleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, "Play");
			setRuleOperator.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, "label");
			String[] parameter1 = { "Outlook", "regular" };
			String[] parameter2 = { "Temperature", "regular" };
			String[] parameter3 = { "Humidity", "regular" };
			String[] parameter4 = { "Wind", "regular" };
			List<String[]> parameters = new ArrayList<String[]>();
			parameters.add(parameter1);
			parameters.add(parameter2);
			parameters.add(parameter3);
			parameters.add(parameter4);

			setRuleOperator.setListParameter("set_additional_roles", parameters);

			/* Rule Induction */
			RuleLearner ruleInductionOperator = OperatorService.createOperator(RuleLearner.class);

			/* Adding operators */
			process.getRootOperator().getSubprocess(0).addOperator(excelDataReader);
			process.getRootOperator().getSubprocess(0).addOperator(setRuleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(ruleInductionOperator);

			/* Connecting operators */
			excelDataReader.getOutputPorts().getPortByName("output")
					.connectTo(setRuleOperator.getInputPorts().getPortByName("example set input"));
			setRuleOperator.getOutputPorts().getPortByName("example set output")
					.connectTo(ruleInductionOperator.getInputPorts().getPortByName("training set"));
			// Auto wire connects the last operator to result 1 automatically.
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el proceso para usar el algoritmo Ripper");
		}
		System.out.println(process);
		return process;
	}

	public static Process getRipper(String sourceFormat, String sourcePath, String label) {
		Process process = new Process();
		Operator reader = Preprocessing.getReader(sourceFormat, sourcePath);
		/* Setting roles */
		ChangeAttributeRole setRuleOperator;
		try {
			setRuleOperator = OperatorService.createOperator(ChangeAttributeRole.class);
			setRuleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, label);
			setRuleOperator
					.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, Constants.LABEL);
			/* Rule Induction */
			RuleLearner ruleInductionOperator = OperatorService.createOperator(RuleLearner.class);
			/* Adding operators */
			process.getRootOperator().getSubprocess(0).addOperator(reader);
			process.getRootOperator().getSubprocess(0).addOperator(setRuleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(ruleInductionOperator);
			// Auto wire connects the last operator to result 1 automatically.
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el proceso para usar el algoritmo Ripper");
		}
		return process;
	}

	public static Process getSubgroupDiscoveryDiscretization() {
		Process process = new Process();
		try {
			/* Reading from Excel */
			String fileSrc = "/Users/felix/03.TFG/DatosDeEjemplo/golf.xlsx";
			ExcelExampleSource excelDataReader;
			excelDataReader = OperatorService.createOperator(ExcelExampleSource.class);
			excelDataReader.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, fileSrc);

			/* Setting roles */
			ChangeAttributeRole setRoleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, "Play");
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, "label");
			String[] parameter1 = { "Outlook", "regular" };
			String[] parameter2 = { "Temperature", "regular" };
			String[] parameter3 = { "Humidity", "regular" };
			String[] parameter4 = { "Wind", "regular" };
			List<String[]> parameters = new ArrayList<String[]>();
			parameters.add(parameter1);
			parameters.add(parameter2);
			parameters.add(parameter3);
			parameters.add(parameter4);

			setRoleOperator.setListParameter("set_additional_roles", parameters);

			/* Discretization */
			BinDiscretization discretizationOperator = OperatorService
					.createOperator(BinDiscretization.class);
			/* Subgroup discovery */
			SubgroupDiscovery subgroupDiscoveryOperator = OperatorService
					.createOperator(SubgroupDiscovery.class);

			/* Adding operators to process */
			process.getRootOperator().getSubprocess(0).addOperator(excelDataReader);
			process.getRootOperator().getSubprocess(0).addOperator(setRoleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(discretizationOperator);
			process.getRootOperator().getSubprocess(0).addOperator(subgroupDiscoveryOperator);
			/* Connecting operators */
			excelDataReader.getOutputPorts().getPortByName("output")
					.connectTo(setRoleOperator.getInputPorts().getPortByName("example set input"));
			setRoleOperator
					.getOutputPorts()
					.getPortByName("example set output")
					.connectTo(
							discretizationOperator.getInputPorts().getPortByName(
									"example set inputs"));
			discretizationOperator
					.getOutputPorts()
					.getPortByName("example set output")
					.connectTo(
							subgroupDiscoveryOperator.getInputPorts().getPortByName("training set"));
			// Auto wire connects the last operator to result 1 automatically.
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el proceso para usar el algoritmo Subgroup Discovery");
		}
		return process;
	}

	public static Process getDecisionTreeToRules() {
		Process process = new Process();
		try {
			/* Reading from Excel */
			String fileSrc = "/Users/felix/03.TFG/DatosDeEjemplo/golf.xlsx";
			ExcelExampleSource excelDataReader;
			excelDataReader = OperatorService.createOperator(ExcelExampleSource.class);
			excelDataReader.setParameter(ExcelExampleSource.PARAMETER_EXCEL_FILE, fileSrc);

			/* Setting roles */
			ChangeAttributeRole setRoleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, "Play");
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, "label");
			String[] parameter1 = { "Outlook", "regular" };
			String[] parameter2 = { "Temperature", "regular" };
			String[] parameter3 = { "Humidity", "regular" };
			String[] parameter4 = { "Wind", "regular" };
			List<String[]> parameters = new ArrayList<String[]>();
			parameters.add(parameter1);
			parameters.add(parameter2);
			parameters.add(parameter3);
			parameters.add(parameter4);
			setRoleOperator.setListParameter("set_additional_roles", parameters);
			/* Tree to rule operator */
			Tree2RuleConverter treeToRuleOperator = OperatorService
					.createOperator(Tree2RuleConverter.class);
			/* Decision tree operator */
			DecisionTreeLearner decisionTreeOperator = OperatorService
					.createOperator(DecisionTreeLearner.class);
			/* Adding operators */
			process.getRootOperator().getSubprocess(0).addOperator(excelDataReader);
			process.getRootOperator().getSubprocess(0).addOperator(setRoleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(treeToRuleOperator);
			treeToRuleOperator.addOperator(decisionTreeOperator, 0);
			/* Connecting operators */
			excelDataReader.getOutputPorts().getPortByName("output")
					.connectTo(setRoleOperator.getInputPorts().getPortByName("example set input"));
			setRoleOperator.getOutputPorts().getPortByName("example set output")
					.connectTo(treeToRuleOperator.getInputPorts().getPortByName("training set"));
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el proceso para usar el algoritmo Tree to Rules");
		}
		return process;
	}
}
