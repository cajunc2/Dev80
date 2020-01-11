package org.cajunc2.dev80.ui.menu;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import org.cajunc2.dev80.project.Project;
import org.cajunc2.dev80.ui.topic.Commands;
import org.cajunc2.dev80.ui.topic.Events;
import org.cajunc2.util.topic.TopicHandler;

class RecentProjectsMenu extends Menu {
	private static final Logger logger = Logger.getLogger(RecentProjectsMenu.class.getName());
	private static final long serialVersionUID = 1L;
	List<String> recentProjects;

	public RecentProjectsMenu() {
		super("Recent Projects");
		rebuildMenu();
	}

	void updatePrefs() {
		String[] arr = recentProjects.toArray(new String[recentProjects.size()]);
		String preference = String.join(File.pathSeparator, arr);
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.put("recentProjects", preference);
	}

	void rebuildMenu() {
		this.removeAll();
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		recentProjects = new LinkedList<String>(Arrays.asList(prefs.get("recentProjects", "").split(File.pathSeparator)));
		for (String recentProject : recentProjects) {
			if (recentProject.isEmpty()) {
				continue;
			}
			final File f = new File(recentProject + File.separator + Project.FILE_NAME);
			if (f.exists()) {
				try {
					final Project p = Project.load(f);
					MenuItem mi = new MenuItem(p.getTitle());
					mi.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Commands.OPEN_SPECIFIC_PROJECT.publish(f);
						}
					});
					add(mi);
				} catch (Exception e) {
					// user probably doesn't care. Maybe the project was deleted/moved or something
					logger.log(Level.SEVERE, "", e);
				}
			}
		}

		addSeparator();

		MenuItem clearMenuItem = new MenuItem("Clear Recent Projects");
		clearMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				recentProjects.clear();
				updatePrefs();
				rebuildMenu();
			}
		});
		add(clearMenuItem);

		TopicHandler<Project> th = new TopicHandler<Project>() {

			@Override
			public void topicReceived(Project payload) {

				String filePath = payload.getProjectDir().getAbsolutePath();
				if (recentProjects.contains(filePath)) {
					return;
				}
				recentProjects.add(0, filePath);
				while (recentProjects.size() > 10) {
					recentProjects.remove(recentProjects.size() - 1);
				}
				updatePrefs();
				rebuildMenu();
			}
		};

		Events.PROJECT_OPENED.subscribe(th);
	}
}