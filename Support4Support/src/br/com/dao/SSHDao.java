package br.com.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.com.modelo.SSHComandoModelo;
import br.com.modelo.SSHModelo;


public class SSHDao {

	private String url;
	private String driver;
	private Connection con;
	
	private List<SSHModelo> list = new ArrayList();
	private List<SSHComandoModelo> listComm = new ArrayList();
	
	/**
	 * Cria conexão com o banco a partir do config.properties
	 */
	public SSHDao() {
		//TODO melhorar essa criação de conexao a partir da properties.... ta uma bagunça
		InputStream inputStream;
		Properties prop = new Properties();
		String propFileName = "resources/config.properties";

		inputStream = SSHModelo.class.getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("Arquivo de configuração '" + propFileName + "' não encontrado");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		driver = "org.postgresql.Driver";
		url = "jdbc:postgresql://"+prop.getProperty("host")+":"+prop.getProperty("porta")+"/"+prop.getProperty("banco");
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, prop.getProperty("usuario"), prop.getProperty("pass"));

		} catch (ClassNotFoundException objErroDriver) {
			System.out.println("Erro no carregamento do driver JDBC");

		} catch (SQLException objErroConexao) {
			System.out.println("Erro na Conexao");
		}

	}

	public void list(){
		ResultSet rs = null;
		PreparedStatement stmt = null;
		list = new ArrayList();
		listComm = new ArrayList();
		String SQL = "select * from SSH join ssh_command on (ssh_command.id_ssh = ssh.id) order by id, ord";
		try {
			stmt = con.prepareStatement(SQL);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				SSHModelo sshmodelo = new SSHModelo(rs.getString("host"), rs.getString("username"), rs.getString("password"), rs.getInt("port"), rs.getString("name"), rs.getInt("id"));
				if(!list.contains(sshmodelo)){
					list.add(sshmodelo);
				}
				listComm.add(new SSHComandoModelo(rs.getInt("id_ssh"), rs.getInt("ord"), rs.getString("command")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public List<SSHModelo> getList() {
		return list;
	}

	public void setList(List<SSHModelo> list) {
		this.list = list;
	}

	public List<SSHComandoModelo> getListComm() {
		return listComm;
	}

	public void setListComm(List<SSHComandoModelo> listComm) {
		this.listComm = listComm;
	}
	
}
