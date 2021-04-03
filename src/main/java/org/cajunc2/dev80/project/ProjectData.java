package org.cajunc2.dev80.project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class ProjectData {
      String title;
      File compileFile;
      File outputFile;
      List<File> files = new ArrayList<>();
      Map<String, WindowParam> windowParams = new HashMap<>();

      @Override
      public int hashCode() {
            return Objects.hash(compileFile, files, outputFile, title, windowParams);
      }

      @Override
      public boolean equals(Object obj) {
            if (this == obj) {
                  return true;
            }
            if (obj == null) {
                  return false;
            }
            if (getClass() != obj.getClass()) {
                  return false;
            }
            ProjectData other = (ProjectData) obj;
            return Objects.equals(compileFile, other.compileFile) && Objects.equals(files, other.files)
                        && Objects.equals(outputFile, other.outputFile) && Objects.equals(title, other.title)
                        && Objects.equals(windowParams, other.windowParams);
      }

}