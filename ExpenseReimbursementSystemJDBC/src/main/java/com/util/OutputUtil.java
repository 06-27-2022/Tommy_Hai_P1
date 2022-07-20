package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputUtil {
	/**
	 *Logger using Logback
	 */
	public static final Logger logger = LoggerFactory.getLogger(OutputUtil.class);

	/**
	 * determines whether printing to console is allowed
	 */
	private static final boolean print=true;
	/**
	 * determines whether showing pictures is allowed
	 */
	public static final boolean display=true;
	
	/**
	 * Same as System.out.println()
	 * @param str
	 */
	public static void println() {
		print("\n");
	}
	/**
	 * Same as System.out.println()
	 * @param str
	 */
	public static void println(String str) {
		print(str+"\n");
	}
	/**
	 * Same as System.out.print()
	 * @param str
	 */

	public static void print(String str) {
		if(print)
			System.out.print(str);
	}
	
	public static void main(String[]args) {
//		LogERS.log().trace("Test");
		OutputUtil.logger.trace("test");
		OutputUtil.println("test"+1);
		OutputUtil.print("test");
		OutputUtil.println("test");
		
	}
}
