package org.mappingviolence.poi.date;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// TODO: Rewrite this class
// TODO: Remove error checking on creation, maybe not.
// Idk, let's talk to eddie about this.
// This shouldn't be an entity, but an embedded
public class Date implements Comparable<Date> {

  public boolean isValid() {
    // TODO: Implement this
    return true;
  }

  private Integer year;

  private String month;

  private Integer date;

  private String modifier;
  private static final Integer MIN_YEAR, MAX_YEAR;

  static {
    Properties prop = new Properties();
    try (InputStream input = Thread
        .currentThread()
        .getContextClassLoader()
        .getResourceAsStream("config/date.properties")) {
      prop.load(input);

      MIN_YEAR = Integer.valueOf(prop.getProperty("min_year"));
      MAX_YEAR = Integer.valueOf(prop.getProperty("max_year"));

    } catch (NumberFormatException e) {
      throw new RuntimeException("min_year key in date.properties is not an integer.");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(
          "Could not find date.properties file. Please make sure it is in the src/main/config directory and is named date.properties.");
    } catch (IOException e) {
      throw new RuntimeException(
          "There was a problem reading from the file. Please try again later.");
    }
  }

  protected Date() {
  }

  public Date(String dateStr) {
    if (dateStr == null) {
      throw new IllegalArgumentException("The provided date string cannot be null.");
    }
    String[] parts = dateStr.split(" ");
    switch (parts.length) {
      case 1:
        if (isValidYear(parts[0])) {
          this.year = parseYear(parts[0]);
        } else {
          throw new IllegalArgumentException(
              "The provided date string did match the date grammar.");
        }
        break;
      case 2:
        if (isValidModifierYear(parts[0], parts[1])) {
          this.modifier = parts[0];
          this.year = parseYear(parts[1]);
        } else if (isValidYearMonth(parts[0], parts[1])) {
          this.year = parseYear(parts[0]);
          this.month = parts[1];
        } else {
          throw new IllegalArgumentException(
              "The provided date string did match the date grammar.");
        }
        break;
      case 3:
        if (isValidYearMonthDate(parts[0], parts[1], parts[2])) {
          this.year = parseYear(parts[0]);
          this.month = parts[1];
          this.date = parseDate(parts[2]);
        } else if (isValidYearModifierMonth(parts[0], parts[1], parts[2])) {
          this.year = parseYear(parts[0]);
          this.modifier = parts[1];
          this.month = parts[2];
        } else {
          throw new IllegalArgumentException(
              "The provided date string did match the date grammar.");
        }
        break;
      case 4:
        if (isValidYearMonthModifierDate(parts[0], parts[1], parts[2], parts[3])) {
          this.year = parseYear(parts[0]);
          this.month = parts[1];
          this.modifier = parts[2];
          this.date = parseDate(parts[3]);
        } else {
          throw new IllegalArgumentException(
              "The provided date string did match the date grammar.");
        }
        break;
      default:
        throw new IllegalArgumentException("The provided date string did match the date grammar.");
    }
  }

  private static Integer parseYear(String yearStr) {
    try {
      Integer year = Integer.valueOf(yearStr);
      if (year < MIN_YEAR) {
        throw new IllegalArgumentException(
            "The provided date is earlier than the earliest allowed date.");
      }
      return year;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The provided date string did match the date grammar.");
    }
  }

  private static Integer parseDate(String dateStr) {
    try {
      Integer date = Integer.valueOf(dateStr);
      if (date < 0 || date > 31) {
        throw new IllegalArgumentException("The provided date is not a valid date.");
      }
      return date;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The provided date string did match the date grammar.");
    }
  }

