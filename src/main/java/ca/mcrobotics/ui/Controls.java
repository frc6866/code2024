package ca.mcrobotics.ui;

import java.io.Console;

import ca.mcrobotics.Robot;
import ca.mcrobotics.Constants.OIConstants;
import ca.mcrobotics.commands.SwerveCommandJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Controls {
    Robot robot;

    XboxController main = new XboxController(0);

    public Controls(Robot robot) {
        this.robot = robot;
    }

    public void teleopPeriodic() {
        robot.swerve.setDefaultCommand(new SwerveCommandJoystick(
            robot.swerve,
            () -> -main.getLeftY(),
            () -> main.getLeftX(),
            () -> main.getRightY(),
            () -> !main.getAButtonPressed()));
    }
}
