package apgutil;

public class UtilString {

	private final static boolean isEmpty(final Object o) {
		if (o == null) {
			return true;
		}
		if ((""+ o).trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 파라미터 객체가 숫자로 변경이 가능한 문자열인지 판단.
	 * 
	 * @param o
	 * @return true: 숫자로 변경이 가능할 때.
	 */
	public static boolean isNumber(final Object o) {
		if (isEmpty(o)) {
			return false;
		}
		String str = (""+o).trim();
		int beginIdx = 0;
		if (str.charAt(str.length() - 1) == '.') {
			str = str + "0";
		}
		if (str.charAt(0) == '-') {
			if (str.length() == 1) {
				return false;
			}
			beginIdx = 1;
		} else if (str.charAt(0) == '.') {
			if (str.length() == 1) {
				return false;
			}
			str = "0" + str;
		}
		int decimalPoints = 0;
		for (int i = beginIdx; i < str.length(); i++) {
			final boolean isDecimalPoint = str.charAt(i) == '.';
			if (isDecimalPoint) {
				decimalPoints++;
			}
			if (decimalPoints > 1) {
				return false;
			}
			if (!isDecimalPoint && !Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 파라미터 문자열이 true에 해당하는 문자열이면 true를 리턴한다.
	 * @param o
	 * @return true에 해당하는 문자열이면 true
	 */
	public static boolean isTrue(final Object o) {
		if (isEmpty(o)) {
			return false;
		}
		final String s = ("" + o).toLowerCase();
		if (isNumber(s)) {
			if (parseDouble(s) == 0) {
				return false;
			} else {
				return true;
			}
		}
		switch (s) {
		case "false":
			return false;
		case "null":
			return false;
		case "undefined":
			return false;
		}
		return true;
	}
	
	/**
	 * 파라미터가 double형 숫자로 파싱이 가능하면 double 변환 된 값을 리턴. 아니면 0을 리턴.
	 * 
	 * @param o
	 * @return 변환된 double형 숫자, 숫자가 아니면 0
	 */
	public static double parseDouble(final Object o) {
		return parseDouble(o, 0);
	}

	/**
	 * 파라미터가 double형 숫자로 파싱이 가능하면 double 변환 된 값을 리턴. 아니면 defaultVal를 리턴.
	 * 
	 * @param o
	 * @param defaultVal 숫자로 변환 불가 시에 리턴되는 값.
	 * @return 변환된 double형 숫자, 숫자가 아니면 defaultVal
	 */
	public static double parseDouble(final Object o, final double defaultVal) {
		if (isEmpty(o)) {
			return 0;
		}
		final String s = (""+o).trim();
		double val = defaultVal;
		try {
			val = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			val = defaultVal;
		}
		return val;
	}
	
	/**
	 * 파라미터가 int형 숫자로 파싱이 가능하면 int 변환 된 값을 리턴. 아니면 0을 리턴.
	 * 
	 * @param o
	 * @return 변환된 int형 숫자, 숫자가 아니면 0
	 */
	public static int parseInt(final Object o) {
		return parseInt(o, 0);
	}

	/**
	 * 파라미터가 int형 숫자로 파싱이 가능하면 int 변환 된 값을 리턴. 아니면 defaultVal를 리턴.
	 * 
	 * @param o
	 * @param defaultVal 숫자로 변환 불가 시에 리턴되는 값.
	 * @return 변환된 int형 숫자, 숫자가 아니면 defaultVal
	 */
	public static int parseInt(final Object o, final int defaultVal) {
		if (isEmpty(o)) {
			return 0;
		}
		final String s = (""+o).trim();
		int val = defaultVal;
		try {
			val = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			val = defaultVal;
		}
		return val;
	}

	/**
	 * 파라미터가 long형 숫자로 파싱이 가능하면 long 변환 된 값을 리턴. 아니면 0을 리턴.
	 * 
	 * @param o
	 * @return 변환된 long형 숫자, 숫자가 아니면 0
	 */
	public static int parseLong(final Object o) {
		return parseLong(o, 0);
	}

	/**
	 * 파라미터가 long형 숫자로 파싱이 가능하면 long형 변환 된 값을 리턴. 아니면 defaultVal를 리턴.
	 * 
	 * @param o
	 * @param defaultVal 숫자로 변환 불가 시에 리턴되는 값.
	 * @return 변환된 long형 숫자, 숫자가 아니면 defaultVal
	 */
	public static int parseLong(final Object o, final int defaultVal) {
		if (isEmpty(o)) {
			return 0;
		}
		final String s = (""+o).trim();
		int val = defaultVal;
		try {
			val = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			val = defaultVal;
		}
		return val;
	}

	/**
	 * 문자열에서 숫자와 소수점을 포함한 문자만 리턴.
	 * @param o
	 * @return
	 */
	public static String getDoubleString(final Object o) {
		if(isEmpty(o)) {
			return "";
		}
		final String s = (""+o).trim();
		return s.replaceAll("[^\\.0-9]", "");
	}

	/**
	 * 문자열에서 숫자만 리턴.
	 * @param o
	 * @return
	 */
	public static String getIntString(final Object o) {
		if(isEmpty(o)) {
			return "";
		}
		final String s = (""+o).trim();
		return s.replaceAll("\\D+", "");
	}
}
