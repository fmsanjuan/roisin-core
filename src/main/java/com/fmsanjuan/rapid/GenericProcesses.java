package com.fmsanjuan.rapid;

import java.util.ArrayList;
import java.util.List;

import com.rapidminer.Process;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.io.ExcelExampleSource;
import com.rapidminer.operator.learner.rules.RuleLearner;
import com.rapidminer.operator.preprocessing.filter.ChangeAttributeRole;
import com.rapidminer.tools.OperatorService;

public class GenericProcesses {

	public static Process getRipper() {
		Process process = new Process();
		try {
			/* Reading from Excel */
			String fileSrc = "/Users/felix/03.TFG/DatosDeEjemplo/golf.xlsx";
			Operator excelDataReader = OperatorService
					.createOperator(ExcelExampleSource.class);
			// OperatorDescription operatorDescription = new
			// OperatorDescription(fullyQualifiedGroupKey, key, clazz,
			// classLoader, iconName, provider)
			excelDataReader.setParameter(
					ExcelExampleSource.PARAMETER_EXCEL_FILE, fileSrc);

			/* Setting roles */
			Operator setRuleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRuleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME,
					"Play");
			setRuleOperator.setParameter(
					ChangeAttributeRole.PARAMETER_TARGET_ROLE, "label");
			String[] parameter1 = { "Outlook", "regular" };
			String[] parameter2 = { "Temperature", "regular" };
			String[] parameter3 = { "Humidity", "regular" };
			String[] parameter4 = { "Wind", "regular" };
			List<String[]> parameters = new ArrayList<String[]>();
			parameters.add(parameter1);
			parameters.add(parameter2);
			parameters.add(parameter3);
			parameters.add(parameter4);

			setRuleOperator
					.setListParameter("set_additional_roles", parameters);

			/* Rule Induction */
			Operator ruleInductionOperator = OperatorService
					.createOperator(RuleLearner.class);

			/* Adding operators */
			process.getRootOperator().getSubprocess(0)
					.addOperator(excelDataReader);
			process.getRootOperator().getSubprocess(0)
					.addOperator(setRuleOperator);
			process.getRootOperator().getSubprocess(0)
					.addOperator(ruleInductionOperator);

			excelDataReader
					.getOutputPorts()
					.getPortByName("output")
					.connectTo(
							setRuleOperator.getInputPorts().getPortByName(
									"example set input"));
			setRuleOperator
					.getOutputPorts()
					.getPortByName("example set output")
					.connectTo(
							ruleInductionOperator.getInputPorts()
									.getPortByName("training set"));
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		}
		return process;
	}

}
