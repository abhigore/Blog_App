package com.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	String fieldname;
	String resourcename;
	long fieldvalue;
	
	public ResourceNotFoundException(String fieldname, String resourcename, long fieldvalue) {
		super(String.format("%s not found with %s: %s",resourcename,fieldname,fieldvalue));
		this.fieldname = fieldname;
		this.resourcename = resourcename;
		this.fieldvalue = fieldvalue;
	}
	
	

}
