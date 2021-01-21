/**
 * 
 */
package com.np.utility;

/**
 * @author NPP
 *
 */
public class Bill {

	protected float powerTotal = 0;
	protected float cableTotal = 0;
	protected String h2OTotal = null;
	protected float gasTotal = 0;
	protected int household = 0;

	/**
	 * 
	 */
	public Bill() {
		this(0.0f,0.0f,"",0.0f,0);
	}

	
	public Bill(float powerTotal, float cableTotal, String h2OTotal, float gasTotal, int houseHold) {
	}

	
	public String computeTotalBills(float powerTotal, float gasTotal,
			float h2OTotal, float cableTotal) {

		String toRet = null;


		return "" + (powerTotal + gasTotal + h2OTotal + cableTotal);
	}


	public String computeITotal(float powerTotal, float gasTotal,
			float h2OTotal, float cableTotal) {

		String toRet = null;


		return "" + (powerTotal + gasTotal + h2OTotal + cableTotal)/household;
	}

}
