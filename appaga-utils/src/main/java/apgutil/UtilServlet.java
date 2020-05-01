package apgutil;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet 관련 유틸리티 클래스
 * @author TtalLaeMiDeul
 *
 */
@Slf4j
public class UtilServlet {
	/**
	 * 세션 정보를 Map으로 반환한다.
	 * @param httpSession HttpSession
	 * @return 세션정보를 담은 Map, 세션이 없으면 null
	 */
	public static Map<String, Object> getSessionInfo(final HttpSession httpSession) {
		if(log.isDebugEnabled()) {
			log.debug("httpSession={}", httpSession);
		}
		if(httpSession==null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("id", httpSession.getId());
		map.put("creationTime", new Date(httpSession.getCreationTime()));
		map.put("lastAccessedTime", new Date(httpSession.getLastAccessedTime()));
		map.put("maxInactiveInterval", httpSession.getMaxInactiveInterval());
		
		Enumeration<String> names = httpSession.getAttributeNames();
		Map<String, Object> attrs = new HashMap<>();
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			attrs.put(name, httpSession.getAttribute(name));
		}
		map.put("attrs", attrs);
		if(log.isDebugEnabled()) {
			log.debug("map={}", map);
		}
		return map;
	}
}
