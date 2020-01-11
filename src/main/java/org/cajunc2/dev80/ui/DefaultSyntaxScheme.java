package org.cajunc2.dev80.ui;

import java.awt.Color;
import java.awt.Font;

import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.TokenTypes;

public class DefaultSyntaxScheme extends SyntaxScheme {

	private static final Font PLAIN = new Font("Consolas", Font.PLAIN, 13);
	private static final Font ITALIC = new Font("Consolas", Font.ITALIC, 13);
	private static final Font BOLD = new Font("Consolas", Font.BOLD, 13);

	private static class Colors {
		public static final Color COMMENT = new Color(160, 160, 160);
	}

	public DefaultSyntaxScheme() {
		super(PLAIN);
		for (int i = 0; i < TokenTypes.DEFAULT_NUM_TOKEN_TYPES; i++) {
			getStyle(i).font = PLAIN;
		}
		
		/* Comments */
		getStyle(TokenTypes.COMMENT_EOL).foreground = Colors.COMMENT;
		getStyle(TokenTypes.COMMENT_EOL).font = ITALIC;

		/* Instructions */
		getStyle(TokenTypes.FUNCTION).foreground = new Color(0, 0, 192);

		/* Assembler Directives */
		getStyle(TokenTypes.PREPROCESSOR).foreground = new Color(0, 128, 128);

		/* Labels */
		getStyle(TokenTypes.ANNOTATION).foreground = new Color(80, 160, 48);
		getStyle(TokenTypes.ANNOTATION).font = BOLD;

		/* Registers */
		getStyle(TokenTypes.VARIABLE).foreground = new Color(192, 96, 0);
		getStyle(TokenTypes.VARIABLE).font = BOLD;

		/* Pointers */
		getStyle(TokenTypes.DATA_TYPE).foreground = new Color(160, 128, 0);
		getStyle(TokenTypes.DATA_TYPE).font = BOLD;

		/* Conditions */
		getStyle(TokenTypes.RESERVED_WORD).foreground = new Color(255, 0, 0);

		/* Literals */
		getStyle(TokenTypes.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.MAGENTA;
		getStyle(TokenTypes.LITERAL_NUMBER_HEXADECIMAL).foreground = Color.MAGENTA;
		getStyle(TokenTypes.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.MAGENTA.darker();
	}

}
