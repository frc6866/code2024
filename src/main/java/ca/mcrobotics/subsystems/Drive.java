package ca.mcrobotics.subsystems;

import javax.print.CancelablePrintJob;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Constants.*;
import ca.mcrobotics.ui.Controls;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drive extends SubsystemBase {
    private CANSparkMax flDrive;
    private CANSparkMax frDrive;
    private CANSparkMax blDrive;
    private CANSparkMax brDrive;

    private CANSparkMax flTurn;
    private CANSparkMax frTurn;
    private CANSparkMax blTurn;
    private CANSparkMax brTurn;

    private CANEncoder flEncoder;
    private CANEncoder frEncoder;
    private CANEncoder blEncoder;
    private CANEncoder brEncoder;

    private double wheelAngA;
    private double wheelAngB;
    private boolean swerveOn;

    public Drive() {
        flDrive = new CANSparkMax(Constants.Drive.kFrontLeftDriveMotorPort, MotorType.kBrushless);
        frDrive = new CANSparkMax(Constants.Drive.kFrontRightDriveMotorPort, MotorType.kBrushless);
        blDrive = new CANSparkMax(Constants.Drive.kBackLeftDriveMotorPort, MotorType.kBrushless);
        brDrive = new CANSparkMax(Constants.Drive.kBackRightDriveMotorPort, MotorType.kBrushless);

        flTurn = new CANSparkMax(Constants.Drive.kFrontLeftTurningMotorPort, MotorType.kBrushless);
        frTurn = new CANSparkMax(Constants.Drive.kFrontRightTurningMotorPort, MotorType.kBrushless);
        blTurn = new CANSparkMax(Constants.Drive.kBackLeftTurningMotorPort, MotorType.kBrushless);
        brTurn = new CANSparkMax(Constants.Drive.kBackRightTurningMotorPort, MotorType.kBrushless);

        flEncoder = flTurn.getEncoder();
        frEncoder = frTurn.getEncoder();
        blEncoder = blTurn.getEncoder();
        brEncoder = brTurn.getEncoder();
        
        flDrive.setInverted(true);
        blDrive.setInverted(true);

        frTurn.setInverted(true);
        blTurn.setInverted(true);

        wheelAngA = 0;
        wheelAngB = 0;
    }

    /**
     * 
     * Drives all 4 motors
     * 
     * @param left - Moves the left drive motors
     * @param right - Move the right drive motors
     * @param anga - Angle of fl and br turn motors
     * @param angb - Angle of fr and bl turn motors
     */
    public void drive(double left, double right, double anga, double angb) {
        // 11/150 does not work as the angle
        // 15/150 is untested, probably too much - aditya
        // maybe try 14/150 for future testing/rnd
        wheelAngA = anga * -14 / 150;
        wheelAngB = angb * 14 / 150;
        if (Controls.swerveIsOn) { //Turn wheel
            swerveOn = true;
            //Only moves once the wheel has reached θ degrees
            if (((-flEncoder.getPosition()-wheelAngA) > -0.01 && (-flEncoder.getPosition()-wheelAngA) < 0.01) ||
                ((-frEncoder.getPosition()-wheelAngB) > -0.01 && (-frEncoder.getPosition()-wheelAngB) < 0.01) ||
                ((-blEncoder.getPosition()-wheelAngB) > -0.01 && (-blEncoder.getPosition()-wheelAngB) < 0.01) ||
                ((-brEncoder.getPosition()-wheelAngA) > -0.01 && (-brEncoder.getPosition()-wheelAngA) < 0.01) && swerveOn) {
                flDrive.set(left);
                frDrive.set(right);
                blDrive.set(left);
                brDrive.set(right);
            } 
        } else { //Normal wheel
            Controls.swerveIsOn = false;
            swerveOn = false;
            flDrive.set(left);
            frDrive.set(right);
            blDrive.set(right);
            brDrive.set(right);

        }
    }
    /**
     * Aligns wheels to θ degrees
     * 
     * Gear ratio of turn motor is 7/150. But in code we changed to 13/150 to account for undershooting.
     * Checks to see if the motor is not within 0.005 rotations and if so moves the motor back into position
     * - The greater the diffrence, the more power there is (As position becomes greater the motor moves more)
     * Once the position is between 0.001 and -0.001 the motor stops as the desired position is reached.
     * 
     * If there is an angle offset we subtract the desired angle from the position and take this into account when aliging wheels.
     */
    public void setWheelAng() { //automatically puts wheels back into straight position (0 degrees)
        if (Math.abs((-flEncoder.getPosition()-wheelAngA)) > 0.005) {
            flTurn.set((-flEncoder.getPosition()-wheelAngA)*0.3);
        } else if ((-flEncoder.getPosition()-wheelAngA) > -0.001 && (-flEncoder.getPosition()-wheelAngA) < 0.001) {
            flTurn.set(0);
        }

        if (Math.abs((-frEncoder.getPosition()-wheelAngB)) > 0.005) {
            frTurn.set((-frEncoder.getPosition()-wheelAngB)*0.3);
        } else if ((-frEncoder.getPosition()-wheelAngB) > -0.001 && (-frEncoder.getPosition()-wheelAngB) < 0.001) {
            frTurn.set(0);
        }

        if (Math.abs((-blEncoder.getPosition()-wheelAngB)) > 0.005) {
            blTurn.set((-blEncoder.getPosition()-wheelAngB)*0.3);
        } else if ((-blEncoder.getPosition()-wheelAngB) > -0.001 && (-blEncoder.getPosition()-wheelAngB) < 0.001) {
            blTurn.set(0);
        }

        if (Math.abs((-brEncoder.getPosition()-wheelAngA)) > 0.005) {
            brTurn.set((-brEncoder.getPosition()-wheelAngA)*0.3);
        } else if ((-brEncoder.getPosition()-wheelAngA) > -0.001 && (-brEncoder.getPosition()-wheelAngA) < 0.001) {
            brTurn.set(0);
        }
    }


// defenceTurn needs to be done before humber competion
// angle to do: 20 degrees 
// wheelAngA - wheelAngB 


    public void stop() {
        flDrive.set(0);
        frDrive.set(0);
        blDrive.set(0);
        brDrive.set(0);
    }

    @Override
    public void periodic() {
        setWheelAng();
    }

    public void setPos(double fl, double fr, double bl, double br) {
        flEncoder.setPosition(fl);
        frEncoder.setPosition(fr);
        blEncoder.setPosition(bl);
        brEncoder.setPosition(br);
    }

    public double[] getPos() {
        return new double[]{flEncoder.getPosition(), frEncoder.getPosition(), blEncoder.getPosition(), brEncoder.getPosition()};
    }
}
  