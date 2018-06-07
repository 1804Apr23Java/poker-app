package com.revature.beans;

public class UserDecision {
	
	public UserDecision() {
		super();
	}
	public UserDecision(String decision) {
		super();
		this.decision = decision;
	}

	private String decision;

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}
	@Override
	public String toString() {
		return "UserDecision [decision=" + decision + "]";
	}
}
