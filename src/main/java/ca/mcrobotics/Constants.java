// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package ca.mcrobotics;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class CONTROL {
    public static final int MAIN_XBOX = 0;
    public static final int OVER_XBOX = 1;
  }

  public static class Common {
    public static final double SHUFFLEBOARD_UPDATE_INTERVAL = 0.3;

    public static final String DEFAULT_AUTON = "Pain";
  }

  public static class Features {
    public static final boolean ENABLE_PARAMTWEAKER = true;
    public static final boolean ENABLE_CONTROLS = true;
    
    public static final boolean ENABLE_DRIVETRAIN = true;
    public static final boolean ENABLE_GYRO = true;
    public static final boolean ENABLE_FLYWHEEL = true;
    public static final boolean ENABLE_INTAKE = true;
    public static final boolean ENABLE_CLIMB = true;

    public static boolean everything() {
      return ENABLE_PARAMTWEAKER && ENABLE_CONTROLS && ENABLE_DRIVETRAIN && ENABLE_GYRO && ENABLE_FLYWHEEL && ENABLE_INTAKE && ENABLE_CLIMB;
    }
  }
  
  public static class Drive {
    //PID stuff
    public static final double kWheelDiameterMeters = Units.inchesToMeters(4);
    public static final double kDriveMotorGearRatio = 1 / 5.8462;
    public static final double kTurningMotorGearRatio = 1 / 18.0;
    public static final double kDriveEncoderRot2Meter = kDriveMotorGearRatio * Math.PI * kWheelDiameterMeters;
    public static final double kTurningEncoderRot2Rad = kTurningMotorGearRatio * 2 * Math.PI;
    public static final double kDriveEncoderRPM2MeterPerSec = kDriveEncoderRot2Meter / 60;
    public static final double kTurningEncoderRPM2RadPerSec = kTurningEncoderRot2Rad / 60;
    public static final double kPTurning = 0.5;

    public static final double kTrackWidth = Units.inchesToMeters(21);
    public static final double kWheelBase = Units.inchesToMeters(25.5);

    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
      new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
      new Translation2d(kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, kTrackWidth / 2));

    // CAN IDs for drivetrain motors
    public static final int FRONT_LEFT_SPEED = 0;
    public static final int FRONT_RIGHT_SPEED = 1;
    public static final int BACK_LEFT_SPEED = 2;
    public static final int BACK_RIGHT_SPEED = 3;

    public static final int FRONT_LEFT_ROTATION = 4;
    public static final int FRONT_RIGHT_ROTATION = 5;
    public static final int BACK_LEFT_ROTATION = 6;
    public static final int BACK_RIGHT_ROTATION = 7;    

    //Encoder for speed/direction motor
    public static final int FRONT_LEFT_SPEED_ENODER_A = 0;
    public static final int FRONT_LEFT_SPEED_ENODER_B = 1;
    public static final int FRONT_RIGHT_SPEED_ENODER_A = 2;
    public static final int FRONT_RIGHT_SPEED_ENODER_B = 3;

    public static final int BACK_LEFT_SPEED_ENODER_A = 4;
    public static final int BACK_LEFT_SPEED_ENODER_B = 5;
    public static final int BACK_RIGHT_SPEED_ENODER_A = 6;
    public static final int BACK_RIGHT_SPEED_ENODER_B = 7;

    //Encoder for rotation motor
    public static final int FRONT_LEFT_ROTATION_ENODER_A = 8;
    public static final int FRONT_LEFT_ROTATION_ENODER_B = 9;
    public static final int FRONT_RIGHT_ROTATION_ENODER_A = 10;
    public static final int FRONT_RIGHT_ROTATION_ENODER_B = 11;

    public static final int BACK_LEFT_ROTATION_ENODER_A = 12;
    public static final int BACK_LEFT_ROTATION_ENODER_B = 13;
    public static final int BACK_RIGHT_ROTATION_ENODER_A = 14;
    public static final int BACK_RIGHT_ROTATION_ENODER_B = 15;

    public static final boolean FRONT_LEFT_SPEED_REVERSED = true;
    public static final boolean FRONT_RIGHT_SPEED_REVERSED = false;
    public static final boolean BACK_LEFT_SPEED_REVERSED = true;
    public static final boolean BACK_RIGHT_SPEED_REVERSED = false;

    public static final double FRONT_LEFT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD = -0.254;
    public static final double FRONT_RIGHT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD = -1.252;
    public static final double BACK_LEFT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD = -1.816;
    public static final double BACK_RIGHT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD = -4.811;

    // speed
    public static final double SPEED = 0.3;
    public static final double kPhysicalMaxSpeedMetersPerSecond = 5;
  }

  public static class Flywheel {
    // CAN IDs for motors
    public static final int FLYWHEEL1_CAN = 1;
    public static final int FLYWHEEL2_CAN = 2;
  }

  public static class Intake {
    // CAN IDs for motors
    public static final int INTAKE1_CAN = 3;
    public static final int INTAKE2_CAN = 4;
  }

  public static class Climb {
    // CAN IDs for motors
    public static final int CLIMB1_CAN = 5;
    public static final int CLIMB2_CAN = 6;
  }

  public static class Template {
    public static final int TEMPLATE_CAN = 7;
    public static double peakSpeed = 0.2;
  }
}
