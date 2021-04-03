/*
 * 03/07/2004
 * 
 * WindowsBatchTokenMaker.java - Scanner for Windows batch files.
 * 
 * This library is distributed under a modified BSD license. See the included RSyntaxTextArea.License.txt file for
 * details.
 */
package org.cajunc2.dev80.ui.syntax;

import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMap;
import org.fife.ui.rsyntaxtextarea.TokenTypes;

/**
 * A token maker that turns text into a linked list of <code>Token</code>s for
 * syntax highlighting Microsoft Windows batch files.
 * 
 * @author Robert Futrell
 * @version 0.1
 */
public class Z80TokenMaker extends AbstractTokenMaker {

	protected final String operators = "+-";

	private int currentTokenStart;
	private int currentTokenType;

	private VariableType varType;

	/**
	 * Constructor.
	 */
	public Z80TokenMaker() {
		super(); // Initializes tokensToHighlight.
	}

	/**
	 * Checks the token to give it the exact ID it deserves before being passed
	 * up to the super method.
	 * 
	 * @param segment
	 *            <code>Segment</code> to get text from.
	 * @param start
	 *            Start offset in <code>segment</code> of token.
	 * @param end
	 *            End offset in <code>segment</code> of token.
	 * @param tokenType
	 *            The token's type.
	 * @param startOffset
	 *            The offset in the document at which the token occurs.
	 */
	@Override
	public void addToken(Segment segment, int start, int end, int tokenType, int startOffset) {

		switch (tokenType) {
		// Since reserved words, functions, and data types are all passed
		// into here as "identifiers," we have to see what the token
		// really is...
		case TokenTypes.IDENTIFIER:
			int value = wordsToHighlight.get(segment, start, end);
			if (value != -1)
				tokenType = value;
			break;
		default:
			break;
		}

		super.addToken(segment, start, end, tokenType, startOffset);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getLineCommentStartAndEnd(int languageIndex) {
		return new String[] { ";", null };
	}

	/**
	 * Returns whether tokens of the specified type should have "mark
	 * occurrences" enabled for the current programming language.
	 * 
	 * @param type
	 *            The token type.
	 * @return Whether tokens of this type should have "mark occurrences"
	 *         enabled.
	 */
	@Override
	public boolean getMarkOccurrencesOfTokenType(int type) {
		return type == TokenTypes.ANNOTATION || type == TokenTypes.VARIABLE;
	}

	/**
	 * Returns the words to highlight
	 * 
	 * @return A <code>TokenMap</code> containing the words to highlight
	 * @see org.fife.ui.rsyntaxtextarea.AbstractTokenMaker#getWordsToHighlight
	 */
	@Override
	public TokenMap getWordsToHighlight() {

		TokenMap tokenMap = new TokenMap(true); // Ignore case.
		int register = TokenTypes.VARIABLE;
		int instruction = TokenTypes.FUNCTION;
		int directive = TokenTypes.PREPROCESSOR;
		int condition = TokenTypes.RESERVED_WORD;
		int operator = TokenTypes.OPERATOR;

		/* Registers */
		tokenMap.put("A", register);
		tokenMap.put("F", register);
		tokenMap.put("B", register);
		tokenMap.put("C", register);
		tokenMap.put("D", register);
		tokenMap.put("E", register);
		tokenMap.put("H", register);
		tokenMap.put("L", register);
		tokenMap.put("AF'", register);
		tokenMap.put("IXH", register);
		tokenMap.put("IXL", register);
		tokenMap.put("IYH", register);
		tokenMap.put("IYL", register);
		tokenMap.put("AF", register);
		tokenMap.put("BC", register);
		tokenMap.put("DE", register);
		tokenMap.put("HL", register);
		tokenMap.put("IX", register);
		tokenMap.put("IY", register);
		tokenMap.put("I", register);
		tokenMap.put("R", register);
		tokenMap.put("SP", register);
		tokenMap.put("PC", register);

		/* CPU Instructions */
		tokenMap.put("ADC", instruction);
		tokenMap.put("ADD", instruction);
		tokenMap.put("AND", instruction);
		tokenMap.put("BIT", instruction);
		tokenMap.put("CALL", instruction);
		tokenMap.put("CCF", instruction);
		tokenMap.put("CP", instruction);
		tokenMap.put("CPD", instruction);
		tokenMap.put("CPDR", instruction);
		tokenMap.put("CPI", instruction);
		tokenMap.put("CPIR", instruction);
		tokenMap.put("CPL", instruction);
		tokenMap.put("DAA", instruction);
		tokenMap.put("DEC", instruction);
		tokenMap.put("DI", instruction);
		tokenMap.put("DJNZ", instruction);
		tokenMap.put("EI", instruction);
		tokenMap.put("EX", instruction);
		tokenMap.put("EXX", instruction);
		tokenMap.put("HALT", instruction);
		tokenMap.put("IM", instruction);
		tokenMap.put("IN", instruction);
		tokenMap.put("INC", instruction);
		tokenMap.put("IND", instruction);
		tokenMap.put("INDR", instruction);
		tokenMap.put("INI", instruction);
		tokenMap.put("INIR", instruction);
		tokenMap.put("JP", instruction);
		tokenMap.put("JR", instruction);
		tokenMap.put("LD", instruction);
		tokenMap.put("LDD", instruction);
		tokenMap.put("LDDR", instruction);
		tokenMap.put("LDI", instruction);
		tokenMap.put("LDIR", instruction);
		tokenMap.put("NEG", instruction);
		tokenMap.put("NOP", instruction);
		tokenMap.put("OR", instruction);
		tokenMap.put("OTDR", instruction);
		tokenMap.put("OTIR", instruction);
		tokenMap.put("OUT", instruction);
		tokenMap.put("OUTD", instruction);
		tokenMap.put("OUTI", instruction);
		tokenMap.put("POP", instruction);
		tokenMap.put("PUSH", instruction);
		tokenMap.put("RES", instruction);
		tokenMap.put("RET", instruction);
		tokenMap.put("RETI", instruction);
		tokenMap.put("RETN", instruction);
		tokenMap.put("RL", instruction);
		tokenMap.put("RLA", instruction);
		tokenMap.put("RLC", instruction);
		tokenMap.put("RLCA", instruction);
		tokenMap.put("RLD", instruction);
		tokenMap.put("RR", instruction);
		tokenMap.put("RRA", instruction);
		tokenMap.put("RRC", instruction);
		tokenMap.put("RRCA", instruction);
		tokenMap.put("RRD", instruction);
		tokenMap.put("RST", instruction);
		tokenMap.put("SBC", instruction);
		tokenMap.put("SCF", instruction);
		tokenMap.put("SET", instruction);
		tokenMap.put("SLA", instruction);
		tokenMap.put("SLL", instruction);
		tokenMap.put("SL1", instruction);
		tokenMap.put("SRA", instruction);
		tokenMap.put("SRL", instruction);
		tokenMap.put("SUB", instruction);
		tokenMap.put("XOR", instruction);

		/* Assembler Directives */
		tokenMap.put("macro", directive);
		tokenMap.put("endm", directive);
		tokenMap.put("if", directive);
		tokenMap.put("else", directive);
		tokenMap.put("endif", directive);
		tokenMap.put("incbin", directive);
		tokenMap.put("include", directive);
		tokenMap.put("seek", directive);
		tokenMap.put("org", directive);
		tokenMap.put("end", directive);
		tokenMap.put("ds", directive);
		tokenMap.put("dw", directive);
		tokenMap.put("db", directive);
		tokenMap.put("dm", directive);
		tokenMap.put("section", directive);
		tokenMap.put("ends", directive);
		tokenMap.put("virtual", directive);
		tokenMap.put("equ", directive);

		/* Conditions */
		// tokenMap.put("c", condition);
		tokenMap.put("nc", condition);
		tokenMap.put("z", condition);
		tokenMap.put("nz", condition);
		tokenMap.put("m", condition);
		tokenMap.put("p", condition);
		tokenMap.put("pe", condition);
		tokenMap.put("po", condition);

		tokenMap.put(",", operator);
		tokenMap.put("+", operator);
		tokenMap.put("-", operator);

		return tokenMap;

	}

	/**
	 * Returns a list of tokens representing the given text.
	 * 
	 * @param text
	 *            The text to break into tokens.
	 * @param startTokenType
	 *            The token with which to start tokenizing.
	 * @param startOffset
	 *            The offset at which the line of tokens begins.
	 * @return A linked list of tokens representing <code>text</code>.
	 */
	@Override
	public Token getTokenList(Segment text, int startTokenType, final int startOffset) {
		resetTokenList();

		char[] array = text.array;
		int offset = text.offset;
		int count = text.count;
		int end = offset + count;

		// See, when we find a token, its starting position is always of the
		// form:
		// 'startOffset + (currentTokenStart-offset)'; but since startOffset and
		// offset are constant, tokens' starting positions become:
		// 'newStartOffset+currentTokenStart' for one less subtraction
		// operation.
		int newStartOffset = startOffset - offset;

		currentTokenStart = offset;
		currentTokenType = startTokenType;

		// beginning:
		for (int i = offset; i < end; i++) {
			char c = array[i];
			switch (currentTokenType) {
			case TokenTypes.NULL:
				currentTokenStart = i; // Starting a new token here.
				switch (c) {
				case ' ':
				case '\t':
					currentTokenType = TokenTypes.WHITESPACE;
					continue;

				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					currentTokenType = TokenTypes.LITERAL_NUMBER_DECIMAL_INT;
					continue;

				case ';':
					currentTokenType = TokenTypes.COMMENT_EOL;
					continue;

				case '#':
					currentTokenType = TokenTypes.LITERAL_NUMBER_HEXADECIMAL;
					continue;

				default:
					currentTokenType = TokenTypes.ANNOTATION;
					continue;
				}

			case TokenTypes.LITERAL_NUMBER_HEXADECIMAL:
				switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case 'a':
				case 'A':
				case 'b':
				case 'B':
				case 'c':
				case 'C':
				case 'd':
				case 'D':
				case 'e':
				case 'E':
				case 'f':
				case 'F':
				case 'h':
				case 'H':
					continue;

				case ' ':
				case '\t':
					addToken(text, currentTokenStart, i - 1, TokenTypes.LITERAL_NUMBER_HEXADECIMAL,
					        newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.WHITESPACE;
					continue;

				default:
					addToken(text, currentTokenStart, i - 1, TokenTypes.LITERAL_NUMBER_HEXADECIMAL,
					        newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.IDENTIFIER;
					continue;
				}
			case TokenTypes.LITERAL_NUMBER_DECIMAL_INT:
				switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					continue;

				case ' ':
				case '\t':
					addToken(text, currentTokenStart, i - 1, TokenTypes.LITERAL_NUMBER_DECIMAL_INT,
					        newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.WHITESPACE;
					continue;

				default:
					addToken(text, currentTokenStart, i - 1, TokenTypes.LITERAL_NUMBER_DECIMAL_INT,
					        newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.IDENTIFIER;
					continue;
				}
			case TokenTypes.WHITESPACE:
				switch (c) {
				case ' ':
				case '\t':
					currentTokenType = TokenTypes.WHITESPACE;
					continue;

				case ';':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.COMMENT_EOL;
					continue;

				case '"':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.LITERAL_STRING_DOUBLE_QUOTE;
					continue;

				case '(':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.DATA_TYPE;
					continue;

				case '#':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.LITERAL_NUMBER_HEXADECIMAL;
					continue;

				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.LITERAL_NUMBER_DECIMAL_INT;
					continue;

				default:
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.IDENTIFIER;
					continue;
				}

			case TokenTypes.IDENTIFIER:
				switch (c) {
				case ' ':
				case '\t':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.WHITESPACE;
					continue;
				case ',':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.IDENTIFIER;
					continue;

				default:
					continue;
				}
			case TokenTypes.ANNOTATION:
				switch (c) {
				case ' ':
				case '\t':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.WHITESPACE;
					continue;

				case ':':
					addToken(text, currentTokenStart, i, TokenTypes.ANNOTATION, newStartOffset + currentTokenStart);
					currentTokenStart = i + 1;
					currentTokenType = TokenTypes.WHITESPACE;
					continue;

				default:
					currentTokenType = TokenTypes.ANNOTATION;
					continue;
				}

			case TokenTypes.COMMENT_EOL:
				if (c == '\n') {
					addToken(text, currentTokenStart, i, TokenTypes.COMMENT_EOL, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.WHITESPACE;
				}
				continue;

			case TokenTypes.LITERAL_STRING_DOUBLE_QUOTE:
				if (c == '"') {
					addToken(text, currentTokenStart, i, TokenTypes.LITERAL_STRING_DOUBLE_QUOTE,
					        newStartOffset + currentTokenStart);
					currentTokenStart = i + 1;
					currentTokenType = TokenTypes.WHITESPACE;
				}
				continue;

			case TokenTypes.DATA_TYPE:
				switch (c) {
				case ')':
					addToken(text, currentTokenStart, i, TokenTypes.DATA_TYPE, newStartOffset + currentTokenStart);
					currentTokenStart = i + 1;
					currentTokenType = TokenTypes.WHITESPACE;
					continue;
				case ' ':
				case '\t':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.WHITESPACE;
					continue;
				default:
					continue;
				}
			}
		}

		// Deal with the (possibly there) last token.
		if (currentTokenType != TokenTypes.NULL) {
			addToken(text, currentTokenStart, end - 1, currentTokenType, newStartOffset + currentTokenStart);
		}

		addNullToken();

		// Return the first token in our linked list.
		return firstToken;
	}

	public Token getTokenListOld(Segment text, int startTokenType, final int startOffset) {

		resetTokenList();

		char[] array = text.array;
		int offset = text.offset;
		int count = text.count;
		int end = offset + count;

		// See, when we find a token, its starting position is always of the
		// form:
		// 'startOffset + (currentTokenStart-offset)'; but since startOffset and
		// offset are constant, tokens' starting positions become:
		// 'newStartOffset+currentTokenStart' for one less subtraction
		// operation.
		int newStartOffset = startOffset - offset;

		currentTokenStart = offset;
		currentTokenType = startTokenType;

		// beginning:
		for (int i = offset; i < end; i++) {

			char c = array[i];

			switch (currentTokenType) {

			case TokenTypes.COMMENT_EOL:
				i = end - 1;
				addToken(text, currentTokenStart, i, TokenTypes.COMMENT_EOL, newStartOffset + currentTokenStart);
				// We need to set token type to null so at the bottom we don't
				// add one more token.
				currentTokenType = TokenTypes.NULL;
				break;

			case TokenTypes.NULL:

				currentTokenStart = i; // Starting a new token here.

				switch (c) {

				case ' ':
				case '\t':
					currentTokenType = TokenTypes.WHITESPACE;
					break;

				case '"':
					currentTokenType = TokenTypes.ERROR_STRING_DOUBLE;
					break;

				case '%':
					currentTokenType = TokenTypes.VARIABLE;
					break;

				// The "separators".
				case '(':
				case ')':
					addToken(text, currentTokenStart, i, TokenTypes.SEPARATOR, newStartOffset + currentTokenStart);
					currentTokenType = TokenTypes.NULL;
					break;

				// Newer version of EOL comments, or a label
				case ';':
					// If this will be the first token added, it is
					// a new-style comment or a label
					currentTokenType = TokenTypes.COMMENT_EOL;
					break;

				default:

					// Just to speed things up a tad, as this will usually be
					// the case (if spaces above failed).
					if (RSyntaxUtilities.isLetterOrDigit(c) || c == '\\') {
						currentTokenType = TokenTypes.IDENTIFIER;
						break;
					}

					int indexOf = operators.indexOf(c, 0);
					if (indexOf > -1) {
						addToken(text, currentTokenStart, i, TokenTypes.OPERATOR, newStartOffset + currentTokenStart);
						currentTokenType = TokenTypes.NULL;
						break;
					}
					currentTokenType = TokenTypes.IDENTIFIER;
					break;

				} // End of switch (c).

				break;

			case TokenTypes.WHITESPACE:

				switch (c) {

				case ' ':
				case '\t':
					break; // Still whitespace.

				case '"':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.ERROR_STRING_DOUBLE;
					break;

				case '%':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.VARIABLE;
					break;

				// The "separators".
				case '(':
				case ')':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					addToken(text, i, i, TokenTypes.SEPARATOR, newStartOffset + i);
					currentTokenType = TokenTypes.NULL;
					break;

				// The "separators2".
				case ',':
				case ';':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					addToken(text, i, i, TokenTypes.IDENTIFIER, newStartOffset + i);
					currentTokenType = TokenTypes.NULL;
					break;

				// Newer version of EOL comments, or a label
				case ':':
					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					// If the previous (whitespace) token was the first token
					// added, this is a new-style comment or a label
					if (firstToken.getNextToken() == null) {
						if (i < end - 1 && array[i + 1] == ':') { // new-style
						                                          // comment
							currentTokenType = TokenTypes.COMMENT_EOL;
						} else { // Label
							currentTokenType = TokenTypes.PREPROCESSOR;
						}
					} else { // Just a colon
						currentTokenType = TokenTypes.IDENTIFIER;
					}
					break;

				default: // Add the whitespace token and start anew.

					addToken(text, currentTokenStart, i - 1, TokenTypes.WHITESPACE, newStartOffset + currentTokenStart);
					currentTokenStart = i;

					// Just to speed things up a tad, as this will usually be
					// the case (if spaces above failed).
					if (RSyntaxUtilities.isLetterOrDigit(c) || c == '\\') {
						currentTokenType = TokenTypes.IDENTIFIER;
						break;
					}

					int indexOf = operators.indexOf(c, 0);
					if (indexOf > -1) {
						addToken(text, currentTokenStart, i, TokenTypes.OPERATOR, newStartOffset + currentTokenStart);
						currentTokenType = TokenTypes.NULL;
						break;
					}
					currentTokenType = TokenTypes.IDENTIFIER;

				} // End of switch (c).

				break;

			default: // Should never happen
			case TokenTypes.IDENTIFIER:

				switch (c) {

				case ' ':
				case '\t':
					// Check for REM comments.
					if (i - currentTokenStart == 3 && (array[i - 3] == 'r' || array[i - 3] == 'R')
					        && (array[i - 2] == 'e' || array[i - 2] == 'E')
					        && (array[i - 1] == 'm' || array[i - 1] == 'M')) {
						currentTokenType = TokenTypes.COMMENT_EOL;
						break;
					}
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.WHITESPACE;
					break;

				case '"':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.ERROR_STRING_DOUBLE;
					break;

				case '%':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					currentTokenStart = i;
					currentTokenType = TokenTypes.VARIABLE;
					break;

				// Should be part of identifiers, but not at end of "REM".
				case '\\':
					// Check for REM comments.
					if (i - currentTokenStart == 3 && (array[i - 3] == 'r' || array[i - 3] == 'R')
					        && (array[i - 2] == 'e' || array[i - 2] == 'E')
					        && (array[i - 1] == 'm' || array[i - 1] == 'M')) {
						currentTokenType = TokenTypes.COMMENT_EOL;
					}
					break;

				case '.':
				case '_':
					break; // Characters good for identifiers.

				// The "separators".
				case '(':
				case ')':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					addToken(text, i, i, TokenTypes.SEPARATOR, newStartOffset + i);
					currentTokenType = TokenTypes.NULL;
					break;

				// The "separators2".
				case ',':
				case ';':
					addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER, newStartOffset + currentTokenStart);
					addToken(text, i, i, TokenTypes.IDENTIFIER, newStartOffset + i);
					currentTokenType = TokenTypes.NULL;
					break;

				default:

					// Just to speed things up a tad, as this will usually be
					// the case.
					if (RSyntaxUtilities.isLetterOrDigit(c) || c == '\\') {
						break;
					}

					int indexOf = operators.indexOf(c);
					if (indexOf > -1) {
						addToken(text, currentTokenStart, i - 1, TokenTypes.IDENTIFIER,
						        newStartOffset + currentTokenStart);
						addToken(text, i, i, TokenTypes.OPERATOR, newStartOffset + i);
						currentTokenType = TokenTypes.NULL;
						break;
					}

					// Otherwise, fall through and assume we're still okay as an
					// IDENTIFIER...

				} // End of switch (c).

				break;

			case TokenTypes.PREPROCESSOR: // Used for labels
				i = end - 1;
				addToken(text, currentTokenStart, i, TokenTypes.PREPROCESSOR, newStartOffset + currentTokenStart);
				// We need to set token type to null so at the bottom we don't
				// add one more token.
				currentTokenType = TokenTypes.NULL;
				break;

			case TokenTypes.ERROR_STRING_DOUBLE:

				if (c == '"') {
					addToken(text, currentTokenStart, i, TokenTypes.LITERAL_STRING_DOUBLE_QUOTE,
					        newStartOffset + currentTokenStart);
					currentTokenStart = i + 1;
					currentTokenType = TokenTypes.NULL;
				}
				// Otherwise, we're still an unclosed string...

				break;

			case TokenTypes.VARIABLE:

				if (i == currentTokenStart + 1) { // first character after '%'.
					varType = VariableType.NORMAL_VAR;
					switch (c) {
					case '{':
						varType = VariableType.BRACKET_VAR;
						break;
					case '~':
						varType = VariableType.TILDE_VAR;
						break;
					case '%':
						varType = VariableType.DOUBLE_PERCENT_VAR;
						break;
					default:
						if (RSyntaxUtilities.isLetter(c) || c == '_' || c == ' ') { // No
						                                                            // tab,
						                                                            // just
						                                                            // space;
						                                                            // spaces
						                                                            // are
						                                                            // okay
						                                                            // in
						                                                            // variable
						                                                            // names.
							break;
						} else if (RSyntaxUtilities.isDigit(c)) { // Single-digit
						                                          // command-line
						                                          // argument
						                                          // ("%1").
							addToken(text, currentTokenStart, i, TokenTypes.VARIABLE,
							        newStartOffset + currentTokenStart);
							currentTokenType = TokenTypes.NULL;
							break;
						} else { // Anything else, ???.
							addToken(text, currentTokenStart, i - 1, TokenTypes.VARIABLE,
							        newStartOffset + currentTokenStart); // ???
							i--;
							currentTokenType = TokenTypes.NULL;
							break;
						}
					} // End of switch (c).
				} else { // Character other than first after the '%'.
					switch (varType) {
					case BRACKET_VAR:
						if (c == '}') {
							addToken(text, currentTokenStart, i, TokenTypes.VARIABLE,
							        newStartOffset + currentTokenStart);
							currentTokenType = TokenTypes.NULL;
						}
						break;
					case TILDE_VAR:
						if (!RSyntaxUtilities.isLetterOrDigit(c)) {
							addToken(text, currentTokenStart, i - 1, TokenTypes.VARIABLE,
							        newStartOffset + currentTokenStart);
							i--;
							currentTokenType = TokenTypes.NULL;
						}
						break;
					case DOUBLE_PERCENT_VAR:
						// Can be terminated with "%%", or (essentially) a
						// space.
						// substring chars are valid
						if (c == '%') {
							if (i < end - 1 && array[i + 1] == '%') {
								i++;
								addToken(text, currentTokenStart, i, TokenTypes.VARIABLE,
								        newStartOffset + currentTokenStart);
								currentTokenType = TokenTypes.NULL;
							}
						} else if (!RSyntaxUtilities.isLetterOrDigit(c) && c != ':' && c != '~' && c != ','
						        && c != '-') {
							addToken(text, currentTokenStart, i - 1, TokenTypes.VARIABLE,
							        newStartOffset + currentTokenStart);
							currentTokenType = TokenTypes.NULL;
							i--;
						}
						break;
					default:
						if (c == '%') {
							addToken(text, currentTokenStart, i, TokenTypes.VARIABLE,
							        newStartOffset + currentTokenStart);
							currentTokenType = TokenTypes.NULL;
						}
						break;
					}
				}
				break;

			} // End of switch (currentTokenType).

		} // End of for (int i=offset; i<end; i++).

		// Deal with the (possibly there) last token.
		if (currentTokenType != TokenTypes.NULL) {

			// Check for REM comments.
			if (end - currentTokenStart == 3 && (array[end - 3] == 'r' || array[end - 3] == 'R')
			        && (array[end - 2] == 'e' || array[end - 2] == 'E')
			        && (array[end - 1] == 'm' || array[end - 1] == 'M')) {
				currentTokenType = TokenTypes.COMMENT_EOL;
			}

			addToken(text, currentTokenStart, end - 1, currentTokenType, newStartOffset + currentTokenStart);
		}

		addNullToken();

		// Return the first token in our linked list.
		return firstToken;

	}

	private enum VariableType {
		BRACKET_VAR, TILDE_VAR, NORMAL_VAR, DOUBLE_PERCENT_VAR; // Escaped '%'
		                                                        // var, special
		                                                        // highlighting
		                                                        // rules?
	}

}