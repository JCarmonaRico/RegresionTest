package com.acc.regresiontest.com.comparator.domains;

public class Value {

	private String expectedValue;
	private String valueFound;

	public Value(String expectedValue, String valueFound) {
		super();
		this.expectedValue = expectedValue;
		this.valueFound = valueFound;
	}

	public Value() {
		super();
	}

	/**
	 * @return the expectedValue
	 */
	public String getExpectedValue() {
		return expectedValue;
	}

	/**
	 * @param expectedValue
	 *            the expectedValue to set
	 */
	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	/**
	 * @return the valueFound
	 */
	public String getValueFound() {
		return valueFound;
	}

	/**
	 * @param valueFound
	 *            the valueFound to set
	 */
	public void setValueFound(String valueFound) {
		this.valueFound = valueFound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Value [expectedValue=");
		builder.append(expectedValue);
		builder.append(", valueFound=");
		builder.append(valueFound);
		builder.append("]");
		return builder.toString();
	}
}
