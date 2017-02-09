package br.com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSH {
	private Connection connection;

	/**
	 * IP, user, password, port
	 * Create connection with server
	 * @param host
	 * @param userName
	 * @param password
	 * @param port
	 */
	public SSH(String host, String userName, String password, int port) {
		connection = new Connection(host, port);
		try {
			connection.connect();
			connection.authenticateWithPassword(userName, password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * command example:
	 * \tomcat_folder/bin/./startup.sh
	 * @param command
	 * @return
	 * @throws IOException
	 */
	public List<String> executCommand(String command) throws IOException {
        List<String> result = new LinkedList<>();
        Session session = null;

        try {
            session = this.connection.openSession();
            session.execCommand(command);
            InputStream stdout = new StreamGobbler(session.getStdout());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(stdout))) {
                String line = br.readLine();
                while (line != null) {
                    result.add(line);
                    line = br.readLine();
                    System.out.println(result);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
