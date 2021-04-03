package org.cajunc2.dev80.project;

import java.util.Objects;

class WindowParam {
      int x, y, width, height;

      @Override
      public int hashCode() {
            return Objects.hash(height, width, x, y);
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
            WindowParam other = (WindowParam) obj;
            return height == other.height && width == other.width && x == other.x && y == other.y;
      }

}
