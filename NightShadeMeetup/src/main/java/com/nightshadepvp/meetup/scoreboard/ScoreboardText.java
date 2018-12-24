package com.nightshadepvp.meetup.scoreboard;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

public class ScoreboardText {
	
	private String text;
	private String extendedText;
	
	private boolean extended = false;
	
	public ScoreboardText(String text) {
		Preconditions.checkState(text.length() <= 32, "Text can't be longer than 32 chars.");
		
		if (text.length() > 16) {
			this.extended = true;
			
			this.extendedText = text.substring(16, text.length());
			this.text = text.substring(0, 16);
		} else { 
			this.text = text;
			this.extendedText = StringUtils.EMPTY;
		}
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getExtendedText() {
		return this.extendedText;
	}
	
	public boolean isExtended() {
		return this.extended;
	}

}