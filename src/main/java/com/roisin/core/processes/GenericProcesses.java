package com.roisin.core.processes;

import java.util.List;
import java.util.SortedSet;

import org.apache.commons.lang.StringUtils;
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
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
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
	public static Process getRipper(String sourcePath, String label,
			SortedSet<Integer> deletedRows, String filterCondition,
			List<String> attributeSelection, String criterion, String sampleRatio, String pureness,
			String minimalPruneBenefit, boolean discretizeLabel) {
		Process process = null;
		try {
			process = Preprocessing.getPreprocessedData(sourcePath, deletedRows, filterCondition,
					attributeSelection);
			/* Setting roles */
			ChangeAttributeRole setRoleOperator = OperatorService
					.createOperator(ChangeAttributeRole.class);
			setRoleOperator.setParameter(ChangeAttributeRole.PARAMETER_NAME, label);
			setRoleOperator
					.setParameter(ChangeAttributeRole.PARAMETER_TARGET_ROLE, Constants.LABEL);
			/* Rule Induction */
			RuleLearner ruleInductionOperator = getRuleLearnerOperator(criterion, sampleRatio,
					pureness, minimalPruneBenefit);

			if (discretizeLabel) {
				/* Label Discretization */
				BinDiscretization discretizationOperator = OperatorService
						.createOperator(BinDiscretization.class);
				discretizationOperator.setParameter("attribute_filter_type", "single");
				discretizationOperator.setParameter("attribute", label);
				process.getRootOperator().getSubprocess(0).addOperator(discretizationOperator);

			}

			// Se añaden los operadores al proceso
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
	public static Process getSubgroupDiscoveryDiscretization(String sourcePath, String label,
			SortedSet<Integer> deletedRows, String filterCondition,
			List<String> attributeSelection, String mode, String utilityFunction,
			String minUtility, String kBestRules, String ruleGeneration, String maxDepth,
			String minCoverage) {
		Process process = null;
		try {
			process = Preprocessing.getPreprocessedData(sourcePath, deletedRows, filterCondition,
					attributeSelection);
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
			SubgroupDiscovery subgroupDiscoveryOperator = getSubgroupDiscoveryOperator(mode,
					utilityFunction, minUtility, kBestRules, ruleGeneration, maxDepth, minCoverage);
			// Adding operators
			process.getRootOperator().getSubprocess(0).addOperator(discretizationOperator);
			process.getRootOperator().getSubprocess(0).addOperator(setRoleOperator);
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
	public static Process getDecisionTreeToRules(String sourcePath, String label,
			SortedSet<Integer> deletedRows, String filterCondition,
			List<String> attributeSelection, String criterion, String minimalSizeForSplit,
			String minimalLeafSize, String minimalGain, String maximalDepth, String confidence,
			String numberOfPrepruningAlt, String noPrepruning, String noPruning,
			boolean discretizeLabel) {
		Process process = null;
		try {
			process = Preprocessing.getPreprocessedData(sourcePath, deletedRows, filterCondition,
					attributeSelection);
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
			DecisionTreeLearner decisionTreeOperator = getDecisionTreeLearnerOperator(criterion,
					minimalSizeForSplit, minimalLeafSize, minimalGain, maximalDepth, confidence,
					numberOfPrepruningAlt, noPrepruning, noPruning);

			if (discretizeLabel) {
				/* Label Discretization */
				BinDiscretization discretizationOperator = OperatorService
						.createOperator(BinDiscretization.class);
				discretizationOperator.setParameter("attribute_filter_type", "single");
				discretizationOperator.setParameter("attribute", label);
				process.getRootOperator().getSubprocess(0).addOperator(discretizationOperator);

			}
			// Adding operators
			process.getRootOperator().getSubprocess(0).addOperator(setRoleOperator);
			process.getRootOperator().getSubprocess(0).addOperator(treeToRuleOperator);
			treeToRuleOperator.getSubprocess(0).addOperator(decisionTreeOperator, 0);
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

	/**
	 * Este método devuelve el operador RuleLearner (Ripper) según los
	 * parámetros indicados.
	 * 
	 * @param criterion
	 * @param sampleRatio
	 * @param pureness
	 * @param minimalPruneBenefit
	 * @return
	 * @throws OperatorCreationException
	 */
	public static RuleLearner getRuleLearnerOperator(String criterion, String sampleRatio,
			String pureness, String minimalPruneBenefit) throws OperatorCreationException {
		RuleLearner ruleInductionOperator = OperatorService.createOperator(RuleLearner.class);
		// Configuración de rule induction operator
		// Sample ratio
		sampleRatio = StringUtils.isBlank(sampleRatio) ? "1.0" : sampleRatio;
		ruleInductionOperator.setParameter(Constants.RIPPER_SAMPLE_RATIO, sampleRatio);
		// Pureness
		if (!StringUtils.isBlank(pureness)) {
			ruleInductionOperator.setParameter(Constants.RIPPER_PURENESS, pureness);
		}
		// Criterion
		if (!StringUtils.isBlank(criterion)) {
			ruleInductionOperator.setParameter(Constants.RIPPER_CRITERION, criterion);
		}
		// Minimal Prune Benefit
		if (!StringUtils.isBlank(minimalPruneBenefit)) {
			ruleInductionOperator.setParameter(Constants.RIPPER_MINIMAL_PRUNE_BENEFIT,
					minimalPruneBenefit);
		}
		return ruleInductionOperator;
	}

	public static SubgroupDiscovery getSubgroupDiscoveryOperator(String mode,
			String utilityFunction, String minUtility, String kBestRules, String ruleGeneration,
			String maxDepth, String minCoverage) throws OperatorCreationException {
		SubgroupDiscovery subgroupDiscoveryOperator = OperatorService
				.createOperator(SubgroupDiscovery.class);
		/* Subgroup discovery configuration */
		// Mode
		if (!StringUtils.isBlank(mode)) {
			subgroupDiscoveryOperator.setParameter(Constants.SUBGROUP_MODE, mode);
		}
		// Utility Function
		if (!StringUtils.isBlank(utilityFunction)) {
			subgroupDiscoveryOperator.setParameter(Constants.SUBGROUP_UTILITY_FUNCTION,
					utilityFunction);
		}
		// Min utility
		if (!StringUtils.isBlank(minUtility)) {
			subgroupDiscoveryOperator.setParameter(Constants.SUBGROUP_MIN_UTILITY, minUtility);
		}
		// Rule Generation
		if (!StringUtils.isBlank(ruleGeneration)) {
			subgroupDiscoveryOperator.setParameter(Constants.SUBGROUP_RULE_GENERATION,
					ruleGeneration);
		}
		// K best rule
		if (!StringUtils.isBlank(mode)) {
			subgroupDiscoveryOperator.setParameter(Constants.SUBGROUP_K_BEST_RULES, kBestRules);
		}
		// Max Depth
		if (!StringUtils.isBlank(maxDepth)) {
			subgroupDiscoveryOperator.setParameter(Constants.SUBGROUP_MAX_DEPTH, maxDepth);
		}
		// Min Coverage
		if (!StringUtils.isBlank(minCoverage)) {
			subgroupDiscoveryOperator.setParameter(Constants.SUBGROUP_MIN_COVERAGE, minCoverage);
		}
		return subgroupDiscoveryOperator;
	}

	/**
	 * Este método devuelve el operador DecisionTreeLearner configurado. Se
	 * comprueba para todos los casos si el parámetro es blanco o nulo.
	 * 
	 * @param criterion
	 * @param minimalSizeForSplit
	 * @param minimalLeafSize
	 * @param minimalGain
	 * @param maximalDepth
	 * @param confidence
	 * @param numberOfPrepruningAlt
	 * @param noPrepruning
	 * @param noPruning
	 * @return
	 * @throws OperatorCreationException
	 */
	public static DecisionTreeLearner getDecisionTreeLearnerOperator(String criterion,
			String minimalSizeForSplit, String minimalLeafSize, String minimalGain,
			String maximalDepth, String confidence, String numberOfPrepruningAlt,
			String noPrepruning, String noPruning) throws OperatorCreationException {
		DecisionTreeLearner decisionTreeOperator = OperatorService
				.createOperator(DecisionTreeLearner.class);
		/* Configuración de DecisionTreeLearner */
		// Criterion
		if (!StringUtils.isBlank(criterion)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_CRITERION, criterion);
		}
		// Minimal Size for Split
		if (!StringUtils.isBlank(minimalSizeForSplit)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_MINIMAL_SIZE_FOR_SPLIT,
					minimalSizeForSplit);
		}
		// Minimal Leaf Size
		if (!StringUtils.isBlank(minimalLeafSize)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_MINIMAL_LEAF_SIZE,
					minimalLeafSize);
		}
		// Minimal gain
		if (!StringUtils.isBlank(minimalGain)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_MINIMAL_GAIN, minimalGain);
		}
		// Maximal Depth
		if (!StringUtils.isBlank(maximalDepth)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_MAXIMAL_DEPTH, maximalDepth);
		}
		// Confidence
		if (!StringUtils.isBlank(confidence)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_CONFIDENCE, confidence);
		}
		// Number of prepuning alternatives
		if (!StringUtils.isBlank(numberOfPrepruningAlt)) {
			decisionTreeOperator.setParameter(
					Constants.DECISION_TREE_NUMBER_OF_PREPRUNING_ALTERNATIVES,
					numberOfPrepruningAlt);
		}
		// No prepruning
		if (!StringUtils.isBlank(noPrepruning)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_NO_PREPRUNING, noPrepruning);
		}
		// No pruning
		if (!StringUtils.isBlank(noPruning)) {
			decisionTreeOperator.setParameter(Constants.DECISION_TREE_NO_PRUNING, noPruning);
		}
		return decisionTreeOperator;
	}
}
