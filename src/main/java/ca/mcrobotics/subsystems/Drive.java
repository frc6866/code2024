package ca.mcrobotics.subsystems;

import javax.print.CancelablePrintJob;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Constants.*;
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

    private double wheelAng;

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

        wheelAng = 0;
    }

    public void drive(double left, double right, double ang) {
        wheelAng = ang*9/150;
        flDrive.set(left);
        frDrive.set(right);
        blDrive.set(left);
        brDrive.set(right);
    }

    public void setWheelAng() { //automatically puts wheels back into straight position (0 degrees)
        if (Math.abs((-flEncoder.getPosition()+wheelAng)) > 0.005) {
            flTurn.set((-flEncoder.getPosition()+wheelAng)*0.3);
        } else if ((-flEncoder.getPosition()+wheelAng) > -0.001 && (-flEncoder.getPosition()+wheelAng) < 0.001) {
            flTurn.set(0);
        }
        
        if (Math.abs((-frEncoder.getPosition()-wheelAng)) > 0.005) {
            frTurn.set((-frEncoder.getPosition()-wheelAng)*0.3);
        } else if ((-frEncoder.getPosition()-wheelAng) > -0.001 && (-frEncoder.getPosition()-wheelAng) < 0.001) {
            frTurn.set(0);
        }

        if (Math.abs((-blEncoder.getPosition()-wheelAng)) > 0.005) {
            blTurn.set((-blEncoder.getPosition()-wheelAng)*0.3);
        } else if ((-blEncoder.getPosition()-wheelAng) > -0.001 && (-blEncoder.getPosition()-wheelAng) < 0.001) {
            blTurn.set(0);
        }

        if (Math.abs((-brEncoder.getPosition()+wheelAng)) > 0.001) {
            brTurn.set((-brEncoder.getPosition()+wheelAng)*0.3);
        } else if ((-brEncoder.getPosition()+wheelAng) > -0.001 && (-brEncoder.getPosition()+wheelAng) < 0.001) {
            brTurn.set(0);
        }

    }

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
}
