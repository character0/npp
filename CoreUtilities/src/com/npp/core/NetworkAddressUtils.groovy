package com.pabslabs.core

/**
 * networkAddressUtils
 * @author cbrown
 *
 */
class NetworkAddressUtils {
	
	/**
	 * getIPv4LocalNetMask
	 * <p>
	 * Not sure who to credit this code for. I found it on pastebin from an anonymous guest.
	 * Thank you anonymous guest on pastebin.
	 * </p>
	 * @param ip
	 * @param netPrefix
	 * @return
	 * @throws Exception
	 */
    static InetAddress getIPv4LocalNetMask(InetAddress ip, int netPrefix)
	throws Exception {
		   
		   //
		   // Since this is for IPv4, it's 32 bits, so set the sign value of
		   // the int to "negative"...
		   //
		   
		   int shiftby = (1<<31)

        //
		   // For the number of bits of the prefix -1 (we already set the sign bit)
		   //
		   
		   for (int i = netPrefix-1; i > 0; i--) {
			   // Shift the sign right... Java makes the sign bit sticky on a shift...
			   // So no need to "set it back up"...
			   shiftby = (shiftby >> 1)
           }
		   
		   //
		   // Transform the resulting value in xxx.xxx.xxx.xxx format, like if
		   // it was a standard address...
		   //
		   
		   String maskString = Integer.toString((shiftby >> 24) & 255) + "." + 
		     				   Integer.toString((shiftby >> 16) & 255) + "." + 
						       Integer.toString((shiftby >> 8) & 255) + "." + 
							   Integer.toString(shiftby & 255)

        // Return the address thus created...
		   return InetAddress.getByName(maskString)

    }
}