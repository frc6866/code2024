// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//I made this while I was at vrc provs lol

package ca.mcrobotics.subsystems.FailedSwerve;

import edu.wpi.first.hal.CANAPITypes.CANDeviceType;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.*;
import swervelib.encoders.CANCoderSwerve;

import com.ctre.phoenix.sensors.*;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax;
import ca.mcrobotics.*;

public class SwerveSubsystem extends SubsystemBase {
  private CANSparkMax flMotorFwd;
  private CANSparkMax frMotorFwd;
  private CANSparkMax blMotorFwd;
  private CANSparkMax brMotorFwd;

  private CANSparkMax flMotorRot;
  private CANSparkMax frMotorRot;
  private CANSparkMax blMotorRot;
  private CANSparkMax brMotorRot;

  private CANCoderSwerve flCanCoder;
  private CANCoderSwerve frCanCoder;
  private CANCoderSwerve blCanCoder;
  private CANCoderSwerve brCanCoder;

  ShuffleboardTab swerve;

  GenericEntry flFwd;
  GenericEntry frFwd;
  GenericEntry blFwd;
  GenericEntry brFwd;

  GenericEntry flRot;
  GenericEntry frRot;
  GenericEntry blRot;
  GenericEntry brRot;

  GenericEntry flEncoder;
  GenericEntry frEncoder;
  GenericEntry blEncoder;
  GenericEntry brEncoder;

  double lastDataSendTime;

  public SwerveSubsystem() {
    flMotorFwd = new CANSparkMax(Constants.Drive.Mod0.DRIVE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    frMotorFwd = new CANSparkMax(Constants.Drive.Mod1.DRIVE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    blMotorFwd = new CANSparkMax(Constants.Drive.Mod2.DRIVE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    brMotorFwd = new CANSparkMax(Constants.Drive.Mod3.DRIVE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);

    flMotorRot = new CANSparkMax(Constants.Drive.Mod0.ANGLE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    frMotorRot = new CANSparkMax(Constants.Drive.Mod1.DRIVE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    blMotorRot = new CANSparkMax(Constants.Drive.Mod2.DRIVE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);
    brMotorRot = new CANSparkMax(Constants.Drive.Mod3.DRIVE_MOTOR_ID, CANSparkMax.MotorType.kBrushless);

    flCanCoder = new CANCoderSwerve(Constants.Drive.Mod0.CAN_CODER_ID);
    frCanCoder = new CANCoderSwerve(Constants.Drive.Mod1.CAN_CODER_ID);
    blCanCoder = new CANCoderSwerve(Constants.Drive.Mod2.CAN_CODER_ID);
    brCanCoder = new CANCoderSwerve(Constants.Drive.Mod3.CAN_CODER_ID);

    lastDataSendTime = Util.staggerUpdates();
    swerve = Shuffleboard.getTab("Drivetrain");
    flFwd = Util.makeEntry(swerve, "FL Move", 0, 0, 1, 1);
    frFwd = Util.makeEntry(swerve, "FR Move", 1, 0, 1, 1);
    blFwd = Util.makeEntry(swerve, "BL Move", 2, 0, 1, 1);
    brFwd = Util.makeEntry(swerve, "BR Move", 3, 0, 1, 1);

    flRot = Util.makeEntry(swerve, "FL Rotation", 0, 1, 1, 1);
    frRot = Util.makeEntry(swerve, "FR Rotation", 1, 1, 1, 1);
    blRot = Util.makeEntry(swerve, "BL Rotation", 2, 1, 1, 1);
    brRot = Util.makeEntry(swerve, "BR Rotation", 3, 1, 1, 1);

    flEncoder = Util.makeEntry(swerve, "FL Encoder", 0, 2, 1, 1);
    frEncoder = Util.makeEntry(swerve, "FR Encoder", 1, 2, 1, 1);
    blEncoder = Util.makeEntry(swerve, "BL Encoder", 2, 2, 1, 1);
    brEncoder = Util.makeEntry(swerve, "BR Encoder", 3, 2, 1, 1);
  }
  
  public double[] getCanCoderPos() {
    double pos[] = {flCanCoder.getAbsolutePosition(), frCanCoder.getAbsolutePosition(), blCanCoder.getAbsolutePosition(), brCanCoder.getAbsolutePosition()};
    return pos;
  }

  public void setCanCoderPos(double flPos, double frPos, double blPos, double brPos) {
    flCanCoder.setAbsoluteEncoderOffset(flPos);
    frCanCoder.setAbsoluteEncoderOffset(frPos);
    blCanCoder.setAbsoluteEncoderOffset(blPos);
    brCanCoder.setAbsoluteEncoderOffset(brPos);    
  }

  public void resetCanCoder() {
    flCanCoder.setAbsoluteEncoderOffset(0);
    frCanCoder.setAbsoluteEncoderOffset(0);
    blCanCoder.setAbsoluteEncoderOffset(0);
    brCanCoder.setAbsoluteEncoderOffset(0);
  }

  /**
   * Main move function
   * 
   * @param ctrlLeftX - X axis of left joystick
   * @param ctrlLeftY - Y axis of left joystick
   * @param ctrlRightX - X axis of right joystick
   * @return void
   */
  public void move(double ctrlLeftX, double ctrlLeftY, double ctrlRightX) {
    ctrlLeftX *= 0.3;
    ctrlLeftY *= 0.3;
    ctrlRightX *= 0.3;
    //Prototype. Very bad.
    //Movement
    flMotorFwd.set(ctrlLeftX+ctrlRightX);
    frMotorFwd.set(ctrlLeftX-ctrlRightX);
    blMotorFwd.set(ctrlLeftX+ctrlRightX);
    brMotorFwd.set(ctrlLeftX-ctrlRightX);

    //Rotation
    flMotorRot.set(ctrlLeftY+ctrlRightX);
    frMotorRot.set(ctrlLeftY+ctrlRightX);
    blMotorRot.set(ctrlLeftY+ctrlRightX);
    brMotorRot.set(ctrlLeftY+ctrlRightX);
  }

  public void stopMove() {
    flMotorFwd.stopMotor();
    frMotorFwd.stopMotor();
    blMotorFwd.stopMotor();
    brMotorFwd.stopMotor();
  }

  public void stopRot() {
    flMotorRot.stopMotor();
    frMotorRot.stopMotor();
    blMotorRot.stopMotor();
    brMotorRot.stopMotor();
  }

  public void stop() {
    stopMove();
    stopRot();
  }

  @Override
  public void periodic() {
    if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      lastDataSendTime = Util.getSeconds();
      Util.putEntry(flFwd, flMotorFwd.get());
      Util.putEntry(frFwd, frMotorFwd.get());
      Util.putEntry(blFwd, blMotorFwd.get());
      Util.putEntry(brFwd, brMotorFwd.get());

      Util.putEntry(flRot, flMotorRot.get());
      Util.putEntry(frRot, frMotorRot.get());
      Util.putEntry(blRot, blMotorRot.get());
      Util.putEntry(brRot, brMotorRot.get());

      Util.putEntry(flEncoder, flCanCoder.getAbsolutePosition());
      Util.putEntry(frEncoder, frCanCoder.getAbsolutePosition());
      Util.putEntry(blEncoder, blCanCoder.getAbsolutePosition());
      Util.putEntry(brEncoder, brCanCoder.getAbsolutePosition());
    }
  }

public Object setSpeedModifier(double d) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'setSpeedModifier'");
}
}
