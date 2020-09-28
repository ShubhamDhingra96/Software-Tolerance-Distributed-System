package com.corba.host;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import GameApi.PlayerHelper;
import GameApi.PlayerPOA;


public class GameServerImpl  implements Database
{
	
	
	public UDPPeer udpPeer;
	
	private static Map<String, Integer> m_UDPLocation_PortNumber_Map; 
	
	
	public String m_Location;
	
	public static String m_finalData;
	private String l_Server_Data_NA, l_Server_Data_EU, l_Server_Data_AS;
	
	GameServerImpl(String ServerLocation, int UDPPortNumber)
	{
		m_UDPLocation_PortNumber_Map = new HashMap<String, Integer>();
		
		m_Location = ServerLocation;
		udpPeer = new UDPPeer(UDPPortNumber, this);
		udpPeer.start();
		GameServerImpl.m_UDPLocation_PortNumber_Map.put("NA", Parameters.UDP_PORT_REPLICA_LEAD_NA);
		GameServerImpl.m_UDPLocation_PortNumber_Map.put("AS", Parameters.UDP_PORT_REPLICA_LEAD_AS);
		GameServerImpl.m_UDPLocation_PortNumber_Map.put("EU", Parameters.UDP_PORT_REPLICA_LEAD_EU);
		
		
	}
	

	private String GetLocationfromAddress(String p_IPAddress)
	{
		if("132".equals(p_IPAddress.substring(0,3)))
		{
			return "NA";
		}
	
		else if("93".equals(p_IPAddress.substring(0,2)))
		{
			return "EU";
		}
	
		else if("182".equals(p_IPAddress.substring(0,3)))
		{
			return "AS";
		}
		else
		{
			return "Error: Wrong IP Address";
		}
	}


	
	
	
	
}
	


