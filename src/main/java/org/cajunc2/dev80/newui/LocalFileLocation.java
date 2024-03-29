package org.cajunc2.dev80.newui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.fife.ui.rsyntaxtextarea.FileLocation;

public class LocalFileLocation extends FileLocation {

      /**
       * The file. This may or may not actually exist.
       */
      private File file;

      /**
       * Constructor.
       *
       * @param file The local file.
       */
      LocalFileLocation(File file) {
            this.file = file;
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected long getActualLastModified() {
            return file.lastModified();
      }

      /**
       * Returns the full path to the file.
       *
       * @return The full path to the file.
       * @see #getFileName()
       */
      @Override
      public String getFileFullPath() {
            return file.getAbsolutePath();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public String getFileName() {
            return file.getName();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected InputStream getInputStream() throws IOException {
            return new FileInputStream(file);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected OutputStream getOutputStream() throws IOException {
            return new FileOutputStream(file);
      }

      /**
       * Always returns <code>true</code>.
       *
       * @return <code>true</code> always.
       * @see #isLocalAndExists()
       */
      @Override
      public boolean isLocal() {
            return true;
      }

      /**
       * Since file locations of this type are guaranteed to be local, this method
       * returns whether the file exists.
       *
       * @return Whether this local file actually exists.
       * @see #isLocal()
       */
      @Override
      public boolean isLocalAndExists() {
            return file.exists();
      }

}
