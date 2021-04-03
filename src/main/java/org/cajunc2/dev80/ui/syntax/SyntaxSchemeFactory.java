package org.cajunc2.dev80.ui.syntax;

import org.cajunc2.dev80.Main;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;

public class SyntaxSchemeFactory {
      public SyntaxScheme getDefault() {
            return Main.DARK_MODE ? new DarkSyntaxScheme() : new LightSyntaxScheme();
      }

}