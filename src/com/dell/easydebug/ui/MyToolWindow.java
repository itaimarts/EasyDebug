package com.dell.easydebug.ui;

import com.dell.easydebug.model.JarReplacementService;
import com.dell.easydebug.model.RemoteDebugService;
import com.dell.easydebug.model.RpaDetails;
import com.dell.easydebug.model.RpcsDetails;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyToolWindow {

	private RemoteDebugService debugService;
	private JarReplacementService jarReplacementService;

	private JPanel myToolWindowContent;
	private JTabbedPane tabbedPane;

	private JFormattedTextField rpaIpTextFieldForRemoteDebug;
	private JFormattedTextField rpaUserTextFieldForRemoteDebug;
	private JPasswordField rpaPasswordFieldForRemoteDebug;
	private JButton debugButton;

	private JFormattedTextField rpaIpTextFieldForJarReplacement;
	private JFormattedTextField rpaUserTextFieldForJarReplacement;
	private JPasswordField rpaPasswordFieldForJarReplacement;
	private JSpinner rpcsNumberSpinnerForJarReplacement;
	private JFormattedTextField rpcsUserTextFieldForJarReplacement;
	private JPasswordField rpcsPasswordFieldForJarReplacement;
	private JButton replaceJarButton;

	private RpaDetails rpaDetails = new RpaDetails();
	private RpcsDetails rpcsDetails = new RpcsDetails();

	MyToolWindow(ToolWindow toolWindow) {

		rpaIpTextFieldForRemoteDebug.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaIp(rpaIpTextFieldForRemoteDebug.getText());
			}
		});
		rpaUserTextFieldForRemoteDebug.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaUser(rpaUserTextFieldForRemoteDebug.getText());
			}
		});
		rpaPasswordFieldForRemoteDebug.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaPass(rpaPasswordFieldForRemoteDebug.getPassword());
			}
		});

		debugButton.addActionListener(event -> executeRemoteDebug());

		rpaIpTextFieldForJarReplacement.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaIp(rpaIpTextFieldForJarReplacement.getText());
			}
		});
		rpaUserTextFieldForJarReplacement.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaUser(rpaUserTextFieldForJarReplacement.getText());
			}
		});

		rpcsNumberSpinnerForJarReplacement.addPropertyChangeListener(event -> rpcsDetails.setRpcsNum((Integer) rpcsNumberSpinnerForJarReplacement.getValue()));

		rpaPasswordFieldForJarReplacement.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaPass(rpaPasswordFieldForJarReplacement.getPassword());
			}
		});

		rpcsUserTextFieldForJarReplacement.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpcsDetails.setRpcsUser(rpcsUserTextFieldForJarReplacement.getText());
			}
		});

		rpcsPasswordFieldForJarReplacement.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpcsDetails.setRpcsPass(rpcsPasswordFieldForJarReplacement.getPassword());
			}
		});


		replaceJarButton.addActionListener(event -> executeJarReplacement());
	}

	private void executeJarReplacement() {
		System.out.println("got Jar replacement request with params : " +
				"RPA IP=" + rpaDetails.getRpaIp() +
				", User=" + rpaDetails.getRpaUser() +
				", Pass=" + rpaDetails.getRpaPass() +
				", Rpcs Num=" + rpcsDetails.getRpcsNum() +
				", User=" + rpcsDetails.getRpcsUser() +
				", Pass=" + rpcsDetails.getRpcsPass());

		jarReplacementService.replaceJar(rpaDetails, rpcsDetails);
	}

	private void executeRemoteDebug() {
		System.out.println("got remote debug request with params : " +
				"RPA IP=" + rpaDetails.getRpaIp() +
				", User=" + rpaDetails.getRpaUser() +
				", Pass=" + rpaDetails.getRpaPass());

		debugService.debug(rpaDetails);
	}

	JPanel getContent() {
		return myToolWindowContent;
	}
}
