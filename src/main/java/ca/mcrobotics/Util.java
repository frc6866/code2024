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
    return (double) LocalTime.now().toNanoOfDay() / 1_000_000_000.0;
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
    for (Field field : Features.class.getDeclaredFields()) {
      if (Modifier.isStatic(field.getModifiers()) && field.getType() == Boolean.TYPE) {
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
    while (d > 180) {
      d -= 360;
    }
    return d;
  }

  public static double mod360(double d) {
    while (d < 0) {
      d += 360;
    }
    while (d > 360) {
      d -= 360;
    }
    return d;
  }

  public static FunctionalCommand singleLambdaCommand(Runnable fn) {
    return new FunctionalCommand(fn, () -> {
    }, (_x) -> {
    }, () -> true);
  }

  /**
   * Steps a value towards a target with a specified step size.
   * 
   * @param _current  The current or starting value. Can be positive or negative.
   * @param _target   The target value the algorithm will step towards. Can be
   *                  positive or negative.
   * @param _stepsize The maximum step size that can be taken.
   * @return The new value for {@code _current} after performing the specified
   *         step towards the specified target.
   */
  public static double StepTowards(double _current, double _target, double _stepsize) {
    if (Math.abs(_current - _target) <= _stepsize) {
      return _target;
    } else if (_target < _current) {
      return _current - _stepsize;
    } else {
      return _current + _stepsize;
    }
  }

  /**
   * Steps a value (angle) towards a target (angle) taking the shortest path with
   * a specified step size.
   * 
   * @param _current  The current or starting angle (in radians). Can lie outside
   *                  the 0 to 2*PI range.
   * @param _target   The target angle (in radians) the algorithm will step
   *                  towards. Can lie outside the 0 to 2*PI range.
   * @param _stepsize The maximum step size that can be taken (in radians).
   * @return The new angle (in radians) for {@code _current} after performing the
   *         specified step towards the specified target.
   *         This value will always lie in the range 0 to 2*PI (exclusive).
   */
  public static double StepTowardsCircular(double _current, double _target, double _stepsize) {
    _current = WrapAngle(_current);
    _target = WrapAngle(_target);

    double stepDirection = Math.signum(_target - _current);
    double difference = Math.abs(_current - _target);

    if (difference <= _stepsize) {
      return _target;
    } else if (difference > Math.PI) { // does the system need to wrap over eventually?
      // handle the special case where you can reach the target in one step while also
      // wrapping
      if (_current + 2 * Math.PI - _target < _stepsize || _target + 2 * Math.PI - _current < _stepsize) {
        return _target;
      } else {
        return WrapAngle(_current - stepDirection * _stepsize); // this will handle wrapping gracefully
      }

    } else {
      return _current + stepDirection * _stepsize;
    }
  }

  /**
   * Finds the (unsigned) minimum difference between two angles including
   * calculating across 0.
   * 
   * @param _angleA An angle (in radians).
   * @param _angleB An angle (in radians).
   * @return The (unsigned) minimum difference between the two angles (in
   *         radians).
   */
  public static double AngleDifference(double _angleA, double _angleB) {
    double difference = Math.abs(_angleA - _angleB);
    return difference > Math.PI ? (2 * Math.PI) - difference : difference;
  }

  /**
   * Wraps an angle until it lies within the range from 0 to 2*PI (exclusive).
   * 
   * @param _angle The angle (in radians) to wrap. Can be positive or negative and
   *               can lie multiple wraps outside the output range.
   * @return An angle (in radians) from 0 and 2*PI (exclusive).
   */
  public static double WrapAngle(double _angle) {
    double twoPi = 2 * Math.PI;

    if (_angle == twoPi) { // Handle this case separately to avoid floating point errors with the floor
                           // after the division in the case below
      return 0.0;
    } else if (_angle > twoPi) {
      return _angle - twoPi * Math.floor(_angle / twoPi);
    } else if (_angle < 0.0) {
      return _angle + twoPi * (Math.floor((-_angle) / twoPi) + 1);
    } else {
      return _angle;
    }
  }
}
