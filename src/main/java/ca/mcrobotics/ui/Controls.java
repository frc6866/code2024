package ca.mcrobotics.ui;

import java.io.Console;

import ca.mcrobotics.Robot;
import ca.mcrobotics.Constants.OIConstants;
import ca.mcrobotics.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Controls {
    Robot robot;

    XboxController main = new XboxController(0);

    public Controls(Robot robot) {
        this.robot = robot;
    }

    public void teleopPeriodic() {
        robot.s_swerve.setDefaultCommand(new CommandDrive(robot,
                                                          main.getLeftY()-main.getRightX(),
                                                          main.getLeftY()+main.getRightX()));
    }
}