  private static boolean isValidYear(String yearStr) {
    try {
      Integer year = Integer.valueOf(yearStr);
      if (year < MIN_YEAR || year > MAX_YEAR) {
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private static boolean isValidModifierYear(String modifier, String year) {
    return DateModifiers.isValidYearModifier(modifier) && isValidYear(year);
  }

  private static boolean isValidYearMonth(String year, String month) {
    return DateModifiers.isValidMonth(month) && isValidYear(year);
  }

  private static boolean isValidYearMonthDate(String year, String month, String date) {
    return isValidYear(year) && DateModifiers.isValidMonthDate(month, date);
  }

  private static boolean isValidYearModifierMonth(String yearStr, String modStr, String monthStr) {
    return isValidYear(yearStr) && DateModifiers.isValidMonthModifier(modStr)
        && DateModifiers.isValidMonth(monthStr);
  }

  private static boolean isValidYearMonthModifierDate(String yearStr, String monthStr,
      String modStr, String dateStr) {
    return isValidYear(yearStr) && DateModifiers.isValidMonthDate(monthStr, dateStr)
        && DateModifiers.isValidDayModifier(modStr);
  }

  public Integer getYear() {
    return year;
  }

  public String getMonth() {
    return month;
  }

  public Integer getDate() {
    return date;
  }

  public String getModifier() {
    return modifier;
  }

  @Override
  public int compareTo(Date o) {
    assert year != null;
    assert o.year != null;
    // if (year.equals(o.year)) {
    // if (month == null) {
    // if (o.month == null) {
    // if (modifier == null) {
    // if (o.modifier == null) {
    // return 0;
    // } else {
    // return 0;
    // }
    // }
    // }
    // }
    // } else {
    // return year.compareTo(o.year);
    // }
    return 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (year != null && month != null && modifier != null && date != null) {
      sb.append(year);
      sb.append(" ");
      sb.append(month);
      sb.append(" ");
      sb.append(modifier);
      sb.append(" ");
      sb.append(date);
    } else if (year != null && month != null && date != null && modifier == null) {
      sb.append(year);
      sb.append(" ");
      sb.append(month);
      sb.append(" ");
      sb.append(date);
    } else if (year != null && modifier != null && month != null && date == null) {
      sb.append(year);
      sb.append(" ");
      sb.append(modifier);
      sb.append(" ");
      sb.append(month);
    } else if (year != null && modifier == null && month != null && date == null) {
      sb.append(year);
      sb.append(" ");
      sb.append(month);
    } else if (year != null && modifier != null && month == null && date == null) {
      sb.append(modifier);
      sb.append(" ");
      sb.append(year);
      sb.append(" ");
    } else if (year != null && modifier == null && month == null && date == null) {
      sb.append(year);
    }
    return sb.toString();
  }
  // @Test
  // public static void main(String[] args) {
  // try {
  // Date d1 = new Date("1912");
  // assertEquals((Integer) 1912, d1.year);
  // assertEquals(null, d1.month);
  // assertEquals(null, d1.modifier);
  // assertEquals(null, d1.date);
  // } catch (Exception e) {
  // assertTrue(false);
  // }
  // try {
  // Date d1 = new Date("1903 June");
  // assertEquals((Integer) 1903, d1.year);
  // assertEquals(null, d1.month);
  // assertEquals(null, d1.modifier);
  // assertEquals(null, d1.date);
  // } catch (Exception e) {
  // assertTrue(false);
  // }
  // try {
  // Date d1 = new Date("1904 Mid July");
  // assertEquals((Integer) 1904, d1.year);
  // assertEquals(null, d1.month);
  // assertEquals(null, d1.modifier);
  // assertEquals(null, d1.date);
  // } catch (Exception e) {
  // assertTrue(false);
  // }
  // try {
  // Date d1 = new Date("1905 January 17");
  // assertEquals((Integer) 1912, d1.year);
  // assertEquals(null, d1.month);
  // assertEquals(null, d1.modifier);
  // assertEquals(null, d1.date);
  // } catch (Exception e) {
  // assertTrue(false);
  // }
  // try {
  // Date d1 = new Date("-1903 November Around 1");
  // assertEquals((Integer) 1912, d1.year);
  // assertEquals(null, d1.month);
  // assertEquals(null, d1.modifier);
  // assertEquals(null, d1.date);
  // } catch (Exception e) {
  // assertTrue(false);
  // }
  // }

  // TODO
  public static boolean isValidDate(String dateStr) {
    return true;
  }
}
