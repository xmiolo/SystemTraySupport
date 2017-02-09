package br.com.modelo;

public class SSHComandoModelo {
	private int idSSHModelo, ordem;
	private String comando;
	
	
	public SSHComandoModelo(int idSSHModelo, int ordem, String comando) {
		this.idSSHModelo = idSSHModelo;
		this.ordem = ordem;
		this.comando = comando;
	}
	public int getIdSSHModelo() {
		return idSSHModelo;
	}
	public void setIdSSHModelo(int idSSHModelo) {
		this.idSSHModelo = idSSHModelo;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
}
