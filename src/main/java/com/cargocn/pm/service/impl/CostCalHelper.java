package com.cargocn.pm.service.impl;

import java.math.BigDecimal;

public class CostCalHelper {

	public static BigDecimal cal(BigDecimal cost, BigDecimal hour) {
		if (cost == null) {
			return null;
		}
		if (hour == null) {
			return BigDecimal.ZERO;
		}
		return cost.multiply(hour);
	}
}
