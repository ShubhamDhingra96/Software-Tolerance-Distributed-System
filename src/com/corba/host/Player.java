package com.corba.host;

import java.io.IOException;

import org.omg.CORBA.ORB;

import GameApi.PlayerPOA;

public class Player extends PlayerPOA implements Database {
	private ORB orb;

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	@Override
	public String createPlayerAccount(String username, String firstName, String lastName, int age, String password,
			String ip) {
		return this.createPlayer(username, firstName, lastName, age, password, ip);
	}

	@Override
	public boolean playerSignIn(String username, String password, String ip) {
		return this.signInPlayer(username, password, ip);
	}

	@Override
	public String playerSignOut(String username, String ip) {
		return this.signOutPlayer(username, ip);
	}

	@Override
	public boolean transferAccount(String username, String password, String oldip, String newip) {
		// TODO Auto-generated method stub
		try {
			return this.transferAccounts(username, password, oldip, newip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	
	

}
