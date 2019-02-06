package com.dell.easydebug.ui;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyToolWindow {

	private JButton hideToolWindowButton;
	private JPanel myToolWindowContent;
	private JTabbedPane tabbedPane;
	private JFormattedTextField rpaIpTextField;
	private JFormattedTextField rpaUserTextField;
	private JPasswordField rpaPasswordField;
	private JButton debugButton;

	private String rpaIp = "127.0.0.1";
	private String rpaUser = "root";
	private String rpaPass = "";

	MyToolWindow(ToolWindow toolWindow) {

		rpaIpTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaIp = rpaIpTextField.getText();
			}
		});
		rpaUserTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaUser = rpaUserTextField.getText();
			}
		});
		rpaPasswordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaPass = String.valueOf(rpaPasswordField.getPassword());
			}
		});

		debugButton.addActionListener(event -> excuteRemoteDebug());

	}

	private void excuteRemoteDebug() {
		System.out.println("got remote debug request with params : " +
				"RPA IP=" + rpaIp + ", User=" + rpaUser + ", Pass=" + rpaPass);

	}

	JPanel getContent() {
		return myToolWindowContent;
	}
}
