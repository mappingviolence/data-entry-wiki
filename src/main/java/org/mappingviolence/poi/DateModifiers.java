package org.mappingviolence.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.ImmutableList;

public final class DateModifiers {
  public final static String EARLY, MID, LATE, AROUND;
  public final static String JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;
  private final static List<String> MONTHS;

  static {
    Properties prop = new Properties();
    try (InputStream input = new FileInputStream(new File("WEB-INF/config/datemodifiers.properties"))) {
      prop.load(input);

      EARLY = prop.getProperty("early");
      MID = prop.getProperty("mid");
      LATE = prop.getProperty("late");
      AROUND = prop.getProperty("around");

      JAN = prop.getProperty("jan");
      FEB = prop.getProperty("feb");
      MAR = prop.getProperty("mar");
      APR = prop.getProperty("apr");
      MAY = prop.getProperty("may");
      JUN = prop.getProperty("jun");
      JUL = prop.getProperty("jul");
      AUG = prop.getProperty("aug");
      SEP = prop.getProperty("sep");
      OCT = prop.getProperty("oct");
      NOV = prop.getProperty("nov");
      DEC = prop.getProperty("dec");

      MONTHS = ImmutableList.<String> builder().add(JAN).add(FEB).add(MAR).add(APR).add(MAY).add(JUN).add(JUL).add(AUG)
          .add(SEP).add(OCT).add(NOV).add(DEC).build();

    } catch (FileNotFoundException e) {
      System.err.println(
          "Could not find datamodifiers.properties file. Please make sure it is in the /src/main/config directory and is named datamodifiers.properties.");
      throw new RuntimeException();
    } catch (IOException e) {
      throw new RuntimeException("There was a problem reading from the file. Please try again later.");
    }
  }

  public static boolean isValidYearModifier(String dateModifier) {
    return dateModifier.equals(EARLY) || dateModifier.equals(MID) || dateModifier.equals(LATE);
  }

  public static boolean isValidMonthModifier(String dateModifier) {
    return dateModifier.equals(EARLY) || dateModifier.equals(MID) || dateModifier.equals(LATE);
  }

  public static boolean isValidDayModifier(String dateModifier) {
    return dateModifier.equals(AROUND);
  }

  public static boolean isValidMonth(String month) {
    return MONTHS.contains(month);
  }

  public static boolean isValidMonthDate(String monthStr, String dateStr) {
    try {
      Integer date = Integer.valueOf(dateStr);
      if (date < 1 || date > 31) {
        return false;
      }
      if (date.equals(31)) {
        return monthStr.equals(JAN) || monthStr.equals(MAR) || monthStr.equals(MAY) || monthStr.equals(JUL)
            || monthStr.equals(AUG) || monthStr.equals(OCT) || monthStr.equals(DEC);
      }
      if (date.equals(30)) {
        return monthStr.equals(APR) || monthStr.equals(JUN) || monthStr.equals(SEP) || monthStr.equals(NOV);
      }
      return isValidMonth(monthStr);
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
