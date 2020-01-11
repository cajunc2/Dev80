package org.cajunc2.dev80.ui.menu;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

import org.cajunc2.dev80.ui.AssemblyEditor;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

class RecentFilesMenu extends Menu {
	private static final long serialVersionUID = 1L;
	List<String> recentFiles;

	public RecentFilesMenu() {
		super("Recent Files");
		rebuildMenu();
	}

	void rebuildMenu() {
		this.removeAll();
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		recentFiles = new LinkedList<String>(Arrays.asList(prefs.get("recentFiles", "").split(File.pathSeparator)));
		for (String recentFile : recentFiles) {
			if (recentFile.isEmpty()) {
				continue;
			}
			final File f = new File(recentFile);
			if (f.exists()) {
				MenuItem mi = new MenuItem(f.getName());
				mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Commands.OPEN_SPECIFIC_FILE.publish(f);
					}
				});
				add(mi);
			}
		}

		addSeparator();

		MenuItem clearMenuItem = new MenuItem("Clear Recent Files");
		clearMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				recentFiles.clear();
				updatePrefs();
				rebuildMenu();
			}
		});
		add(clearMenuItem);

		TopicHandler<AssemblyEditor> th = new TopicHandler<AssemblyEditor>() {

			@Override
			public void topicReceived(AssemblyEditor payload) {
				String filePath = payload.getCurrentFile().getAbsolutePath();
				if (recentFiles.contains(filePath)) {
					return;
				}
				recentFiles.add(0, filePath);
				while (recentFiles.size() > 10) {
					recentFiles.remove(recentFiles.size() - 1);
				}
				updatePrefs();
				rebuildMenu();
			}
		};

		Events.FILE_OPENED.subscribe(th);
		Events.FILE_SAVED.subscribe(th);
	}

	void updatePrefs() {
		String[] arr = recentFiles.toArray(new String[recentFiles.size()]);
		String preference = String.join(File.pathSeparator, arr);
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.put("recentFiles", preference);
	}
}