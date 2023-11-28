// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package ca.mcrobotics;

import ca.mcrobotics.subsystems.*;
import ca.mcrobotics.subsystems.driveTrain.Swerve;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final CommandXboxController mainXbox = new CommandXboxController(Constants.CONTROL.MAIN_XBOX);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
    /*Command tankDrive = Commands.runEnd(
        () -> drive.drive(mainXbox.getLeftX(), mainXbox.getLeftY(), mainXbox.getRightY()),
        () -> drive.stop(),
        drive);
    overXbox.rightBumper().whileTrue(tankDrive);

    Command tankDriveOver = Commands.runEnd(
        () -> drive.drive(overXbox.getLeftX(), overXbox.getLeftY(), overXbox.getRightY()),
        () -> drive.stop(),
        drive);
    overXbox.rightBumper().whileFalse(tankDriveOver);

    Command liftArm = Commands.runEnd(
        () -> arm.setPosition(Math.max(mainXbox.getLeftTriggerAxis(), mainXbox.getRightTriggerAxis())),
        () -> arm.stop(),
        arm);
    overXbox.rightBumper().whileTrue(liftArm);

    Command liftArmOver = Commands.runEnd(
        () -> arm.setPosition(Math.max(overXbox.getLeftTriggerAxis(), overXbox.getRightTriggerAxis())),
        () -> arm.stop(),
        arm);
    overXbox.rightBumper().whileFalse(liftArmOver);

    Command climp = Commands.runOnce(() -> clamp.toggleClamp(), clamp);
    overXbox.rightBumper().and(mainXbox.x()).whileTrue(climp);
    overXbox.x().whileTrue(climp);
  }
  */
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
