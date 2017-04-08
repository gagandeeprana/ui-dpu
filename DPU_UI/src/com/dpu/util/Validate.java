package com.dpu.util;

public class Validate {

	public boolean validateLength(String str, int minLength, int maxLength) {
		if (str != null && str.length() > 0) {
			if (str.length() >= minLength && str.length() <= maxLength) {
				return true;
			}
		}
		return false;
	}

	
	public boolean validateEmptyness(String str) {
		if (str != null && str.length() > 0) {
			return true;
		}
		return false;
	}

	public boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public boolean isAlpha(String name) {
		for (char c : name.toCharArray()) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

}
