package ca.mcrobotics.ui;

import java.io.Console;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Robot;
import ca.mcrobotics.Constants.Amp;
import ca.mcrobotics.Constants.Flywheel;
import ca.mcrobotics.Constants.OIConstants;
import ca.mcrobotics.commands.CommandSwerve;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Controls {
    Robot robot;

    XboxController main = new XboxController(Constants.CONTROL.MASTER_1); //Non LED
    XboxController alt = new XboxController(Constants.CONTROL.MASTER_2); //LED (Has left x axis drift)

    public Controls(Robot robot) {
        this.robot = robot;
    }

    public void teleopPeriodic() {
        robot.s_swerve.setDefaultCommand(new CommandSwerve(
            robot,
            () -> main.getLeftY(),
            () -> main.getLeftX(),
            () -> main.getRightX(),
            () -> !main.getAButtonPressed()));
                    
        if (alt.getYButton()) {
            robot.s_amp.startAmp(Amp.MAX_SPEED);
        } else if (alt.getAButton()) {
            robot.s_amp.startAmp(-Amp.MAX_SPEED);
        } else {
            robot.s_amp.stopAmp();
        }

        if (alt.getBButton()) {
            robot.s_flywheel.moveFlywheel(Flywheel.PEAK_SPEED_FLYWHEEL);
            robot.s_flywheel.moveTransfer(Flywheel.PEAK_SPEED_TRANSFER);
        } else if (alt.getXButton()) {
            robot.s_flywheel.moveFlywheel(-Flywheel.PEAK_SPEED_FLYWHEEL);
            robot.s_flywheel.moveTransfer(-Flywheel.PEAK_SPEED_TRANSFER);            
        } else {
            robot.s_flywheel.stopAll();
        }
    }
}