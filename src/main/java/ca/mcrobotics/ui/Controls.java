package ca.mcrobotics.ui;

import ca.mcrobotics.*;
import ca.mcrobotics.commands.CommandSwerve;
import ca.mcrobotics.subsystems.Swerve;
import ca.mcrobotics.Constants.Features;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Controls {
  private Robot robot;
  private XboxController main;
  private XboxController aux;

  /* Drive Controls */
  private int translationAxis = XboxController.Axis.kLeftY.value;
  private int strafeAxis = XboxController.Axis.kLeftX.value;
  private int rotationAxis = XboxController.Axis.kRightX.value;

  private SlewRateLimiter translationLimiter = new SlewRateLimiter(3.0);
  private SlewRateLimiter strafeLimiter = new SlewRateLimiter(3.0);
  private SlewRateLimiter rotationLimiter = new SlewRateLimiter(3.0);


  double lastDataSendTime;
  ShuffleboardTab tab;
  GenericEntry leftX;
  GenericEntry leftY;
  GenericEntry rightX;
  GenericEntry rightY;

  public Controls(Robot robot) {
    this.robot = robot;
    main = new XboxController(0);
    aux = new XboxController(1);

    translationAxis = XboxController.Axis.kLeftY.value;
    strafeAxis = XboxController.Axis.kLeftX.value;
    rotationAxis = XboxController.Axis.kRightX.value;
    
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
    return main;
  }

  public XboxController getAuxController() {
    return aux;
  }

  public void teleopPeriodic() {
    translationAxis = XboxController.Axis.kLeftY.value;
    strafeAxis = XboxController.Axis.kLeftX.value;
    rotationAxis = XboxController.Axis.kRightX.value;

    robot.s_swerve.drive(
      new Translation2d(
        translationLimiter.calculate(MathUtil.applyDeadband(-main.getRawAxis(translationAxis), Constants.Drive.stickDeadband)),
        strafeLimiter.calculate(MathUtil.applyDeadband(-main.getRawAxis(strafeAxis), Constants.Drive.stickDeadband))).times(Constants.Drive.maxSpeed),
        rotationLimiter.calculate(MathUtil.applyDeadband(-main.getRawAxis(rotationAxis), Constants.Drive.stickDeadband)) * Constants.Drive.maxAngularVelocity,
      true,
      true);
    robot.s_flywheel.move(main.getLeftTriggerAxis());
    
    if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      lastDataSendTime = Util.getSeconds();

      leftX.setDouble(main.getLeftX());
      leftY.setDouble(main.getLeftY());
      rightX.setDouble(main.getRightX());
      rightY.setDouble(main.getRightY());

    }
  }
}
