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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Controls {
    Robot robot;

    XboxController main = new XboxController(Constants.CONTROL.MASTER_1); //Non LED
    XboxController alt = new XboxController(Constants.CONTROL.MASTER_2); //LED (Has left x axis drift)

    double altRightY;
    double wheelAng;

    double mainLeftX = 0;
    double mainLeftY = 0;
    double mainRightX = 0;

    double leftDrive;
    double rightDrive;
    double angA;
    double angB;

    public static boolean swerveIsOn;

    public Controls(Robot robot) {
        swerveIsOn = false;
        this.robot = robot;
    }

    public void teleopPeriodic() {
        mainLeftX = main.getLeftX();
        mainLeftY = main.getLeftY();
        mainRightX = main.getRightX();

        if (main.getYButton()) {
            robot.speed = (robot.speed == Speed.FAST) ? Speed.SLOW : Speed.FAST;
        }

        if (main.getBButton()) {
            //x configuration
            robot.s_swerve.drive(0, 0, -22.5, 22.5);
        } else {
            //Driving
            if (Math.abs(mainLeftX) >= 0.95 && Math.abs(mainLeftY) <= 0.5) { //Swerve
                angA = mainLeftX * 90;
                angB = mainLeftX * 90;
                leftDrive = mainLeftY - 1;
                rightDrive = mainLeftY - 1;
            } else {
                swerveIsOn = false;
                if (Math.abs(mainRightX) >= 0.1) { //Turn
                    angA = 45;
                    angB = -45;
                    leftDrive = -mainRightX;
                    rightDrive = mainRightX;
                } else { //Normal Drive
                    angA = mainLeftX*90;
                    angB = mainLeftX*90;
                    leftDrive = mainLeftY-mainRightX;
                    rightDrive = mainLeftY+mainRightX;
                }
            }

            SmartDashboard.setDefaultNumber("1", robot.s_swerve.getPos()[0]);
            SmartDashboard.setDefaultNumber("2", robot.s_swerve.getPos()[1]);
            SmartDashboard.setDefaultNumber("3", robot.s_swerve.getPos()[2]);
            SmartDashboard.setDefaultNumber("4", robot.s_swerve.getPos()[3]);


            // Adjust for robot speed
            leftDrive *= robot.speed;
            rightDrive *= robot.speed;
            

            robot.s_swerve.drive(leftDrive,
                                rightDrive,
                                angA,
                                angB);
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
            robot.s_flywheel.moveFlywheel(-Flywheel.PEAK_SPEED_FLYWHEEL * 0.3);
            robot.s_flywheel.moveTransfer(-Flywheel.PEAK_SPEED_TRANSFER * 0.3);            
        } else if (Math.abs(alt.getRightY()) > 0.05) { //Shoot
            altRightY = Math.abs(alt.getRightY());
            robot.s_flywheel.moveFlywheel(altRightY);
            if (alt.getRightTriggerAxis() > 0){
                robot.s_flywheel.moveTransfer(Flywheel.PEAK_SPEED_TRANSFER);
            } else {
                robot.s_flywheel.stopTransfer();
            }
        } else {
            robot.s_flywheel.stopAll();
        }
    }
}