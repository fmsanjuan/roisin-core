package com.roisin.core.utils;

/**
 * Constantes necesarias para el desarrollo de Roisin.
 * 
 * @author F�lix Miguel Sanju�n Segovia <fmsanse@gmail.com>
 * 
 */
public interface Constants {

	String EXCEL_FORMAT = "xls";

	String EXCEL_FORMAT_2007 = "xlsx";

	String ARFF_FORMAT = "arff";

	String XRFF_FORMAT = "xrff";

	String CSV_FORMAT = "csv";

	String LABEL = "label";

	String PORT_ORIGINAL = "original";

	String PORT_EXAMPLE_SET = "exampleSet";

	String PORT_EXAMPLE_SET_TREE2R = "example set";

	String PORT_RESULT_2 = "result 2";

	/*
	 * Ripper configuration constants
	 */
	String RIPPER_SAMPLE_RATIO = "sample_ratio";

	String RIPPER_CRITERION = "criterion";

	String RIPPER_PURENESS = "pureness";

	String RIPPER_MINIMAL_PRUNE_BENEFIT = "minimal_prune_benefit";

	/*
	 * Subgroup configuration constants
	 */
	String SUBGROUP_MODE = "mode";

	String SUBGROUP_UTILITY_FUNCTION = "utility_function";

	String SUBGROUP_MIN_UTILITY = "min_utility";

	String SUBGROUP_K_BEST_RULES = "k_best_rules";

	String SUBGROUP_RULE_GENERATION = "rule_generation";

	String SUBGROUP_MAX_DEPTH = "max_depth";

	String SUBGROUP_MIN_COVERAGE = "min_coverage";

	/*
	 * Decision Tree configuration constants
	 */
	String DECISION_TREE_CRITERION = "criterion";

	String DECISION_TREE_MINIMAL_SIZE_FOR_SPLIT = "minimal_size_for_split";

	String DECISION_TREE_MINIMAL_LEAF_SIZE = "minimal_leaf_size";

	String DECISION_TREE_MINIMAL_GAIN = "minimal_gain";

	String DECISION_TREE_MAXIMAL_DEPTH = "maximal_depth";

	String DECISION_TREE_CONFIDENCE = "conficence";

	String DECISION_TREE_NUMBER_OF_PREPRUNING_ALTERNATIVES = "number_of_prepruning_alternatives";

	String DECISION_TREE_NO_PREPRUNING = "no_prepruning";

	String DECISION_TREE_NO_PRUNING = "no_pruning";

	int PROCESS_OUTPUT_INDEX = 0;

	int EXAMPLE_OUTPUT_INDEX = 1;

	String TRUE = "true";

	String DOT_SYMBOL = ".";

	int TRUNCATE_VALUE = 1000;

}
