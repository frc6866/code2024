package ca.mcrobotics.ui;

import ca.mcrobotics.*;
import ca.mcrobotics.commands.AbsoluteDriveAdv;
import ca.mcrobotics.commands.CommandSwerve;
import ca.mcrobotics.subsystems.Swerve;
import ca.mcrobotics.Constants.Features;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Controls {
  private Robot robot;
  private CommandXboxController main;
  private CommandXboxController alt;

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
    main = new CommandXboxController(0);
    alt = new CommandXboxController(1);

    translationAxis = XboxController.Axis.kLeftY.value;
    strafeAxis = XboxController.Axis.kLeftX.value;
    rotationAxis = XboxController.Axis.kRightX.value;

    lastDataSendTime = Util.staggerUpdates();
    tab = Shuffleboard.getTab("Controls");
    leftX = Util.makeEntry(tab, "Left X", 0, 0, 2, 1);
    leftY = Util.makeEntry(tab, "Left Y", 2, 0, 2, 1);
    rightX = Util.makeEntry(tab, "Right X", 0, 1, 2, 1);
    rightY = Util.makeEntry(tab, "Right Y", 2, 1, 2, 1);

    AbsoluteDriveAdv closedAbsoluteDriveAdv = new AbsoluteDriveAdv(robot.s_swerve,
                                                                   () -> -MathUtil.applyDeadband(main.getLeftY(),
                                                                                                Constants.Drive.LEFT_Y_DEADBAND),
                                                                   () -> -MathUtil.applyDeadband(main.getLeftX(),
                                                                                                Constants.Drive.LEFT_X_DEADBAND),
                                                                   () -> -MathUtil.applyDeadband(main.getRightX(),
                                                                                                Constants.Drive.RIGHT_X_DEADBAND),
                                                                   main.getHID()::getYButtonPressed,
                                                                   main.getHID()::getAButtonPressed,
                                                                   main.getHID()::getXButtonPressed,
                                                                   main.getHID()::getBButtonPressed);

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the desired angle NOT angular rotation
    Command driveFieldOrientedDirectAngle = robot.s_swerve.driveCommand(
        () -> MathUtil.applyDeadband(main.getLeftY(), Constants.Drive.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(main.getLeftX(), Constants.Drive.LEFT_X_DEADBAND),
        () -> main.getRightX(),
        () -> main.getRightY());

    // Applies deadbands and inverts controls because joysticks
    // are back-right positive while robot
    // controls are front-left positive
    // left stick controls translation
    // right stick controls the angular velocity of the robot
    Command driveFieldOrientedAnglularVelocity = robot.s_swerve.driveCommand(
        () -> MathUtil.applyDeadband(main.getLeftY(), Constants.Drive.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(main.getLeftX(), Constants.Drive.LEFT_X_DEADBAND),
        () -> main.getRightX() * 0.5);

    Command driveFieldOrientedDirectAngleSim = robot.s_swerve.simDriveCommand(
        () -> MathUtil.applyDeadband(main.getLeftY(), Constants.Drive.LEFT_Y_DEADBAND),
        () -> MathUtil.applyDeadband(main.getLeftX(), Constants.Drive.LEFT_X_DEADBAND),
        () -> main.getRawAxis(2));

    robot.s_swerve.setDefaultCommand(
        !RobotBase.isSimulation() ? driveFieldOrientedDirectAngle : driveFieldOrientedDirectAngleSim);

  }

  public void configureKeybinds() {
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    main.a().onTrue((Commands.runOnce(robot.s_swerve::zeroGyro)));
    main.x().onTrue(Commands.runOnce(robot.s_swerve::addFakeVisionReading));
    main.b().whileTrue(
        Commands.deferredProxy(() -> robot.s_swerve.driveToPose(
                                   new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
                              ));
    // main.x().whileTrue(Commands.runOnce(robot.s_swerve::lock, robot.s_swerve).repeatedly());
  }

  public CommandXboxController getMainController() {
    return main;
  }

  public void teleopPeriodic() {
    translationAxis = XboxController.Axis.kLeftY.value;
    strafeAxis = XboxController.Axis.kLeftX.value;
    rotationAxis = XboxController.Axis.kRightX.value;



    robot.s_flywheel.move(main.getLeftTriggerAxis());

    if (Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      lastDataSendTime = Util.getSeconds();

      leftX.setDouble(main.getLeftX());
      leftY.setDouble(main.getLeftY());
      rightX.setDouble(main.getRightX());
      rightY.setDouble(main.getRightY());

    }
  }
}
