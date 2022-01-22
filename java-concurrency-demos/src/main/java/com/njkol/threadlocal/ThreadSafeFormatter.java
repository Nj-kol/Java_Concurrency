package com.njkol.threadlocal;

import java.text.SimpleDateFormat;

/**
 * Holder pattern for making an object thread safe
 * 
 * @author Nilanjan Sarkar
 *
 */
public class ThreadSafeFormatter {

	// SimpleDateFormat is not thread-safe, so give one to each thread
	public static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>() {
		
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
		
		@Override
		public SimpleDateFormat get() {
			return super.get();
		}
	};
}
