package br.com;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.com.utils.SystemTryMenus;


public class Principal {

	public static void main(String[] args) {
		if (!SystemTray.isSupported()) {
			System.out.println("Software não suportado!\n Necessário Java 8 ;)");
			return;
		}
		SystemTray systemTray = SystemTray.getSystemTray();
		//Image image = Toolkit.getDefaultToolkit().getImage("src/images/telemarketer.png");
		Image im = null;
		try {
			im = ImageIO.read( Principal.class.getResource("/images/telemarketer.png" ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Image image = new Image(Principal.class.getResourceAsStream("/images/telemarketer.png"));
		PopupMenu trayPopupMenu = new PopupMenu();
		TrayIcon trayIcon = new TrayIcon(im, "Support4Support", trayPopupMenu);
		
		/*
		 * Add menu to SystemTry
		 */
		SystemTryMenus.addMenus(trayPopupMenu, trayIcon);
		
		trayIcon.setImageAutoSize(true);
		try {
			systemTray.add(trayIcon);
		} catch (AWTException awtException) {
			awtException.printStackTrace();
		}
	}
}
