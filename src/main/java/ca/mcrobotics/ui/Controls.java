package ca.mcrobotics.ui;

import java.io.Console;

import ca.mcrobotics.Robot;
import ca.mcrobotics.Constants.Amp;
import ca.mcrobotics.Constants.OIConstants;
import ca.mcrobotics.commands.SwerveCommandJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Controls {
    Robot robot;

    XboxController main = new XboxController(0);
    XboxController alt = new XboxController(1);

    public Controls(Robot robot) {
        this.robot = robot;
    }

    public void teleopPeriodic() {
        robot.s_swerve.setDefaultCommand(new SwerveCommandJoystick(
            robot,
            () -> -main.getLeftY(),
            () -> main.getLeftX(),
            () -> main.getRightX(),
            () -> !main.getAButtonPressed()));
                    
        if (alt.getYButton()) {
            robot.s_intake.startAmp(Amp.MAX_SPEED);
        } else if (alt.getAButton()) {
            robot.s_intake.startAmp(-Amp.MAX_SPEED);
        } else {
            robot.s_intake.stopAmp();
        }
    }
}