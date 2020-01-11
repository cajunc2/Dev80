package org.cajunc2.dev80.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class DnDTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 8806183628966921831L;
	private static final int LINEWIDTH = 4;
	private static final String NAME = "test";
	private static final Color LINE_COLOR = new Color(0, 100, 255);
	final GhostGlassPane glassPane = new GhostGlassPane();
	final Rectangle2D lineRect = new Rectangle2D.Double();
	int dragTabIndex = -1;

	public DnDTabbedPane() {
		super();

		final DragSourceListener dsl = new DragSourceListener() {

			@Override
			public void dragEnter(DragSourceDragEvent e) {
				e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
			}

			@Override
			public void dragExit(DragSourceEvent e) {
				e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
				lineRect.setRect(0, 0, 0, 0);
				glassPane.setPoint(new Point(-1000, -1000));
				glassPane.repaint();
			}

			@Override
			public void dragOver(DragSourceDragEvent e) {
				Point tabPt = e.getLocation();
				SwingUtilities.convertPointFromScreen(tabPt, DnDTabbedPane.this);
				Point glassPt = e.getLocation();
				SwingUtilities.convertPointFromScreen(glassPt, glassPane);
				int targetIdx = getTargetTabIndex(glassPt);
				if (getTabAreaBound().contains(tabPt) && targetIdx >= 0) {
					e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
				} else {
					e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
				}
			}

			@Override
			public void dragDropEnd(DragSourceDropEvent e) {
				lineRect.setRect(0, 0, 0, 0);
				dragTabIndex = -1;
				if (hasGhost()) {
					glassPane.setVisible(false);
					glassPane.setImage(null);
				}
			}

			@Override
			public void dropActionChanged(DragSourceDragEvent e) {
				// No implementation
			}
		};
		final Transferable t = new Transferable() {

			private final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, NAME);

			@Override
			public Object getTransferData(DataFlavor flavor) {
				return DnDTabbedPane.this;
			}

			@Override
			public DataFlavor[] getTransferDataFlavors() {
				DataFlavor[] f = new DataFlavor[1];
				f[0] = this.FLAVOR;
				return f;
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return flavor.getHumanPresentableName().equals(NAME);
			}
		};
		final DragGestureListener dgl = new DragGestureListener() {

			@Override
			public void dragGestureRecognized(DragGestureEvent e) {
				Point tabPt = e.getDragOrigin();
				dragTabIndex = indexAtLocation(tabPt.x, tabPt.y);
				if (dragTabIndex < 0)
					return;
				initGlassPane(e.getComponent(), e.getDragOrigin());
				try {
					e.startDrag(DragSource.DefaultMoveDrop, t, dsl);
				} catch (InvalidDnDOperationException idoe) {
					idoe.printStackTrace();
				}
			}
		};

		@SuppressWarnings("unused")
		DropTarget unused = new DropTarget(glassPane, DnDConstants.ACTION_COPY_OR_MOVE, new CDropTargetListener(), true);
		new DragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, dgl);
	}

	class CDropTargetListener implements DropTargetListener {

		@Override
		public void dragEnter(DropTargetDragEvent e) {
			if (isDragAcceptable(e))
				e.acceptDrag(e.getDropAction());
			else
				e.rejectDrag();
		}

		@Override
		public void dragExit(DropTargetEvent e) {
			// No implementation
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent e) {
			// No implementation
		}

		@Override
		public void dragOver(final DropTargetDragEvent e) {
			if (getTabPlacement() == SwingConstants.TOP || getTabPlacement() == SwingConstants.BOTTOM) {
				initTargetLeftRightLine(getTargetTabIndex(e.getLocation()));
			} else {
				initTargetTopBottomLine(getTargetTabIndex(e.getLocation()));
			}
			repaint();
			if (hasGhost()) {
				glassPane.setPoint(e.getLocation());
				glassPane.repaint();
			}
		}

		@Override
		public void drop(DropTargetDropEvent e) {
			if (isDropAcceptable(e)) {
				convertTab(dragTabIndex, getTargetTabIndex(e.getLocation()));
				e.dropComplete(true);
			} else {
				e.dropComplete(false);
			}
			repaint();
		}

		public boolean isDragAcceptable(DropTargetDragEvent e) {
			Transferable t = e.getTransferable();
			DataFlavor[] f = e.getCurrentDataFlavors();
			if (t.isDataFlavorSupported(f[0]) && dragTabIndex >= 0) {
				return true;
			}
			return false;
		}

		public boolean isDropAcceptable(DropTargetDropEvent e) {
			Transferable t = e.getTransferable();
			DataFlavor[] f = t.getTransferDataFlavors();
			if (t.isDataFlavorSupported(f[0]) && dragTabIndex >= 0) {
				return true;
			}
			return false;
		}
	}

	private boolean hasGhost = true;

	public void setPaintGhost(boolean flag) {
		hasGhost = flag;
	}

	public boolean hasGhost() {
		return hasGhost;
	}

	int getTargetTabIndex(Point glassPt) {
		Point tabPt = SwingUtilities.convertPoint(glassPane, glassPt, DnDTabbedPane.this);
		boolean isTB = getTabPlacement() == SwingConstants.TOP || getTabPlacement() == SwingConstants.BOTTOM;
		for (int i = 0; i < getTabCount(); i++) {
			Rectangle r = getBoundsAt(i);
			if (isTB)
				r.setRect(r.x - r.width / 2, r.y, r.width, r.height);
			else
				r.setRect(r.x, r.y - r.height / 2, r.width, r.height);
			if (r.contains(tabPt))
				return i;
		}
		Rectangle r = getBoundsAt(getTabCount() - 1);
		if (isTB)
			r.setRect(r.x + r.width / 2, r.y, r.width, r.height);
		else
			r.setRect(r.x, r.y + r.height / 2, r.width, r.height);
		return r.contains(tabPt) ? getTabCount() : -1;
	}

	void convertTab(int prev, int next) {
		if (next < 0 || prev == next) {
			return;
		}
		Component cmp = getComponentAt(prev);
		String str = getTitleAt(prev);
		Component tabComponent = getTabComponentAt(prev);
		if (next == getTabCount()) {
			remove(prev);
			addTab(str, cmp);
			setTabComponentAt(getTabCount() - 1, tabComponent);
			setSelectedIndex(getTabCount() - 1);
		} else if (prev > next) {
			remove(prev);
			insertTab(str, null, cmp, null, next);
			setTabComponentAt(next, tabComponent);
			setSelectedIndex(next);
		} else {
			remove(prev);
			insertTab(str, null, cmp, null, next - 1);
			setSelectedIndex(next - 1);
			setTabComponentAt(next - 1, tabComponent);
		}
	}

	void initTargetLeftRightLine(int next) {
		if (next < 0) {
			lineRect.setRect(0, 0, 0, 0);
		} else if (next == getTabCount()) {
			Rectangle rect = getBoundsAt(getTabCount() - 1);
			lineRect.setRect(rect.x + rect.width - LINEWIDTH / 2 - 2, rect.y - 2, LINEWIDTH, rect.height + 5);
		} else if (next == 0) {
			Rectangle rect = getBoundsAt(0);
			lineRect.setRect(-LINEWIDTH / 2 + 2, rect.y - 2, LINEWIDTH, rect.height + 4);
		} else if (next - dragTabIndex == 1) {
			Rectangle rect = getBoundsAt(next - 1);
			lineRect.setRect(rect.x + rect.width - 2, rect.y - 2, LINEWIDTH, rect.height + 4);
		} else {
			Rectangle rect = getBoundsAt(next);
			lineRect.setRect(rect.x - 2, rect.y - 2, LINEWIDTH, rect.height + 5);
		}
	}

	void initTargetTopBottomLine(int next) {
		if (next < 0 || dragTabIndex == next || next - dragTabIndex == 1) {
			lineRect.setRect(0, 0, 0, 0);
		} else if (next == getTabCount()) {
			Rectangle rect = getBoundsAt(getTabCount() - 1);
			lineRect.setRect(rect.x, rect.y + rect.height - LINEWIDTH / 2, rect.width, LINEWIDTH);
		} else if (next == 0) {
			Rectangle rect = getBoundsAt(0);
			lineRect.setRect(rect.x, -LINEWIDTH / 2, rect.width, LINEWIDTH);
		} else {
			Rectangle rect = getBoundsAt(next - 1);
			lineRect.setRect(rect.x, rect.y + rect.height - LINEWIDTH / 2, rect.width, LINEWIDTH);
		}
	}

	void initGlassPane(Component c, Point tabPt) {
		getRootPane().setGlassPane(glassPane);
		if (hasGhost()) {
			Rectangle rect = getBoundsAt(dragTabIndex);
			BufferedImage image = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			c.paint(g);
			image = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
			glassPane.setImage(image);
		}
		Point glassPt = SwingUtilities.convertPoint(c, tabPt, glassPane);
		glassPane.setPoint(glassPt);
		glassPane.setVisible(true);
	}

	Rectangle getTabAreaBound() {
		Rectangle lastTab = getUI().getTabBounds(this, getTabCount() - 1);
		return new Rectangle(0, 0, getWidth(), lastTab.y + lastTab.height);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (dragTabIndex >= 0) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(LINE_COLOR);
			g2.fill(lineRect);
		}
	}

	private static class GhostGlassPane extends JPanel {
		private static final long serialVersionUID = -8608887727914115195L;
		private final AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		private Point location = new Point(0, 0);
		private BufferedImage draggingGhost = null;
		int ghostWidth = 0;
		int ghostHeight = 0;

		public GhostGlassPane() {
			setOpaque(false);
		}

		public void setImage(BufferedImage draggingGhost) {
			this.draggingGhost = draggingGhost;
			if (draggingGhost == null) {
				ghostWidth = 0;
				ghostHeight = 0;
				return;
			}
			ghostWidth = draggingGhost.getWidth();
			ghostHeight = draggingGhost.getHeight();
		}

		public void setPoint(Point location) {
			this.location = location;
		}

		@Override
		public void paintComponent(Graphics g) {
			if (draggingGhost == null) {
				return;
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.setComposite(composite);
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			double xx = location.getX() - (ghostWidth / 2d);
			double yy = location.getY() - (ghostHeight / 2d);
			g2.drawImage(draggingGhost, (int) xx, (int) yy, null);
		}
	}

}

