package com.dell.easydebug.ui;

import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class MyToolWindow {

	private JButton hideToolWindowButton;
	private JPanel myToolWindowContent;

	MyToolWindow(ToolWindow toolWindow) {
		hideToolWindowButton.addActionListener(event -> toolWindow.hide(null));
	}

	JPanel getContent() {
		return myToolWindowContent;
	}
}
