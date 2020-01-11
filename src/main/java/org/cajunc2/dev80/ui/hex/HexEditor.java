package org.cajunc2.dev80.ui.hex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class HexEditor extends JPanel {

	private static final int COL_WIDTH = 25;
	private static final long serialVersionUID = 9124652681396163900L;
	private final HexTable table = new HexTable();
	final int addressOffset;
	byte[] data;

	public HexEditor(byte[] data) {
		this(data, 0);
	}

	public HexEditor(byte[] data, int addressOffset) {
		this.addressOffset = addressOffset;
		setLayout(new BorderLayout());
		this.data = data;
		JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(510, 0));
	}

	private final class HexTable extends JTable {

		private static final long serialVersionUID = -6363190495498159554L;

		public HexTable() {
			setModel(new HexTableModel());
			setFont(new Font("Consolas", Font.PLAIN, 12));
			setShowGrid(false);
			setGridColor(new Color(240, 240, 240));
			setCellSelectionEnabled(true);
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			setIntercellSpacing(new Dimension(0, 0));
			setFillsViewportHeight(true);

			getColumnModel().getColumn(0).setMinWidth(COL_WIDTH);
			getColumnModel().getColumn(0).setMaxWidth(54325423);
			for (int i = 1; i <= 16; i++) {
				getColumnModel().getColumn(i).setMinWidth(COL_WIDTH);
				getColumnModel().getColumn(i).setMaxWidth(COL_WIDTH);
			}

			getColumnModel().getColumn(17).setMinWidth(125);
			getColumnModel().getColumn(17).setMaxWidth(125);

			getTableHeader().setReorderingAllowed(false);
			getTableHeader().setResizingAllowed(false);
			getTableHeader().setDefaultRenderer(new ColumnIndexRenderer());
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public TableCellRenderer getCellRenderer(int row, int column) {
			if (column == 0) {
				return new RowIndexRenderer();
			}
			if (column == 17) {
				return new ASCIIOutputCellRenderer();
			}
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			return renderer;
		}

		@Override
		public TableCellEditor getCellEditor(int row, int column) {
			if (column == 0) {
				return null;
			}
			return super.getCellEditor(row, column);
		}
	}

	private static class ColumnIndexRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 3384444863525920384L;

		public ColumnIndexRenderer() {
			this.setBackground(UIManager.getColor("Panel.background"));
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		                                               boolean hasFocus, int row, int column) {
			JComponent c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			Border line = BorderFactory.createMatteBorder(0, 0, 1, 0, UIManager.getColor("TabbedPane.shadow"));
			Border padding = BorderFactory.createEmptyBorder(10, 0, 1, 0);
			c.setBorder(BorderFactory.createCompoundBorder(line, padding));
			return c;
		}
	}

	private static class RowIndexRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 3384444863525920384L;

		public RowIndexRenderer() {
			this.setBackground(UIManager.getColor("Panel.background"));
			setHorizontalAlignment(SwingConstants.TRAILING);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		                                               boolean hasFocus, int row, int column) {
			JComponent c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			Border line = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY);
			Border padding = BorderFactory.createEmptyBorder(0, 0, 0, 2);
			c.setBorder(BorderFactory.createCompoundBorder(line, padding));
			return c;
		}

	}

	private static class ASCIIOutputCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 3384444863525920384L;

		public ASCIIOutputCellRenderer() {
			setHorizontalAlignment(SwingConstants.LEADING);

			setFont(new Font("Monospaced", Font.PLAIN, 12));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		                                               boolean hasFocus, int row, int column) {
			JComponent c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			Border line = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY);
			Border padding = BorderFactory.createEmptyBorder(0, 4, 0, 0);
			c.setBorder(BorderFactory.createCompoundBorder(line, padding));
			return c;
		}
	}

	private class HexTableModel extends AbstractTableModel {

		private static final long serialVersionUID = -2308441938226561275L;

		public HexTableModel() {
		}

		@Override
		public int getRowCount() {
			return data.length / 16;
		}

		@Override
		public int getColumnCount() {
			return 18;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return Integer.toHexString(rowIndex + (addressOffset / 16)).toUpperCase() + ".";
			}
			if (columnIndex == 17) {
				return new String(Arrays.copyOfRange(data, rowIndex * 16, rowIndex * 16 + 16));
			}
			int cellIndex = cellIndex(rowIndex, columnIndex);
			if (cellIndex >= data.length) {
				return null;
			}
			int cellData = data[cellIndex(rowIndex, columnIndex)] & 0xFF;
			if (cellData < 16) {
				return '0' + Integer.toHexString(cellData).toUpperCase();
			}
			return Integer.toHexString(cellData).toUpperCase();
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			super.setValueAt(aValue, rowIndex, columnIndex);
		}

		@Override
		public String getColumnName(int column) {
			if (column == 0) {
				return "";
			}
			if (column == 17) {
				return "Data";
			}
			return "." + Integer.toHexString(column - 1);
		}

	}

	public byte[] getByteContent() {
		return this.data;
	}

	public void setByteContent(byte[] data) {
		this.data = data;
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		repaint();
	}

	static int cellIndex(int row, int col) {
		return row * 16 + col - 1;
	}
}
