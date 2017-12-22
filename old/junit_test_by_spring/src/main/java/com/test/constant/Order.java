package com.test.constant;

public enum Order {
	DESC(0), ASC(1);
	private final int value;

	Order(int value) {
		this.value = value;
	}

	public int intValue() {
		return value;
	}

	public static Order valueOf(int value) {
		switch (value) {
			case 0:
				return DESC;
			case 1:
				return ASC;
			default:
				throw new AssertionError("Unknown value : " + value);
		}
	}

}