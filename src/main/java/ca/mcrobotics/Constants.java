// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package ca.mcrobotics;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.pathplanner.lib.util.PIDConstants;
import com.revrobotics.CANSparkBase.IdleMode;

import ca.lib.config.SwerveModuleConstants;
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
    public static final int MAIN_XBOX = 0;
    public static final int OVER_XBOX = 1;
  }

  public static class Common {
    public static final double SHUFFLEBOARD_UPDATE_INTERVAL = 0.3;
    public static final String DEFAULT_AUTON = "TestAuton";
    public static final double GYRO_OFFSET = 0;
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
    public static final double kTrackWidth = Units.inchesToMeters(21);
    public static final double kWheelBase = Units.inchesToMeters(25.5);

    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
      new Translation2d(kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    public static final double stickDeadband = 0.1;

    public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

    /* Drivetrain Constants */
    public static final double trackWidth = Units.inchesToMeters(21.73);
    public static final double wheelBase = Units.inchesToMeters(21.73);
    public static final double wheelDiameter = Units.inchesToMeters(4.0);
    public static final double wheelCircumference = wheelDiameter * Math.PI;

    public static final double openLoopRamp = 0.25;
    public static final double closedLoopRamp = 0.0;

    public static final double driveGearRatio = (6.12 / 1.0); // 6.75:1
    public static final double angleGearRatio = (7/150); // 150/7:1
    
    /* Swerve Voltage Compensation */
    public static final double voltageComp = 12.0;

    /* Swerve Current Limiting */
    public static final int angleContinuousCurrentLimit = 20;
    public static final int driveContinuousCurrentLimit = 80;

    /* Angle Motor PID Values */
    public static final double angleKP = 0.01;
    public static final double angleKI = 0.0;
    public static final double angleKD = 0.0;
    public static final double angleKFF = 0.0;

    /* Drive Motor PID Values */
    public static final double driveKP = 0.1;
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveKFF = 0.0;

    /* Drive Motor Characterization Values */
    public static final double driveKS = 0.667;
    public static final double driveKV = 2.44;
    public static final double driveKA = 0.27;

    /* Drive Motor Conversion Factors */
    public static final double driveConversionPositionFactor =
        (wheelDiameter * Math.PI) / driveGearRatio;
    public static final double driveConversionVelocityFactor = driveConversionPositionFactor / 60.0;
    public static final double angleConversionFactor = 360.0 / angleGearRatio;

    /* Swerve Profiling Values */
    public static final double maxSpeed = 4.5; // meters per second
    public static final double maxAngularVelocity = 11.5;

    /* Neutral Modes */
    public static final IdleMode angleNeutralMode = IdleMode.kBrake;
    public static final IdleMode driveNeutralMode = IdleMode.kBrake;

    /* Motor Inverts */
    public static final boolean driveInvert = false;
    public static final boolean angleInvert = false;

    /* Angle Encoder Invert */
    public static final boolean canCoderInvert = false;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */  
    public static final class Mod0 {
      public static final int DRIVE_MOTOR_ID = 2;
      public static final int ANGLE_MOTOR_ID = 1;
      public static final int CAN_CODER_ID = 9;
      public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0); //327.48046875
      public static final SwerveModuleConstants CONSTANTS =
          new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 {
      public static final int DRIVE_MOTOR_ID = 4;
      public static final int ANGLE_MOTOR_ID = 3;
      public static final int CAN_CODER_ID = 10;
      public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0); //286.34765625
      public static final SwerveModuleConstants CONSTANTS =
          new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
    }

    /* Back Left Module - Module 2 */
    public static final class Mod2 {
      public static final int DRIVE_MOTOR_ID = 6;
      public static final int ANGLE_MOTOR_ID = 5;
      public static final int CAN_CODER_ID = 11;
      public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0); //55.01953125
      public static final SwerveModuleConstants CONSTANTS =
          new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 {
      public static final int DRIVE_MOTOR_ID = 8;
      public static final int ANGLE_MOTOR_ID = 7;
      public static final int CAN_CODER_ID = 12;
      public static final Rotation2d ANGLE_OFFSET = Rotation2d.fromDegrees(0); //67.939453125
      public static final SwerveModuleConstants CONSTANTS =
          new SwerveModuleConstants(DRIVE_MOTOR_ID, ANGLE_MOTOR_ID, CAN_CODER_ID, ANGLE_OFFSET);
    }
    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
    public static final PIDConstants ANGLE_PID   = new PIDConstants(0.4, 0, 0.01);
    public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
    public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
    public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag
    public static final double LEFT_X_DEADBAND  = 0.1;
    public static final double LEFT_Y_DEADBAND  = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
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
      public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
          new TrapezoidProfile.Constraints(
              kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static class Flywheel {
    // CAN IDs for motors
    public static final int FLYWHEEL1_CAN = 1;
    public static final int FLYWHEEL2_CAN = 2;
    public static final double PEAK_SPEED = 0.2;
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
}
