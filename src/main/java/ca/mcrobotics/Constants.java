// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package ca.mcrobotics;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkBase.IdleMode;

import ca.lib.config.SwerveModuleConstants;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
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
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)

    public static final double kTrackWidth = Units.inchesToMeters(21);
    public static final double kWheelBase = Units.inchesToMeters(25.5);
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
      new Translation2d(kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    public static final double stickDeadband = 0.1;

    public static final int kDrivingMotorPinionTeeth = 14;

    // Invert the turning encoder, since the output shaft rotates in the opposite direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean kTurningEncoderInverted = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = 5676/60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;

    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction; // meters
    public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction) / 60.0; // meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
    public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second

    public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
    public static final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians

    public static final double kDrivingP = 0.04;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    public static final double kTurningP = 1;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningFF = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;

    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 50; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps

    public static final boolean kGyroReversed = false;

    public static final double kDriveDeadband = 0.05;
    public static final boolean canCoderInvert = false;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */  
    public static final class Mod0 {
      public static final int DRIVE_MOTOR_ID = 4;
      public static final int ANGLE_MOTOR_ID = 3;
      public static final int CAN_CODER_ID = 1;
      public static final double ANGLE_OFFSET = 327.48046875;
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 {
      public static final int DRIVE_MOTOR_ID = 14;
      public static final int ANGLE_MOTOR_ID = 13;
      public static final int CAN_CODER_ID = 2;
      public static final double ANGLE_OFFSET = 286.34765625;
    }

    /* Back Left Module - Module 2 */
    public static final class Mod2 {
      public static final int DRIVE_MOTOR_ID = 2;
      public static final int ANGLE_MOTOR_ID = 1;
      public static final int CAN_CODER_ID = 3;
      public static final double ANGLE_OFFSET = 55.01953125;
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 {
      public static final int DRIVE_MOTOR_ID = 15;
      public static final int ANGLE_MOTOR_ID = 16;
      public static final int CAN_CODER_ID = 4;
      public static final double ANGLE_OFFSET = 67.939453125;
    }
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
