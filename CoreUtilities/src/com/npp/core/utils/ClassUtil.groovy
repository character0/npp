package com.pabslabs.core.utils

class ClassUtil {
	static List getFieldNames(Class cls) {
		def tmpFields = cls.declaredFields.collect{it.name}
		def fields = tmpFields.findAll{!it.startsWith("\$") && !it.startsWith("__")} - ["metaClass", "log"]
		
		return fields
	}
}