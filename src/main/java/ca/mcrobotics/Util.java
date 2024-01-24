package ca.mcrobotics;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalTime;
import java.util.Random;

import ca.mcrobotics.Constants.Features;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;

public class Util {
  public static Random globalRandom = new Random();

  public static final double getSeconds() {
    return (double)LocalTime.now().toNanoOfDay() / 1_000_000_000.0;
  }

  public static final boolean shouldUpdateShuffleboard(double lastDataSendTime) {
    return Util.getSeconds() > lastDataSendTime + Constants.Common.SHUFFLEBOARD_UPDATE_INTERVAL;
  }

  public static final double staggerUpdates() {
    return getSeconds() + globalRandom.nextDouble();
  }

  public static void logInfo(Object me, String msg) {
    System.out.printf("[%s] [INFO] %s\n", me.getClass().getSimpleName(), msg);
  }

  public static void logErr(Object me, String msg) {
    System.out.printf("[%s] [ERR] %s\n", me.getClass().getSimpleName(), msg);
  }

  public static void logWarn(Object me, String msg) {
    System.out.printf("[%s] [WARN] %s\n", me.getClass().getSimpleName(), msg);
  }

  public static void printAllFeatures(Object sender) {
    Util.logInfo(sender, "Feature Config:");
    for(Field field : Features.class.getDeclaredFields()) {
      if(Modifier.isStatic(field.getModifiers()) && field.getType() == Boolean.TYPE) {
        try {
          Util.logInfo(sender, "  " + field.getName() + ": " + field.getBoolean(null));
        } catch (IllegalArgumentException e) {
          Util.logErr(sender, "Failed to list features");
        } catch (IllegalAccessException e) {
          Util.logErr(sender, "Failed to list features");
        }
      }
    }
  }

  public static GenericEntry makeEntry(ShuffleboardTab tab, String title, int x, int y, int w, int h) {
    return tab.add(title, "<no data>").withPosition(x, y).withSize(w, h).getEntry();
  }

  public static void putEntry(GenericEntry entry, Object o) {
    entry.setString(o.toString());
  }

  public static double mod360Around0(double d) {
    while (d < -180) {
      d += 360;
    }
    while(d > 180) {
      d -= 360;
    }
    return d;
  }

  public static double mod360(double d) {
    while (d < 0) {
      d += 360;
    }
    while(d > 360) {
      d -= 360;
    }
    return d;
  }

  public static FunctionalCommand singleLambdaCommand(Runnable fn) {
    return new FunctionalCommand(fn, () -> {}, (_x) -> {}, () -> true);
  }

  public static double clamp(double x, double min, double max) {
    return Math.max(Math.min(x, max), min);
  }
}
