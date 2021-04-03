package org.cajunc2.dev80.ui.syntax;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.TokenTypes;

public class DarkSyntaxScheme extends SyntaxScheme {

	private static final Font PLAIN = new Font("Consolas", Font.PLAIN, 13);

	private static class Colors {
		public static final Color FOREGROUND = UIManager.getColor("EditorPane.foreground");
		public static final Color COMMENT = new Color(FOREGROUND.getRGB() & 0x99ffffff, true);
	}

	public DarkSyntaxScheme() {
		super(PLAIN);
		for (int i = 0; i < TokenTypes.DEFAULT_NUM_TOKEN_TYPES; i++) {
			getStyle(i).font = PLAIN;
			getStyle(i).foreground = Colors.FOREGROUND;
		}

		/* Comments */
		getStyle(TokenTypes.COMMENT_EOL).foreground = Colors.COMMENT;

		/* Instructions */
		getStyle(TokenTypes.FUNCTION).foreground = new Color(128, 160, 255);

		/* Assembler Directives */
		getStyle(TokenTypes.PREPROCESSOR).foreground = new Color(128, 160, 192);

		/* Labels */
		getStyle(TokenTypes.ANNOTATION).foreground = new Color(160, 192, 128);

		/* Registers */
		getStyle(TokenTypes.VARIABLE).foreground = new Color(224, 160, 128);

		/* Pointers */
		getStyle(TokenTypes.DATA_TYPE).foreground = new Color(224, 176, 128);

		/* Conditions */
		getStyle(TokenTypes.RESERVED_WORD).foreground = new Color(192, 160, 255);

		/* Literals */
		getStyle(TokenTypes.LITERAL_NUMBER_DECIMAL_INT).foreground = new Color(255, 160, 192);
		getStyle(TokenTypes.LITERAL_NUMBER_HEXADECIMAL).foreground = new Color(255, 160, 192);

		/* Strings */
		getStyle(TokenTypes.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(192, 176, 128);
	}

}
