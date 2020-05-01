package apgutil;

import java.util.List;
import java.util.Map;

public class Empty {

	/**
	 * 파라미터 객체가 널이거나 빈 객체인지 판단. 문자열인 경우 공백으로만 되어 있으면 true 이다.
	 * @param o
	 * @return true: 널이거나 빈 객체일 때.
	 */
	public static boolean isEmpty(final Object o) {
		if (o == null) {
			return true;
		}
		if ((o instanceof String) && (((String) o).trim().length() == 0)) {
			return true;
		}
		if (o instanceof Map) {
			return ((Map<?, ?>) o).isEmpty();
		}
		if (o instanceof List) {
			return ((List<?>) o).isEmpty();
		}
		if (o instanceof Object[]) {
			return (((Object[]) o).length == 0);
		}
		if (("" + o).trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 파라미터 객체가 널이거나 빈 객체인지 판단. 문자열인 경우 공백으로만 되어 있으면 false 이다.
	 * @param o
	 * @return true: 널이 아니고 비어있지 않을 때.
	 */
	public static boolean isNotEmpty(final Object o) {
		return !isEmpty(o);
	}

}
