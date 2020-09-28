package com.corba.host;

import java.io.IOException;
import java.util.Set;

import org.omg.CORBA.ORB;

import GameApi.AdminPOA;
import GameApi.Hashtable;
import GameApi.Map;

public class Admin extends AdminPOA implements Database {
	private ORB orb;

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}

	public Admin() {
		Init();
		System.out.println("Admin init");
	}

	@Override
	public boolean adminSignIn(String username, String password, String ip) {
		// TODO Auto-generated method stub
		return signInAdmin(username, password, ip);
	}

	@Override
	public Hashtable[] getPlayerStatus() {
		Hashtable[] table = new Hashtable[3];
		java.util.Hashtable<String, java.util.Map<String, Integer>> act = new java.util.Hashtable<>(actives);
		Set<String> keys = act.keySet();
		int y = 0;
		for (String key : keys) {
			Map[] m = new Map[2];
			java.util.Map<String, Integer> map = act.get(key);

			Set<String> kys = map.keySet();
			int x = 0;
			for (String ky : kys) {
				m[x] = new Map(ky, map.get(ky));
				x++;
			}
			table[y] = new Hashtable(key, m);
			y++;

		}

		return table;
	}

	@Override
	public boolean suspendAccount(String username, String password, String adminip, String usernameToSuspend) {
		
		try {
			System.out.println("supend");
			return this.suspendAccounts(username, password, adminip, usernameToSuspend);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	

}
