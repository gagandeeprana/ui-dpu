package com.dpu.util;

public class Validate {

	public boolean validateLength(String str, int minLength, int maxLength) {
		if(str != null && str.length() > 0) {
			if(str.length() >= minLength && str.length() <= maxLength) {
				return true;
			}
		}
		return false;
	}
	
	public boolean validateEmptyness(String str) {
		if(str != null && str.length() > 0) {
			return true;
		}
		return false;
	}
}
