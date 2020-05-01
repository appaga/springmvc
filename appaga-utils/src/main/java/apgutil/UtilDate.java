package apgutil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilDate {
	public static final String PTN_HMS = "HHmmss";
	public static final String PTN_YMD = "yyyyMMdd";
	public static final String PTN_FULL = "yyyyMMddHHmmss";
	public static final String PTN_FULL_SSS = "yyyyMMddHHmmssSSS";
	public static final SimpleDateFormat SDF_HMS= new SimpleDateFormat(PTN_HMS, Locale.KOREAN);
	public static final SimpleDateFormat SDF_YMD= new SimpleDateFormat(PTN_YMD, Locale.KOREAN);
	public static final SimpleDateFormat SDF_FULL= new SimpleDateFormat(PTN_FULL, Locale.KOREAN);
	public static final SimpleDateFormat SDF_FULL_SSS = new SimpleDateFormat(PTN_FULL_SSS, Locale.KOREAN);
	
	/**
	 * 현재 시간을 HHmmss 형식으로 리턴
	 * @return
	 */
	public static String getNowHms() {
		return SDF_HMS.format(new Date());
	}
	/**
	 * 현재 시간을 yyyyMMdd 형식으로 리턴
	 * @return
	 */
	public static String getNowYmd() {
		return SDF_YMD.format(new Date());
	}
	/**
	 * 현재 시간을 yyyyMMddHHmmss 형식으로 리턴
	 * @return
	 */
	public static String getNowFull() {
		return SDF_FULL.format(new Date());
	}
	/**
	 * 현재 시간을 yyyyMMddHHmmssSSS 형식으로 리턴
	 * @return
	 */
	public static String getNowFullSSS() {
		return SDF_FULL_SSS.format(new Date());
	}
}
