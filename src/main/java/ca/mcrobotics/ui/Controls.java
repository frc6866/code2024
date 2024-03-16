package ca.mcrobotics.ui;

import java.io.Console;

import ca.mcrobotics.Robot;
import ca.mcrobotics.Constants;
import ca.mcrobotics.Constants.Amp;
import ca.mcrobotics.Constants.Flywheel;
import ca.mcrobotics.Constants.OIConstants;
import ca.mcrobotics.Constants.Speed;
import ca.mcrobotics.commands.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Controls {
    Robot robot;

    XboxController main = new XboxController(Constants.CONTROL.MASTER_1); //Non LED
    XboxController alt = new XboxController(Constants.CONTROL.MASTER_2); //LED (Has left x axis drift)

    double altRightY;
    double wheelAng;

    double mainLeftX;
    double mainLeftY;
    double mainRightX;

    double leftDrive;
    double rightDrive;
    double ang;

    public Controls(Robot robot) {
        this.robot = robot;
    }

    public void teleopPeriodic() {
        // Multiplier for robot speed
        mainLeftX = main.getLeftX() * robot.speed;
        mainLeftY = main.getLeftY() * robot.speed;
        mainRightX = main.getRightX() * robot.speed;

        //Turning
        if(mainLeftX > 0.15) { //Right
            if (mainLeftY > 0) { //Up right
                ang = mainLeftX*90;
            } else { //Down right
                ang = mainLeftX*90+90;
            }
        } else if (mainLeftX < 0.15) { //Left
            if (mainLeftY > 0) { //Up left
                ang = -mainLeftX*90;
            } else { //Down left
                ang = -mainLeftX*90-90;
            }
        }

        //Driving
        if (Math.abs(mainLeftX) >= 0.95 &&  Math.abs(mainLeftY) <= 0.5) { //Fix for swerve
            leftDrive = 1-mainLeftX;
            rightDrive = 1-mainLeftX;
        } else {
            leftDrive = mainLeftX-mainRightX;
            rightDrive = mainLeftX+mainRightX;
        }

        robot.s_swerve.drive(leftDrive,
                             rightDrive,
                             ang);

        
        if (main.getYButton()) {
            robot.speed = (robot.speed == Speed.FAST) ? Speed.SLOW : Speed.FAST;
        }

        if (alt.getYButton()) {
            robot.s_amp.startAmp(Amp.MAX_SPEED_OUT);
        } else if (alt.getAButton()) {
            robot.s_amp.startAmp(-Amp.MAX_SPEED_IN);
        } else { 
            robot.s_amp.stopAmp();
        }

        //Flywheel
        if (alt.getXButton()) { //Intake
            robot.s_flywheel.moveFlywheel(-Flywheel.PEAK_SPEED_FLYWHEEL*0.3);
            robot.s_flywheel.moveTransfer(-Flywheel.PEAK_SPEED_TRANSFER*0.3);            
        } else if (Math.abs(alt.getRightY()) > 0.05) { //Shoot
            altRightY = Math.abs(alt.getRightY());
            robot.s_flywheel.moveFlywheel(altRightY);
            if (alt.getLeftTriggerAxis() > 0){
                robot.s_flywheel.moveTransfer(Flywheel.PEAK_SPEED_TRANSFER);
            } else {
                robot.s_flywheel.stopTransfer();
            }
        } else {
            robot.s_flywheel.stopAll();
        }
    }
}