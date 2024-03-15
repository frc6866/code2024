// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package ca.mcrobotics;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

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
    public static final int MASTER_1 = 0; //Drivetrain
    public static final int MASTER_2 = 1; //Everything else
  }

  public static class Common {
    public static final double SHUFFLEBOARD_UPDATE_INTERVAL = 0.3;
    public static final String DEFAULT_AUTON = "TestAuton";
    public static final double GYRO_OFFSET = 90;
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
      return ENABLE_PARAMTWEAKER && ENABLE_CONTROLS && ENABLE_DRIVETRAIN && ENABLE_GYRO && ENABLE_FLYWHEEL
          && ENABLE_INTAKE && ENABLE_CLIMB;
    }
  }

  public static class Drive {
    public static final double kTrackWidth = Units.inchesToMeters(19.75);
    // Distance between right and left wheels
    public static final double kWheelBase = Units.inchesToMeters(19.75);
    // Distance between front and back wheels
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2));

    public static final int kFrontLeftDriveMotorPort = 6;
    public static final int kFrontRightDriveMotorPort = 8;
    public static final int kBackLeftDriveMotorPort = 4;
    public static final int kBackRightDriveMotorPort = 2;

    public static final int kFrontLeftTurningMotorPort = 5;
    public static final int kFrontRightTurningMotorPort = 7;
    public static final int kBackLeftTurningMotorPort = 3;
    public static final int kBackRightTurningMotorPort = 1;

    public static final boolean kFrontLeftTurningEncoderReversed = true;
    public static final boolean kBackLeftTurningEncoderReversed = true;
    public static final boolean kFrontRightTurningEncoderReversed = true;
    public static final boolean kBackRightTurningEncoderReversed = true;

    public static final boolean kFrontLeftDriveEncoderReversed = true;
    public static final boolean kBackLeftDriveEncoderReversed = true;
    public static final boolean kFrontRightDriveEncoderReversed = false;
    public static final boolean kBackRightDriveEncoderReversed = false;
    
    public static final int kFrontLeftDriveAbsoluteEncoderPort = 10; //Dead. Built in encoders
    public static final int kFrontRightDriveAbsoluteEncoderPort = 9;
    public static final int kBackLeftDriveAbsoluteEncoderPort = 11;
    public static final int kBackRightDriveAbsoluteEncoderPort = 12; //Dead. Built in encoders

    public static final boolean kFrontLeftDriveAbsoluteEncoderReversed = false;
    public static final boolean kBackLeftDriveAbsoluteEncoderReversed = false;
    public static final boolean kFrontRightDriveAbsoluteEncoderReversed = false;
    public static final boolean kBackRightDriveAbsoluteEncoderReversed = false;

    public static final double kFrontLeftDriveAbsoluteEncoderOffsetRad = 0; //Neo encoder
    public static final double kFrontRightDriveAbsoluteEncoderOffsetRad = 0.249755859375; 
    public static final double kBackLeftDriveAbsoluteEncoderOffsetRad = 0.33740234375;
    public static final double kBackRightDriveAbsoluteEncoderOffsetRad = 0; //Neo encoder

    public static final double kPhysicalMaxSpeedMetersPerSecond = 5;
    public static final double kPhysicalMaxAngularSpeedRadiansPerSecond = 2 * 2 * Math.PI;

    public static final double kTeleDriveMaxSpeedMetersPerSecond = kPhysicalMaxSpeedMetersPerSecond / 4;
    public static final double kTeleDriveMaxAngularSpeedRadiansPerSecond = kPhysicalMaxAngularSpeedRadiansPerSecond / 4;
    public static final double kTeleDriveMaxAccelerationUnitsPerSecond = 3;
    public static final double kTeleDriveMaxAngularAccelerationUnitsPerSecond = 3;

    public static final double kWheelDiameterMeters = Units.inchesToMeters(4);
    public static final double kDriveMotorGearRatio = 1/6.12;
    public static final double kTurningMotorGearRatio = 7/150;
    public static final double kDriveEncoderRot2Meter = kDriveMotorGearRatio * Math.PI * kWheelDiameterMeters;
    public static final double kTurningEncoderRot2Rad = kTurningMotorGearRatio * 2 * Math.PI;
    public static final double kDriveEncoderRPM2MeterPerSec = kDriveEncoderRot2Meter / 60;
    public static final double kTurningEncoderRPM2RadPerSec = kTurningEncoderRot2Rad / 60;
    public static final double kPTurning = 0.01;
    public static final double kITurning = 0;
    public static final double kDTurning = 0;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    // Constraint for the motion profilied robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static class Flywheel {
    // CAN IDs for motors
    public static final int TRANSFER_CAN = 14;
    public static final double PEAK_SPEED_TRANSFER = 0.3;
    public static final int FLYWHEEL1_CAN = 15;
    public static final int FLYWHEEL2_CAN = 16;
    public static final double PEAK_SPEED_FLYWHEEL = 1;
  }

  public static class Amp {
  // CAN IDs for motors
    public static final int AMP_CAN = 13;
    public static final double MAX_SPEED_IN = 0.25;
    public static final double MAX_SPEED_OUT = 1;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;

    public static final int kDriverYAxis = 1;
    public static final int kDriverXAxis = 0;
    public static final int kDriverRotAxis = 4;
    public static final int kDriverFieldOrientedButtonIdx = 1;

    public static final double kDeadband = 0.1;
  }
}