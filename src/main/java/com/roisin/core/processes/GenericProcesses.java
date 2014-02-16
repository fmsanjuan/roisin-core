package com.roisin.core.processes;

import java.util.List;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import com.rapidminer.Process;
import com.rapidminer.operator.OperatorCreationException;
import com.rapidminer.operator.learner.meta.Tree2RuleConverter;
import com.rapidminer.operator.learner.rules.RuleLearner;
import com.rapidminer.operator.learner.subgroups.SubgroupDiscovery;
import com.rapidminer.operator.learner.tree.DecisionTreeLearner;
import com.rapidminer.operator.ports.metadata.CompatibilityLevel;
import com.rapidminer.operator.preprocessing.discretization.BinDiscretization;
import com.rapidminer.operator.preprocessing.filter.ChangeAttributeRole;
import com.rapidminer.tools.OperatorService;
import com.roisin.core.utils.Constants;

import exception.RoisinException;

/**
 * Implementación de los métodos necesarios para obtener los procesos Ripper,
 * Tree2Rules y Subgroup Discovery.
 * 
 * @author Félix Miguel Sanjuán Segovia <fmsanse@gmail.com>
 * 
 */
public class GenericProcesses {

	/**
	 * Log
	 */
	private static Logger log = Logger.getLogger(GenericProcesses.class);

