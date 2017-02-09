package br.com.utils;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.dao.SSHDao;
import br.com.modelo.SSHComandoModelo;
import br.com.modelo.SSHModelo;

public class SystemTryMenus {
	public static void addMenus(PopupMenu popupMenu, TrayIcon trayIcon) {

		menuGerarCPF(popupMenu, trayIcon);
		
		menuMontar(popupMenu, trayIcon);
		
		menuClose(popupMenu, trayIcon);
	}

	private static void menuMontar(PopupMenu popupMenu, TrayIcon trayIcon) {
		SSHDao sdao = new SSHDao();
		sdao.list();
		for(SSHModelo sshModelo: sdao.getList()){
			MenuItem men = new MenuItem(sshModelo.getName());
			men.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trayIcon.displayMessage("Aguarde...", "Tentando conexão com servidor!", TrayIcon.MessageType.INFO);
				SSH ssh = new SSH(sshModelo.getHost(), sshModelo.getUserName(), sshModelo.getPassword(), sshModelo.getPort());
				for(SSHComandoModelo sshComando : sdao.getListComm()){
					if(sshComando.getIdSSHModelo() == sshModelo.getId()){
						try {
							ssh.executCommand(sshComando.getComando());
							try {
								TimeUnit.SECONDS.sleep(3);
							} catch (InterruptedException e1) {
								trayIcon.displayMessage("Erro!", "Problema na execução\nContate o desenvolvedor!", TrayIcon.MessageType.WARNING);
								e1.printStackTrace();
							}
						} catch (IOException e1) {
							trayIcon.displayMessage("Erro!", "Problema na execução\nContate o desenvolvedor!", TrayIcon.MessageType.WARNING);
							e1.printStackTrace();
						}
					}
				}
				trayIcon.displayMessage("Sucesso!", "Tarefa executada sem falhas!", TrayIcon.MessageType.INFO);
			}
			
			});
			popupMenu.add(men);
		}
	}

	private static void menuClose(PopupMenu popupMenu, TrayIcon trayIcon) {
		MenuItem close = new MenuItem("Fechar Programa");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popupMenu.add(close);
	}

	private static void menuGerarCPF(PopupMenu popupMenu, final TrayIcon trayIcon) {
		MenuItem action = new MenuItem("Gerar CPF");
		action.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cpf cpf = new Cpf();
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(new StringSelection(cpf.geraCPFFinal()), null);
				trayIcon.displayMessage("CPF Gerado!", "Basta pressionar CTRL+V para utiliza-lo!", TrayIcon.MessageType.INFO);
			}
		});
		popupMenu.add(action);
	}
}
