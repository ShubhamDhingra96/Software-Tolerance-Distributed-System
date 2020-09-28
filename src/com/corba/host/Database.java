package com.corba.host;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface Database {

	public String basePath = "Myserver\\";
	public static Hashtable<String, Map<String, Integer>> actives = new Hashtable<>();

	

	public default void Init() {
		
		HashMap<String, Integer> euonline = new HashMap<String, Integer>();
		euonline.put("Online", 0);
		euonline.put("Offline", 0);

		HashMap<String, Integer> naOnline = new HashMap<String, Integer>();
		naOnline.put("Online", 0);
		naOnline.put("Offline", 0);

		HashMap<String, Integer> asOnline = new HashMap<String, Integer>();
		asOnline.put("Online", 0);
		asOnline.put("Offline", 0);

		actives.put("EU", euonline);
		actives.put("NA", naOnline);
		actives.put("AS", asOnline);

	}

	public default String createPlayer(String username, String firstName, String lastName, Integer age, String password,
			String IpAdress) {

		System.out.println("Username should be less than 15 characters and more than 6 characters and "
				+ "Password should be greater than or equal to atleast 6 characters");
		String data = "\n" + username + "|" + firstName + "|" + lastName + "|" + age + "|" + password + "|" + IpAdress;

		String ip = IpAdress.split("\\.")[0];

		String path = this.basePath + ip + ".txt";

		switch (ip) {
		case "93": {
			if (this.isUserExist(username, password, path, CheckType.CREATE)) {
				return "Username already exist.";
			}

			try {
				this.logger(data, ip);
			} catch (IOException e) {

				e.printStackTrace();
			}
			String message = this.writeFile(path, data);
			this.addActiveUser("EU", "Offline");
			try {
				this.getAllOperations(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;
		}
		case "132": {
			if (this.isUserExist(username, password, path, CheckType.CREATE)) {
				return "Username already exist.";
			}
			try {
				this.logger(data, ip);
			} catch (IOException e) {

				e.printStackTrace();
			}
			String message = this.writeFile(path, data);
			this.addActiveUser("NA", "Offline");
			try {
				this.getAllOperations(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;
		}

		case "182": {
			if (this.isUserExist(username, password, path, CheckType.CREATE)) {
				return "Username already exist.";
			}

			try {
				this.logger(data, ip);
			} catch (IOException e) {

				e.printStackTrace();
			}
			String message = this.writeFile(path, data);
			this.addActiveUser("AS", "Offline");
			try {
				this.getAllOperations(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;
		}
		default: {
			throw new RuntimeException("Invalid IP address ERORRFLIFN");

		}
		}

	}

	public default Boolean signInPlayer(String username, String password, String IpAdress) {
		String ip = IpAdress.split("\\.")[0];
		String path = this.basePath + ip + ".txt";

		switch (ip) {
		case "93": {
			if (this.isUserExist(username, password, path, CheckType.SIGNIN)) {
				try {
					this.logger(username + "," + password, ip);
				} catch (IOException e) {

					e.printStackTrace();
				}
				this.addActiveUser("EU", "Online");

				return true;
			}
			return false;
		}
		case "132": {
			if (this.isUserExist(username, password, path, CheckType.SIGNIN)) {
				try {
					this.logger(username + "," + password, ip);
				} catch (IOException e) {

					e.printStackTrace();
				}
				this.addActiveUser("NA", "Online");
				return true;
			}
			return false;
		}
		case "182": {
			if (this.isUserExist(username, password, path, CheckType.SIGNIN)) {
				try {
					this.logger(username + "," + password, ip);
				} catch (IOException e) {

					e.printStackTrace();
				}
				this.addActiveUser("AS", "Online");
				return true;
			}
			return false;
		}
		default: {
			throw new RuntimeException("Invalid IP address.");

		}
		}
	}

	public default String signOutPlayer(String username, String IpAdress) {
		String ip = IpAdress.split("\\.")[0];
		String path = this.basePath + ip + ".txt";

		switch (ip) {
		case "93": {
			if (this.isUserExist(username, null, path, CheckType.SIGNOUT)) {
				try {
					this.logger(username, ip);
				} catch (IOException e) {

					e.printStackTrace();
				}

				this.logOutUser("EU", "Offline");
				return "Successfully Signout, Hence the status is Offline";
			} else {
				return "User does not exist.";
			}
		}
		case "132": {
			if (this.isUserExist(username, null, path, CheckType.SIGNOUT)) {
				try {
					this.logger(username, ip);
				} catch (IOException e) {

					e.printStackTrace();
				}
				this.logOutUser("NA", "Offline");
				return "Successfully Signout, Hence the status is Offline";
			} else {
				return "User does not exist.";
			}
		}
		case "182": {
			if (this.isUserExist(username, null, path, CheckType.SIGNOUT)) {
				try {
					this.logger(username, ip);
				} catch (IOException e) {

					e.printStackTrace();
				}
				this.logOutUser("AS", "Offline");
				return "Successfully Signout, Hence the status is Offline";
			} else {
				return "User does not exist.";
			}
		}
		default: {
			throw new RuntimeException("Invalid IP address.");

		}
		}

	}

	public default String createAdmini(String username, String firstName, String lastName, String password,
			String IpAddress) {
		String data = "\n" + username + "|" + firstName + "|" + lastName + "|" + password + "|" + IpAddress;

		String path = this.basePath + "ADMIN\\ACCOUNT.txt";
		String ip = IpAddress.split("\\.")[0];

		if (this.isUserExist("Admin", "Admin", path, CheckType.CREATE)) {

			try {
				this.logger(data, ip);
			} catch (IOException e) {

				e.printStackTrace();

			}
			return this.writeFile(path, data);

		} else
			return "Record doesnot exist";

	}

	public default Boolean signInAdmin(String username, String password, String IpAddress) {
		String data = "\n" + username + "|" + password + "|" + IpAddress;
		String path = this.basePath + "ADMIN\\ACCOUNT.txt";
		String ip = IpAddress.split("\\.")[0];

		if (username.equals("Admin") && password.equals("Admin")) {

			System.out.println("Success");

			try {
				this.logger(data, ip);
			} catch (IOException e) {

				e.printStackTrace();
			}
			this.writeFile(path, data);
			return true;

		} else {
			System.out.println("Username and Password should be Admin only");
			return false;
		}

	}

	public default String writeFile(String path, String data) {
		try {
			Files.write(Paths.get(path), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (Exception e) {
			e.printStackTrace();
			return "Oops, there is some technical issues. Please try latter.";
		}
		return "User is created successfully";
	}

	public default Boolean isUserExist(String username, String password, String path, CheckType checkType) {
		try {
			Map<String, String> users = this.setAllUsername(Files.readAllLines(Paths.get(path)));
			if ((checkType == CheckType.CREATE || checkType == CheckType.SIGNOUT) && users.containsKey(username))
				return true;
			else if (checkType == CheckType.SIGNIN && users.containsKey(username)
					&& password.equals(users.get(username))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public default Map<String, String> setAllUsername(List<String> users) {
		Map<String, String> userNames = new HashMap<>();
		Integer count = 0;
		for (String readLine : users) {
			if (!readLine.trim().isEmpty())
				if (count > 0) {
					String data[] = readLine.split("\\|");
					userNames.put(data[0], data[4]);
				}
			count++;
		}
		return userNames;
	}

	public default Hashtable<String, Map<String, Integer>> activeUsers() {
		return actives;
	}

	public default Boolean addActiveUser(String location, String status) {
		String data = "\n" + status + "|" + location;
		String path = this.basePath + "ADMIN\\ACCOUNT.txt";
		Map<String, Integer> innerData = actives.get(location);
		if (innerData != null) {

			if (status.equals("Online")) {
				Integer active = innerData.get("Online");
				if (active != null) {
					innerData.put("Online", active + 1);
					actives.put(location, innerData);
					this.writeFile(path, data);
					return true;
				}
			} else if (status.equals("Offline")) {
				Integer offline = innerData.get("Offline");
				if (offline != null) {
					innerData.put("Offline", (offline == 0) ? 1 : offline + 1);
					actives.put(location, innerData);
					return true;
				}
			}
		} else {
			innerData = new Hashtable<String, Integer>();
			if (status.equals("Online")) {

				innerData.put("Online", 1);
				actives.put(location, innerData);
				return true;

			} else if (status.equals("Offline")) {

				innerData.put("Offline", 1);
				actives.put(location, innerData);
				return true;
			}
		}

		return false;
	}

	public default String limit(String username) {
		Integer minlimit = 6;
		Integer maxlimit = 15;

		if (username.length() >= minlimit && username.length() <= maxlimit) {

			System.out.println("Success");

		} else {
			System.out.println("Username should be less than 15 characters and more than 6 characters and");
		}
		return username;

	}

	public default String limitPassword(String password) {

		if (password.length() >= 6) {
			System.out.println("Success");

		} else {
			System.out.println("Password should be greater than or equal to atleast 6 characters");
		}
		return password;
	}

	public default Hashtable<String, String> getDir(String fn) throws IOException {
		String editPath = this.basePath.substring(0, this.basePath.length() - 1);

		File folder = new File(editPath);
		File[] files = folder.listFiles();
		Hashtable<String, String> userTable = new Hashtable<>();

		for (File file : files) {

			if (file.isFile() && file.getName().startsWith(fn)) {

				List<String> lines = Files.readAllLines(Paths.get(file.getPath()));
				int counter = 0;
				for (String line : lines) {
					if (counter == 0) {
						counter++;
						continue;
					}

					String data[] = line.split("\\|");
					if (data.length > 0 && !data[0].trim().isEmpty()) {
						if (userTable.isEmpty()) {
							userTable.put(String.valueOf(data[0].charAt(0)), line);
						} else {
							if (userTable.containsKey(String.valueOf(data[0].charAt(0)))) {
								String value = userTable.get(String.valueOf(data[1].charAt(0)));
								value += ",\n" + line;
								userTable.put(String.valueOf(data[0].charAt(0)), value);
							} else {
								userTable.put(String.valueOf(data[0].charAt(0)), line);
							}

						}
					}
					counter++;
				}
			}

		}

		return userTable;
	}

	public default void logger(String logData, String IpAddress) throws IOException {

//		String ip = getClientHost().split("\\.")[0];
		String ip = IpAddress.split("\\.")[0];
		switch (ip) {
		case "93": {
			String path = this.basePath + "log\\93\\" + formatFile() + ".txt";
			System.out.println(path);
			this.log(path, "\n" + new Date() + " : " + logData.substring(2, logData.length()));
		}
			break;

		case "132": {
			String path = this.basePath + "log\\132\\" + formatFile() + ".txt";
			this.log(path, "\n" + new Date() + " : " + logData.substring(2, logData.length()));
		}
			break;

		case "182": {
			String path = this.basePath + "log\\182\\" + formatFile() + ".txt";
			this.log(path, "\n" + new Date() + " : " + logData.substring(2, logData.length()));

		}
			break;
		}

	}

	public default void log(String path, String data) throws IOException {
		Files.write(Paths.get(path), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

	}

	public default String formatFile() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public default String getAllOperations(String ipAddress) throws IOException {
		String ip = ipAddress.split("\\.")[0];
		String path = this.basePath + "Directory_Logs\\Directory_" + ip + ".txt";
		System.out.println(path);

		if (ip.equals("93")) {
			Hashtable<String, String> file = this.getDir(ip);
			FileWriter writer = new FileWriter(path, true);
			for (String key : file.keySet()) {
				writer.write(key + " : " + file.get(key) + "\n");

			}
			writer.close();

		} else if (ip.equals("132")) {
			Hashtable<String, String> file = this.getDir(ip);
			FileWriter writer = new FileWriter(path, true);
			for (String key : file.keySet()) {
				writer.write(key + " : " + file.get(key) + "\n");

			}
			writer.close();

		} else if (ip.equals("182")) {
			Hashtable<String, String> file = this.getDir(ip);
			FileWriter writer = new FileWriter(path, true);
			for (String key : file.keySet()) {
				writer.write(key + " : " + file.get(key) + "\n");

			}
			writer.close();
		}
		return "Success";

	}

	public default Boolean logOutUser(String location, String status) {
		String data = "\n" + status + "|" + location;
		String path = this.basePath + "ADMIN\\ACCOUNT.txt";
		Map<String, Integer> innerData = actives.get(location);
		if (innerData != null) {

			if (status.equals("Offline")) {
				Integer offline = innerData.get("Offline");
				if (offline != null) {
					innerData.put("Offline", (offline == 0) ? 1 : offline + 1);
					actives.put(location, innerData);

				}
				Integer active = innerData.get("Online");
				if (active != null) {
					innerData.put("Online", active - 1);
					actives.put(location, innerData);
					this.writeFile(path, data);

				}
				return true;
			}

		} else {
			innerData = new Hashtable<String, Integer>();
			if (status.equals("Offline")) {

				innerData.put("Offline", 1);
				actives.put(location, innerData);
				return true;
			}
		}

		return false;
	}

	public default boolean transferAccounts(String username, String password, String oldip, String newip)
			throws IOException {
		String ip = oldip.split("\\.")[0];
		String path = this.basePath + ip + ".txt";
		String tpath = this.basePath + newip.split("\\.")[0] + ".txt";
		boolean found = false;
		File file = new File(path);
		if (!file.exists())
			return false;
		file = new File(tpath);
		if (!file.exists())
			return false;

		switch (ip) {
		case "93": {

			List<String> lines = Files.readAllLines(Paths.get(path));
			List<String> restLines = new ArrayList<>();
			if (!lines.isEmpty()) {
				for (String line : lines) {
					String data[] = line.split("\\|");
					if (data[0].trim().equalsIgnoreCase(username) && data[4].trim().equalsIgnoreCase(password)) {
						found = true;
						data[data.length - 1] = newip;
						line = "";
						for (String d : data) {
							line += d + "|";
						}
						if (this.isUserExist(username, password, tpath, CheckType.CREATE)) {
							return false;
						}
						this.writeFile(tpath, "\n" + line.substring(0, line.length() - 1));
						if (newip.split("\\.")[0].equals("93"))
							this.addActiveUser("EU", "Offline");
						else if (newip.split("\\.")[0].equals("132"))
							this.addActiveUser("NA", "Offline");
						else if (newip.split("\\.")[0].equals("182"))
							this.addActiveUser("AS", "Offline");
					} else {
						restLines.add(line);
					}
				}
				if (!restLines.isEmpty() && found) {
					file = new File(path);
					boolean flag = file.delete();
					if (flag)
						Files.write(Paths.get(path), restLines, StandardOpenOption.CREATE_NEW);
				}
			}

		}
			break;
		case "132": {
			List<String> lines = Files.readAllLines(Paths.get(path));
			List<String> restLines = new ArrayList<>();
			if (!lines.isEmpty()) {
				for (String line : lines) {
					String data[] = line.split("\\|");
					if (data[0].trim().equalsIgnoreCase(username) && data[4].trim().equalsIgnoreCase(password)) {
						found = true;
						data[data.length - 1] = newip;
						line = "";
						for (String d : data) {
							line += d + "|";
						}
						if (this.isUserExist(username, password, tpath, CheckType.CREATE)) {
							return false;
						}

						this.writeFile(tpath, "\n" + line.substring(0, line.length() - 1));
						if (newip.split("\\.")[0].equals("93"))
							this.addActiveUser("EU", "Offline");
						else if (newip.split("\\.")[0].equals("132"))
							this.addActiveUser("NA", "Offline");
						else if (newip.split("\\.")[0].equals("182"))
							this.addActiveUser("AS", "Offline");

					} else {
						restLines.add(line);
					}
				}
				if (!restLines.isEmpty() && found) {
					file = new File(path);
					boolean flag = file.delete();
					if (flag)
						Files.write(Paths.get(path), restLines, StandardOpenOption.CREATE_NEW);
				}
			}
		}
			break;
		case "182": {
			List<String> lines = Files.readAllLines(Paths.get(path));
			List<String> restLines = new ArrayList<>();
			if (!lines.isEmpty()) {
				for (String line : lines) {
					String data[] = line.split("\\|");
					if (data[0].trim().equalsIgnoreCase(username) && data[4].trim().equalsIgnoreCase(password)) {
						found = true;
						data[data.length - 1] = newip;
						line = "";
						for (String d : data) {
							line += d + "|";
						}
						if (this.isUserExist(username, password, tpath, CheckType.CREATE)) {
							return false;
						}
						this.writeFile(tpath, "\n" + line.substring(0, line.length() - 1));
						if (newip.split("\\.")[0].equals("93"))
							this.addActiveUser("EU", "Offline");
						else if (newip.split("\\.")[0].equals("132"))
							this.addActiveUser("NA", "Offline");
						else if (newip.split("\\.")[0].equals("182"))
							this.addActiveUser("AS", "Offline");
					} else {
						restLines.add(line);
					}
				}
				if (!restLines.isEmpty() && found) {
					file = new File(path);
					boolean flag = file.delete();
					if (flag)
						Files.write(Paths.get(path), restLines, StandardOpenOption.CREATE_NEW);
				}
			}
		}
			break;
		}
		return found;
	}

	public default boolean suspendAccounts(String username, String password, String adminip, String usernameToSuspend)
			throws IOException {
		String ip = adminip.split("\\.")[0];
		String path = this.basePath + ip + ".txt";
		boolean found = false;
		File file = new File(path);
		if (!file.exists())
			return false;

		switch (ip) {
		case "93": {

			List<String> lines = Files.readAllLines(Paths.get(path));
			List<String> restLines = new ArrayList<>();
			if (!lines.isEmpty()) {
				for (String line : lines) {
					String data[] = line.split("\\|");
					if (data[0].trim().equalsIgnoreCase(usernameToSuspend)) {
						found = true;
					} else {
						restLines.add(line);
					}
				}
				if (!restLines.isEmpty() && found) {
					file = new File(path);
					boolean flag = file.delete();
					if (flag) {
						Files.write(Paths.get(path), restLines, StandardOpenOption.CREATE_NEW);
						this.addActiveUser("EU", "Offline");
					}
				}
			}

		}
			break;
		case "132": {
			List<String> lines = Files.readAllLines(Paths.get(path));
			List<String> restLines = new ArrayList<>();
			if (!lines.isEmpty()) {
				for (String line : lines) {
					String data[] = line.split("\\|");
					if (data[0].trim().equalsIgnoreCase(usernameToSuspend)) {
						found = true;
					} else {
						restLines.add(line);
					}
				}
				if (!restLines.isEmpty() && found) {
					file = new File(path);
					boolean flag = file.delete();
					if (flag) {
						Files.write(Paths.get(path), restLines, StandardOpenOption.CREATE_NEW);
						this.addActiveUser("NA", "Offline");
					}
				}
			}
		}
			break;
		case "182": {
			List<String> lines = Files.readAllLines(Paths.get(path));
			List<String> restLines = new ArrayList<>();
			if (!lines.isEmpty()) {
				for (String line : lines) {
					String data[] = line.split("\\|");
					if (data[0].trim().equalsIgnoreCase(usernameToSuspend)) {
						found = true;
					} else {
						restLines.add(line);
					}
				}
				if (!restLines.isEmpty() && found) {
					file = new File(path);
					boolean flag = file.delete();
					if (flag) {
						Files.write(Paths.get(path), restLines, StandardOpenOption.CREATE_NEW);
						this.addActiveUser("AS", "Offline");
					}
				}
			}
		}
			break;
		}
		return found;
	}
}
