package com.pabslabs.core

enum RESTCustomCode {

	INVALID_FILE_EXTENSION(551), FILE_EXCEEDS_SIZE_LIMIT(552), SERVER_ERROR(553), FILE_SIZE_ON_SERVER_ZERO(554);
	
	private int status

    RESTCustomCode(int status)
	{
		this.status = status
    }

    int toInt()
	{
		return status
    }
		
}
