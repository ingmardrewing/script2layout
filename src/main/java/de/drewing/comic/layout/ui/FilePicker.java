package de.drewing.comic.layout.ui;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FilePicker extends JFrame {
  public String open() {
		FileDialog fd = new FileDialog(this, "Choose a plain text comic script file (.txt)", FileDialog.LOAD);
		final String dir = System.getProperty("user.dir");
		fd.setDirectory(dir);
		fd.setFile("*.txt");
		fd.setVisible(true);
		String filename = fd.getDirectory() + fd.getFile();
		if (filename == null) {
				System.out.println("You cancelled the choice");
				return null;
		}
		else {
				System.out.println("You chose " + filename);
		}
		return filename;
  }

	public String openDir() {
		System.setProperty("apple.awt.fileDialogForDirectories", "true");
		FileDialog fd = new FileDialog(this, "Choose a directory to store the generated pages in", FileDialog.LOAD);
		String dir = System.getProperty("user.dir");
		fd.setDirectory(dir);
		fd.setVisible(true);
		String filename = fd.getDirectory() + fd.getFile();
		if (filename == null) {
				System.out.println("You cancelled the choice");
				return null;
		}
		else {
				System.out.println("You chose " + filename);
		}
		System.setProperty("apple.awt.fileDialogForDirectories", "false");
		return filename;
	}
}