	/**
	 * Este método devueve el proceso que contiene el algoritmo Ripper para un
	 * conjunto de datos dado. Se debe indicar fuente del conjunto de datos y
	 * formato. También permite filtrado por filas o columnas.
	 * 
	 * @param sourceFormat
	 *            formato de conjunto de datos
	 * @param sourcePath
	 *            lugar donde se encuentra el conjunto de datos
	 * @param label
	 *            clase
	 * @param deletedRows
	 *            filas que serán borradas
	 * @param filterCondition
	 *            condición para el filtrado por filas
	 * @param attributeSelection
	 *            condición para el filtrado por columnas
	 * @return process proceso
	 */
	public static Process getRipper(String sourceFormat, String sourcePath, String label,
			SortedSet<Integer> deletedRows, String filterCondition, List<String> attributeSelection) {
		Process process = null;
		try {
			process = Preprocessing.getPreprocessedData(sourceFormat, sourcePath, deletedRows,
					filterCondition, attributeSelection, label);
			/* Setting roles */
			ChangeAttributeRole setRoleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, label);
			setRoleOperator
					.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, Constants.LABEL);
			/* Rule Induction */
			RuleLearner ruleInductionOperator = OperatorService.createOperator(RuleLearner.class);
			ruleInductionOperator.setParameter(Constants.RIPPER_SAMPLE_RATIO, "1.0");
			process.getRootOperator().getSubprocess(0).addOperator(setRoleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(ruleInductionOperator);
			// Es obligatorio devolver el conjunto de datos de ejemplo como un
			// resultado.
			ruleInductionOperator
					.getOutputPorts()
					.getPortByName(Constants.PORT_EXAMPLE_SET)
					.connectTo(
							process.getRootOperator().getSubprocess(0).getInnerSinks()
									.getPortByName(Constants.PORT_RESULT_2));
			// Auto wire connects the last operator to result 1 automatically.
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el proceso para usar el algoritmo Ripper");
		} catch (RoisinException e) {
			log.error("Error en la obtención del proceso para el algoritmo ripper => " + e);
		}
		return process;
	}

	/**
	 * Este método devueve el proceso que contiene el algoritmo
	 * SubgroupDiscovery para un conjunto de datos dado. Se debe indicar fuente
	 * del conjunto de datos y formato. También permite filtrado por filas o
	 * columnas.
	 * 
	 * @param sourceFormat
	 *            formato de conjunto de datos
	 * @param sourcePath
	 *            lugar donde se encuentra el conjunto de datos
	 * @param label
	 *            clase
	 * @param deletedRows
	 *            filas que serán borradas
	 * @param filterCondition
	 *            condición para el filtrado por filas
	 * @param attributeSelection
	 *            condición para el filtrado por columnas
	 * @return process proceso
	 */
	public static Process getSubgroupDiscoveryDiscretization(String sourceFormat,
			String sourcePath, String label, SortedSet<Integer> deletedRows,
			String filterCondition, List<String> attributeSelection) {
		Process process = null;
		try {
			process = Preprocessing.getPreprocessedData(sourceFormat, sourcePath, deletedRows,
					filterCondition, attributeSelection, label);
			/* Setting roles */
			ChangeAttributeRole setRoleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, label);
			setRoleOperator
					.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, Constants.LABEL);
			/* Discretization */
			BinDiscretization discretizationOperator = OperatorService
					.createOperator(BinDiscretization.class);
			/* Subgroup discovery */
			SubgroupDiscovery subgroupDiscoveryOperator = OperatorService
					.createOperator(SubgroupDiscovery.class);
			// Adding operators
			process.getRootOperator().getSubprocess(0).addOperator(setRoleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(discretizationOperator);
			process.getRootOperator().getSubprocess(0).addOperator(subgroupDiscoveryOperator);
			// Es obligatorio devolver el conjunto de datos de ejemplo como un
			// resultado.
			subgroupDiscoveryOperator
					.getOutputPorts()
					.getPortByName(Constants.PORT_EXAMPLE_SET)
					.connectTo(
							process.getRootOperator().getSubprocess(0).getInnerSinks()
									.getPortByName(Constants.PORT_RESULT_2));
			// Auto wire connects the last operator to result 1 automatically.
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el proceso para usar el algoritmo Subgroup Discovery");
		} catch (RoisinException e) {
			log.error("Error en la obtención del proceso para el algoritmo Subgroup Discovery => "
					+ e);
		}
		return process;
	}

	/**
	 * Este método devueve el proceso que contiene el algoritmo TreeToRules para
	 * un conjunto de datos dado. Se debe indicar fuente del conjunto de datos y
	 * formato. También permite filtrado por filas o columnas.
	 * 
	 * @param sourceFormat
	 *            formato de conjunto de datos
	 * @param sourcePath
	 *            lugar donde se encuentra el conjunto de datos
	 * @param label
	 *            clase
	 * @param deletedRows
	 *            filas que serán borradas
	 * @param filterCondition
	 *            condición para el filtrado por filas
	 * @param attributeSelection
	 *            condición para el filtrado por columnas
	 * @return process proceso
	 */
	public static Process getDecisionTreeToRules(String sourceFormat, String sourcePath,
			String label, SortedSet<Integer> deletedRows, String filterCondition,
			List<String> attributeSelection) {
		Process process = null;
		try {
			process = Preprocessing.getPreprocessedData(sourceFormat, sourcePath, deletedRows,
					filterCondition, attributeSelection, label);
			/* Setting roles */
			ChangeAttributeRole setRoleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, label);
			setRoleOperator
					.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, Constants.LABEL);
			/* Tree to rule operator */
			Tree2RuleConverter treeToRuleOperator = OperatorService
					.createOperator(Tree2RuleConverter.class);
			/* Decision tree operator */
			DecisionTreeLearner decisionTreeOperator = OperatorService
					.createOperator(DecisionTreeLearner.class);
			process.getRootOperator().getSubprocess(0).addOperator(setRoleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(treeToRuleOperator);
			treeToRuleOperator.addOperator(decisionTreeOperator, 0);
			// Es obligatorio devolver el conjunto de datos de ejemplo como un
			// resultado.
			treeToRuleOperator
					.getOutputPorts()
					.getPortByName(Constants.PORT_EXAMPLE_SET_TREE2R)
					.connectTo(
							process.getRootOperator().getSubprocess(0).getInnerSinks()
									.getPortByName(Constants.PORT_RESULT_2));
			// Auto wire connects the last operator to result 1 automatically.
			process.getRootOperator().getSubprocess(0)
					.autoWire(CompatibilityLevel.VERSION_5, true, true);
		} catch (OperatorCreationException e) {
			log.error("No ha sido posible crear el proceso para usar el algoritmo Subgroup Discovery");
		} catch (RoisinException e) {
			log.error("Error en la obtención del proceso para el algoritmo Subgroup Discovery => "
					+ e);
		}
		return process;
	}
}
