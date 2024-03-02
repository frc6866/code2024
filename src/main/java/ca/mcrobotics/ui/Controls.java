package ca.mcrobotics.ui;

import ca.mcrobotics.*;
import ca.mcrobotics.commands.CommandSwerve;
import ca.mcrobotics.subsystems.Swerve;
import ca.mcrobotics.Constants.Features;
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
    main = new XboxController(0);
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
    return main;
  }

  public XboxController getAuxController() {
    return aux;
  }

  public void teleopPeriodic() {
    if(Features.ENABLE_DRIVETRAIN) {
      robot.s_swerve.setDefaultCommand(new CommandSwerve(
      robot.s_swerve,
      () -> -main.getRawAxis(translationAxis),
      () -> -main.getRawAxis(strafeAxis),
      () -> -main.getRawAxis(rotationAxis)));
    }

    if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      lastDataSendTime = Util.getSeconds();

      leftX.setDouble(main.getLeftX());
      leftY.setDouble(main.getLeftY());
      rightX.setDouble(main.getRightX());
      rightY.setDouble(main.getRightY());

    }
  }
}
