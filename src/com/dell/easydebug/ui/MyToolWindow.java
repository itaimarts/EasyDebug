package com.dell.easydebug.ui;

import com.dell.easydebug.model.*;
import com.dell.easydebug.remotedebug.ConfigureRpaToDebug;
import com.dell.easydebug.reset.version.ResetVersion;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.popup.PopupFactoryImpl;
import com.jcraft.jsch.JSchException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyToolWindow {

	private RemoteDebugService debugService = new ConfigureRpaToDebug();
	private JarReplacementService jarReplacementService;
	private GetVersionsService versionsService;

	private JPanel myToolWindowContent;
	private JTabbedPane tabbedPane;

	private JFormattedTextField rpaIpTextFieldForRemoteDebug;
	private JFormattedTextField rpaUserTextFieldForRemoteDebug;
	private JPasswordField rpaPasswordFieldForRemoteDebug;
	private JComboBox<String> precessComboBox;
	private JButton debugButton;

	private JFormattedTextField rpaIpTextFieldForJarReplacement;
	private JFormattedTextField rpaUserTextFieldForJarReplacement;
	private JPasswordField rpaPasswordFieldForJarReplacement;
	private JSpinner rpcsNumberSpinnerForJarReplacement;
	private JFormattedTextField rpcsUserTextFieldForJarReplacement;
	private JPasswordField rpcsPasswordFieldForJarReplacement;
	private JButton replaceJarButton;

	private JFormattedTextField rpaIpTextFieldForVersionReset;
	private JFormattedTextField rpaUserTextFieldForVersionReset;
	private JPasswordField rpaPasswordFieldForVersionReset;
	private JFormattedTextField rpaVersionTextField;
	private JFormattedTextField branchVersionTextField;
	private JButton getVersionsButton;
	private JButton resetVersionButton;
	private JSpinner rpcsNumberSpinnerForVersionReset;
	private JFormattedTextField rpcsUserTextFieldForVersionReset;
	private JPasswordField rpcsPasswordFieldForVersionReset;

	PopupFactoryImpl popupFactory = new PopupFactoryImpl();

	public static RpaDetails rpaDetails = new RpaDetails();
	public static RpcsDetails rpcsDetails = new RpcsDetails();

	MyToolWindow(ToolWindow toolWindow) {

		setEventsForRemoteDebugging();

		setEventsForJarReplacement();

		setEventForVersionReset();

		setEventForTabSwitch();

		versionsService = new ResetVersion();

	}

	private void setEventForTabSwitch() {
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				setRpaDetailsOnAllTabs();
				setRpcsDetailsOnAllTabs();
			}
		});
	}

	private void setRpcsDetailsOnAllTabs() {
		rpcsNumberSpinnerForJarReplacement.setValue(rpcsDetails.getRpcsNum());
		rpcsNumberSpinnerForVersionReset.setValue(rpcsDetails.getRpcsNum());

		rpcsUserTextFieldForJarReplacement.setText(rpcsDetails.getRpcsUser());
		rpcsUserTextFieldForVersionReset.setText(rpcsDetails.getRpcsUser());

		rpcsPasswordFieldForJarReplacement.setText(rpcsDetails.getRpcsPass());
		rpcsPasswordFieldForVersionReset.setText(rpcsDetails.getRpcsPass());
	}

	private void setRpaDetailsOnAllTabs() {
		rpaIpTextFieldForJarReplacement.setText(rpaDetails.getRpaIp());
		rpaIpTextFieldForRemoteDebug.setText(rpaDetails.getRpaIp());
		rpaIpTextFieldForVersionReset.setText(rpaDetails.getRpaIp());

		rpaUserTextFieldForJarReplacement.setText(rpaDetails.getRpaUser());
		rpaUserTextFieldForRemoteDebug.setText(rpaDetails.getRpaUser());
		rpaUserTextFieldForVersionReset.setText(rpaDetails.getRpaUser());

		rpaPasswordFieldForJarReplacement.setText(rpaDetails.getRpaPass());
		rpaPasswordFieldForRemoteDebug.setText(rpaDetails.getRpaPass());
		rpaPasswordFieldForVersionReset.setText(rpaDetails.getRpaPass());
	}

	private void setEventForVersionReset() {
		rpaIpTextFieldForVersionReset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaIp(rpaIpTextFieldForVersionReset.getText());
			}
		});
		rpaUserTextFieldForVersionReset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaUser(rpaUserTextFieldForVersionReset.getText());
			}
		});
		rpaPasswordFieldForVersionReset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaPass(rpaPasswordFieldForVersionReset.getPassword());
			}
		});

		getVersionsButton.addActionListener(event -> executeGetVersions());

		rpcsNumberSpinnerForVersionReset.addChangeListener(event -> rpcsDetails.setRpcsNum((Integer) rpcsNumberSpinnerForVersionReset.getValue()));

		rpcsUserTextFieldForVersionReset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpcsDetails.setRpcsUser(rpcsUserTextFieldForVersionReset.getText());
			}
		});

		rpcsPasswordFieldForVersionReset.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpcsDetails.setRpcsPass(rpcsPasswordFieldForVersionReset.getPassword());
			}
		});

		resetVersionButton.addActionListener(event -> executeResetVersion());
	}

	private void setEventsForJarReplacement() {
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

		rpaPasswordFieldForJarReplacement.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				rpaDetails.setRpaPass(rpaPasswordFieldForJarReplacement.getPassword());
			}
		});

		rpcsNumberSpinnerForJarReplacement.addChangeListener(event -> rpcsDetails.setRpcsNum((Integer) rpcsNumberSpinnerForJarReplacement.getValue()));

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

	}

	private void setEventsForRemoteDebugging() {
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
	}

	private void executeGetVersions() {
		System.out.println("got 'Get versions' request with params : " +
				"RPA IP=" + rpaDetails.getRpaIp() +
				", User=" + rpaDetails.getRpaUser() +
				", Pass=" + rpaDetails.getRpaPass() +
				", Rpcs Num=" + rpcsDetails.getRpcsNum() +
				", User=" + rpcsDetails.getRpcsUser() +
				", Pass=" + rpcsDetails.getRpcsPass());

		String branchVersion = "";
		//versionsService.getBranchVersion(rpcsDetails);
		String rpaVersion = null;
		try {
			rpaVersion = versionsService.getRpaVersion(rpaDetails);
		} catch (JSchException e) {
			e.printStackTrace();
		}

		branchVersionTextField.setText(branchVersion);
		rpaVersionTextField.setText(rpaVersion);
	}


	private void executeRemoteDebug() {
		System.out.println("got 'Remote debug' request with params : " +
				"RPA IP=" + rpaDetails.getRpaIp() +
				", User=" + rpaDetails.getRpaUser() +
				", Pass=" + rpaDetails.getRpaPass());

		String precess = precessComboBox.getItemAt(precessComboBox.getSelectedIndex());
		debugService.debug(rpaDetails, precess);

		JBPopup message = popupFactory.createMessage("Remote debug has completed successfully!");
		message.showUnderneathOf(resetVersionButton);
	}

	private void executeResetVersion() {
		String rpaVersion = rpaVersionTextField.getText();
		System.out.println("got 'Version reset' request with params : " +
				"Rpcs Num=" + rpcsDetails.getRpcsNum() +
				", User=" + rpcsDetails.getRpcsUser() +
				", Pass=" + rpcsDetails.getRpcsPass() +
				", rpaVersion=" + rpaVersion);

		versionsService.resetVersion(rpcsDetails, rpaVersion);

		JBPopup message = popupFactory.createMessage("Branch has reset to the RPA version");
		message.showUnderneathOf(resetVersionButton);
	}


	JPanel getContent() {
		return myToolWindowContent;
	}
}
