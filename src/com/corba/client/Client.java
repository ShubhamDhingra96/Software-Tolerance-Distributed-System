package com.corba.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import com.corba.host.Player;

import GameApi.Admin;
import GameApi.AdminHelper;
import GameApi.Hashtable;
import GameApi.PlayerHelper;
import replicaA.interfaceIDL;
import replicaA.interfaceIDLHelper;

public class Client {

	public static void main(String[] args) throws NotFound, CannotProceed, InvalidName, IOException {

		ORB orb = ORB.init(args, null);
//		BufferedReader bufferedReader = new BufferedReader(new FileReader("ior_Asia.txt"));
//		String stringORB = bufferedReader.readLine();
//		bufferedReader.close();
		
//		org.omg.CORBA.Object reference_CORBA = orb.string_to_object(stringORB);
//		Object obj = interfaceIDLHelper.narrow(reference_CORBA);
//		if(obj instanceof Player) {
//			player = (GameApi.Player) obj;
//		}else {
//			admin=(Admin) obj;
//		}
	
		String service = "IOR:000000000000002b49444c3a6f6d672e6f72672f436f734e616d696e672f4e616d696e67436f6e746578744578743a312e30000000000001000000000000009a000102000000000d3139322e3136382e322e31360000041b00000045afabcb0000000020000f424000000001000000000000000200000008526f6f74504f41000000000d544e616d65536572766963650000000000000008000000010000000114000000000000020000000100000020000000000001000100000002050100010001002000010109000000010001010000000026000000020002";
		org.omg.CORBA.Object objRef = orb.string_to_object(service);
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		
		GameApi.Player player =(GameApi.Player) PlayerHelper.narrow(ncRef.resolve_str("Player"));
		Admin admin = (Admin) AdminHelper.narrow(ncRef.resolve_str("Admin"));
		

		// System.out.println("dsuhfosuhfus>>>" + admin.suspendAccount("Admin", "Admin", "132.0.1.2",
		// "timmyna"));

		Scanner scanner = new Scanner(System.in);
		Integer signInAs = null;
		Integer step = 0;
		while (true) {
			if (step == 0 || step == -1) {
				System.out.println("Please signin as below user\n \t 1)Player\n  \t2)Admin\n  \t3)Exit");
				System.out.println();
				signInAs = scanner.nextInt();
			}
			if (signInAs == 3) {
				step = 99;
			}

			if (signInAs > 0 && signInAs < 3 && step < 99) {
				step = 1;
				if (signInAs == 1) {
					Integer playerStep = 0;
					Integer playeraction = null;
					while (true) {
						if (playerStep == 0) {
							System.out.println("\t1)Create Account\t2)SignIn\t3)SignOut\t4Transfer Account");
							System.out.println();
							playeraction = scanner.nextInt();
						}
						if (playeraction == 1) {
							playerStep = 1;
							System.out.println("Please Enter below details to Create an Account.");
							System.out.println("Username");							
							String username = scanner.next();
							
							
							if (username.length() >= 6 && username.length() <= 15) {

								//System.out.println("Success");

							} else {
								System.out.println(
										"Username should be less than 15 characters and more than 6 characters");
								System.out.println("Create Account Again");

							}

							System.out.println("First Name");
							String fn = scanner.next();
							System.out.println("Last Name");
							String ln = scanner.next();
							System.out.println("Age");
							Integer age = scanner.nextInt();
							System.out.println("Password");
							String password = scanner.next();

							
							if (password.length() >= 6) { 
								// System.out.println("Success"); 
								
								} else {
								System.out.println("Password should be greater than or equal to atleast 6 characters");
								System.out.println("Create Account Again");
							}
 
							System.out.println("IpAddress");
							String ipAdress = scanner.next();
							String message = player.createPlayerAccount( username,fn, ln, age,password, ipAdress);								
							if (message.contains("successfully")) {
								System.out.println(message + "Press 1 to Try again Or press 2 for Back Menu.");
								System.out.println();
								Integer act = scanner.nextInt();
								if (act == 2) {
									playerStep = 0;
								}						
							} else {
								System.out.println(message);
							}

						} else if (playeraction == 2) {
							playerStep = 2;
							System.out.println("Please Enter below details to Sign in.");
							System.out.println("Username");
							String username = scanner.next();
							System.out.println("Password");
							String password = scanner.next();
							System.out.println("IpAddress");
							String ipAddress = scanner.next();
							boolean flag = player.playerSignIn(username, password, ipAddress);
							System.out.println(flag);
							if (flag) {
								System.out.println(
										"You have successfully logged in.Press 1 to logout Or press 2 for Back Menu.");
								System.out.println();
								Integer act = scanner.nextInt();
								if (act == 2) {
									playerStep = 0;
								} else if (act == 1) {
									step = 0;
								}
							}
						}

						else if (playeraction == 3) {
							playerStep = 3;							
							System.out.println("Username");
							String username = scanner.next();
							System.out.println("IpAdress");
							String IpAdress = scanner.next();
							String message = player.playerSignOut(username, IpAdress);
							System.out.println("Player Signout Successfull, Hence the status is Offline");
							Integer logut = scanner.nextInt();
							if (logut == 1) {
								scanner.close();
								System.exit(0);
							} else {
								step = 0;
							}

						}
						
						else if (playeraction == 4) {
							playerStep = 4;
							System.out.println("Username");
							String username = scanner.next();
							System.out.println("Password");
							String password = scanner.next();
							System.out.println("IpAddress");
							String ip = scanner.next();
							System.out.println("New IpAddress");
							String newIp = scanner.next();
							boolean account = player.transferAccount(username, password, ip, newIp);
							System.out.println(account);
							System.out.println("Account Transfered to New Ip Succesfully");
							
									
						}

					}

				} else {

					Integer playerStep = 0;
					Integer playeraction = null;
					while (true) {
						if (playerStep == 0) {
							System.out.println("\t1)SignIn\t2)Exit\t3Suspend Account");
							System.out.println();
							playeraction = scanner.nextInt();
						}
						if (playeraction == 1) {
							playerStep = 2;
							System.out.println("Please Enter below details to Sign In.");
							System.out.println("Username");
							String username = scanner.next();
							System.out.println("Password");
							String password = scanner.next();
							System.out.println("IpAddress");
							String ipAddress = scanner.next();
							boolean flag = admin.adminSignIn(username, password, ipAddress);
							if(flag == false) 
							System.out.println("Username and Password should be Admin only");
							if (flag) {

								System.out.println(
										"You have successfully logged in.\n Press 1 to Get Active Players List\n Press 2 for logout\n press 3 for back");
								System.out.println();
								Integer act = scanner.nextInt();
								if (act == 2) {
									playerStep = 0;
								} else if (act == 1) {
									Hashtable[] table =admin.getPlayerStatus();	
									System.out.println(playerStatus(table));									
									System.out.println(
											"You have successfully logged in.Press 1 to Signout Or press 2 for Back Menu.");
									System.out.println();
									Integer acts = scanner.nextInt();
									if (acts == 2) {
										playerStep = 0;
									} else if (acts == 1) {	
										step = 0;
									}
								} else if (act == 2) {
									step = 1;
								}
							}
						} else if (playeraction == 2) {
							step = 99;
							break;
						} else if (playeraction == 3) {
							playerStep = 3;
							System.out.println("Username");
							String username = scanner.next();
							System.out.println("Password");
							String password = scanner.next();
							if(username.equals("Admin") && password.equals("Admin")) {								
							} else {
								System.out.println("Username and Password should be Admin only");
							}							
							System.out.println("IpAddress");
							String ip = scanner.next(); 
							System.out.println("Username to Suspend");
							String user = scanner.next();
							boolean suspend = admin.suspendAccount(username, password, ip, user);							
							System.out.println("Username Suspended Successfully");

					}

				}
				}

			} else if (step == 99) {
				System.out.println("Do you want to Exit?\n \t 1)Yes\n  \t2)No");
				System.out.println();
				Integer logout = scanner.nextInt();
				if (logout == 1) {
					scanner.close();
					System.exit(0);
				} else {
					step = 0;
				}
			} else {
				System.out.println("Invalid User please try again");
				step = 0;
			}
		}
		

	}

	private static java.util.Hashtable<String, Map<String, Integer>> playerStatus(Hashtable[] table) {
		java.util.Hashtable<String, Map<String, Integer>> hashtable = new java.util.Hashtable<>();
		for (Hashtable tab : table) {
			Map<String, Integer> maps = new HashMap<String, Integer>();

			for (GameApi.Map map : tab.value) {
				maps.put(map.key, map.value);
			}

			hashtable.put(tab.key, maps);

		}

		return hashtable;
	}

}
