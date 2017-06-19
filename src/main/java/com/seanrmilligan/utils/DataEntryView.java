/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seanrmilligan.utils;

/**
 *
 * @author Sean Milligan
 */
public interface DataEntryView {
		
	/*
	 * DATA ENTRY MODES
	 * Potential modes for when the view is first presented.
	 * 
	 * Depending which is selected, the view should behave
	 * differently, e.g. the title bar would change from "Add..."
	 * to "Edit..."
	 * 
	 * UNSET should be the entry mode set by the view upon its
	 * close, so that the next time the view is presented it
	 * does not mistakenly carry any behavior over from the
	 * last time it was used, whether that be to ENTER, EDIT,
	 * or DELETE information.
	 * 
	 * 
	 */
	public static enum Mode {
		UNSET,
		ENTER,
		EDIT,
		DELETE
	}
	
	public void setDataEntryMode (Mode m);
}
