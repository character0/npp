package com.pabslabs.core.utils

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class ListUtil {
	private static Log log = LogFactory.getLog("util.ListUtil")
	
	// Finds the first item in the list given the closure, removes it from the list, and returns it.
	static findAndPop(List list, Closure findClosure) {
		if (!list) {
			return null
		}
		
		def iter = list.iterator()
		while (iter.hasNext()) {
			def obj = iter.next()
			if (findClosure(obj)) {
				iter.remove()
				return obj
			}
		}
		
		return null
	}
	
	// Pops the first item of the list, removes it from the list, and returns it.
	static popFirstItem(List list) {
		if (!list) {
			return null
		}
		
		def obj = null
		
		def iter = list.iterator()
		if (iter.hasNext()) {
			obj = iter.next()
			iter.remove()
		}
		
		return obj
	}
}