// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package ca.mcrobotics;

import ca.mcrobotics.subsystems.*;
import ca.mcrobotics.subsystems.Archive.Arm;
import ca.mcrobotics.subsystems.Archive.Clamp;
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
  private final Arm arm = new Arm();
  private final Clamp clamp = new Clamp();
  private final Swerve fl = new Swerve(Constants.DRIVE.FRONT_LEFT_SPEED, 
  Constants.DRIVE.FRONT_LEFT_ROTATION, 
  Constants.DRIVE.FRONT_LEFT_ROTATION_ABSOLUTE_ENCODER_REVERSED, 
  Constants.DRIVE.FRONT_LEFT_ROTATION_ABSOLUTE_ENCODER_REVERSED,
  Constants.DRIVE.FRONT_LEFT_SPEED_ENODER_A,
  Constants.DRIVE.FRONT_LEFT_SPEED_ENODER_B,
  Constants.DRIVE.FRONT_LEFT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD,
  Constants.DRIVE.FRONT_LEFT_SPEED_ABSOLUTE_ENCODER_REVERSED);
  
  private final Swerve fr = new Swerve(Constants.DRIVE.FRONT_RIGHT_SPEED, 
  Constants.DRIVE.FRONT_RIGHT_ROTATION, 
  Constants.DRIVE.FRONT_RIGHT_ROTATION_ABSOLUTE_ENCODER_REVERSED, 
  Constants.DRIVE.FRONT_RIGHT_ROTATION_ABSOLUTE_ENCODER_REVERSED,
  Constants.DRIVE.FRONT_RIGHT_SPEED_ENODER_A,
  Constants.DRIVE.FRONT_RIGHT_SPEED_ENODER_B,
  Constants.DRIVE.FRONT_RIGHT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD,
  Constants.DRIVE.FRONT_RIGHT_SPEED_ABSOLUTE_ENCODER_REVERSED);
  
  private final Swerve bl = new Swerve(Constants.DRIVE.BACK_LEFT_SPEED, 
  Constants.DRIVE.BACK_LEFT_ROTATION, 
  Constants.DRIVE.BACK_LEFT_ROTATION_ABSOLUTE_ENCODER_REVERSED,
  Constants.DRIVE.BACK_LEFT_ROTATION_ABSOLUTE_ENCODER_REVERSED,
  Constants.DRIVE.BACK_LEFT_SPEED_ENODER_A,
  Constants.DRIVE.BACK_LEFT_SPEED_ENODER_B,
  Constants.DRIVE.BACK_LEFT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD,
  Constants.DRIVE.BACK_LEFT_SPEED_ABSOLUTE_ENCODER_REVERSED);
  
  private final Swerve br = new Swerve(Constants.DRIVE.BACK_RIGHT_SPEED, 
  Constants.DRIVE.BACK_RIGHT_ROTATION, 
  Constants.DRIVE.BACK_RIGHT_ROTATION_ABSOLUTE_ENCODER_REVERSED,
  Constants.DRIVE.BACK_RIGHT_ROTATION_ABSOLUTE_ENCODER_REVERSED,
  Constants.DRIVE.BACK_RIGHT_SPEED_ENODER_A,
  Constants.DRIVE.BACK_RIGHT_SPEED_ENODER_B,
  Constants.DRIVE.BACK_RIGHT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD,
  Constants.DRIVE.BACK_RIGHT_SPEED_ABSOLUTE_ENCODER_REVERSED);

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
