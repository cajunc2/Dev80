package org.cajunc2.dev80.ui.help;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.cajunc2.dev80.ui.help.instructions.InstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.InstructionVariant;

public class OpcodeTable extends JTable {

	private static final long serialVersionUID = 1L;
	static final Font OPCODE_FONT = new Font("Consolas", Font.PLAIN, 12);
	private final OpcodeTableModel model = new OpcodeTableModel();

	public OpcodeTable() {
		this.setModel(model);
		this.setFillsViewportHeight(true);
		this.setFont(getFont().deriveFont(11f));
		TableCellRenderer r = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setBorder(BorderFactory.createEmptyBorder(6, 4, 2, 8));
				if (column == 1) {
					setFont(OPCODE_FONT);
				}
				return this;
			}
		};

		this.getColumnModel().getColumn(1).setCellRenderer(r);
		this.getColumnModel().getColumn(0).setPreferredWidth(70);
		this.getColumnModel().getColumn(1).setPreferredWidth(94);
		this.getColumnModel().getColumn(2).setPreferredWidth(32);
		this.getColumnModel().getColumn(3).setPreferredWidth(15);
	}

	public void changeInstruction(InstructionDetail instruction) {
		model.instruction = instruction;
		model.fireTableDataChanged();
	}

	public void resizeColumnWidth() {
		final TableColumnModel columnModel = getColumnModel();
		for (int column = 0; column < getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < getRowCount(); row++) {
				TableCellRenderer renderer = getCellRenderer(row, column);
				Component comp = prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			if (width > 300) {
				width = 300;
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	private static class OpcodeTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		private static final String[] COLUMN_NAMES = new String[] { "Mnemonic", "Opcode", "Time", "Size" };

		InstructionDetail instruction = null;

		public OpcodeTableModel() {
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}

		@Override
		public int getRowCount() {
			if (instruction == null || instruction.variants() == null) {
				return 0;
			}
			return instruction.variants().size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			InstructionVariant v = instruction.variants().get(row);
			switch (column) {
				case 0:
					return v.getMnemonic();
				case 1:
					return v.getOpcode();
				case 2:
					return v.getCycles();
				case 3:
					return v.getSize();
				default:
					return "";
			}
		}
	}

}
