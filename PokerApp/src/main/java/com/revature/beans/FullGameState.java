package com.revature.beans;

import java.util.List;

public class FullGameState {
	public FullGameState() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FullGameState(CurrentHands user, List<CurrentHands> otherPlayers, String tableState) {
		super();
		this.user = user;
		this.otherPlayers = otherPlayers;
		this.tableState = tableState;
	}

	private CurrentHands user;
	private List<CurrentHands> otherPlayers;
	private String tableState;
	
	public CurrentHands getUser() {
		return user;
	}
	public void setUser(CurrentHands user) {
		this.user = user;
	}
	public List<CurrentHands> getOtherPlayers() {
		return otherPlayers;
	}
	public void setOtherPlayers(List<CurrentHands> otherPlayers) {
		this.otherPlayers = otherPlayers;
	}
	public String getTableState() {
		return tableState;
	}
	public void setTableState(String tableState) {
		this.tableState = tableState;
	}
}
