package ca.mcrobotics.ui;

import ca.mcrobotics.*;
import ca.mcrobotics.commands.CommandSwerve;
import ca.mcrobotics.subsystems.Swerve;
import ca.mcrobotics.Constants.Features;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Controls {
  private Robot robot;
  public XboxController master;
  public XboxController aux;

  /* Drive Controls */
  private int translationAxis = XboxController.Axis.kLeftY.value;
  private int strafeAxis = XboxController.Axis.kLeftX.value;
  private int rotationAxis = XboxController.Axis.kRightX.value;

  /* Subsystems */
  private Swerve m_swerve = new Swerve();

  double lastDataSendTime;
  ShuffleboardTab tab;
  GenericEntry leftX;
  GenericEntry leftY;
  GenericEntry rightX;
  GenericEntry rightY;

  public Controls(Robot robot) {
    this.robot = robot;
    master = new XboxController(0);
    aux = new XboxController(1);

    lastDataSendTime = Util.staggerUpdates();
    tab = Shuffleboard.getTab("Controls");
    leftX = Util.makeEntry(tab, "Left X", 0, 0, 2, 1);
    leftY = Util.makeEntry(tab, "Left Y", 2, 0, 2, 1);
    rightX = Util.makeEntry(tab, "Right X", 0, 1, 2, 1);
    rightY = Util.makeEntry(tab, "Right Y", 2, 1, 2, 1);
  }

  public void configureKeybinds() {
  
  }

  public XboxController getMainController() {
    return master;
  }

  public XboxController getAuxController() {
    return aux;
  }

  public void teleopPeriodic() {
    if(Features.ENABLE_DRIVETRAIN) {
      robot.Swerve.setDefaultCommand(new CommandSwerve(
      robot.Swerve,
      -master.getRawAxis(translationAxis),
      -master.getRawAxis(strafeAxis),
      -master.getRawAxis(rotationAxis)));
      //this.robotCentricSup = robotCentricSup;
      // Configure default commands
      robot.Swerve.setDefaultCommand(
      // The left stick controls translation of the robot.
      // Turning is controlled by the X axis of the right stick.
      new RunCommand(
        () -> robot.Swerve.drive(
          -MathUtil.applyDeadband(master.getLeftY(), Constants.Drive.kDriveDeadband),
          -MathUtil.applyDeadband(master.getLeftX(), Constants.Drive.kDriveDeadband),
          -MathUtil.applyDeadband(master.getRightX(), Constants.Drive.kDriveDeadband),
          true, true),
        robot.Swerve));
    }

    if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      lastDataSendTime = Util.getSeconds();

      leftX.setDouble(master.getLeftX());
      leftY.setDouble(master.getLeftY());
      rightX.setDouble(master.getRightX());
      rightY.setDouble(master.getRightY());
    }
  }
}
