package com.acc.regresiontest.com.comparator.domains;

import java.util.Arrays;

public class Difference {

	private String[] tree;
	private Value values;

	public Difference(String[] tree, Value values) {
		super();
		this.tree = tree;
		this.values = values;
	}

	public Difference() {
		super();
	}

	/**
	 * @return the tree
	 */
	public String[] getTree() {
		return tree;
	}

	/**
	 * @param tree
	 *            the tree to set
	 */
	public void setTree(String[] tree) {
		this.tree = tree;
	}

	/**
	 * @return the values
	 */
	public Value getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the values to set
	 */
	public void setValues(Value values) {
		this.values = values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Difference [tree=");
		builder.append(Arrays.toString(tree));
		builder.append(", values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}

}
