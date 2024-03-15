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
    }

    public void drive(double left, double right) {
        flDrive.set(left);
        frDrive.set(right);
        blDrive.set(left);
        brDrive.set(right);
    }

    public void stop() {
        flDrive.set(0);
        frDrive.set(0);
        blDrive.set(0);
        brDrive.set(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("1", flEncoder.getPosition());
        SmartDashboard.putNumber("2", frEncoder.getPosition());
        SmartDashboard.putNumber("3", blEncoder.getPosition());
        SmartDashboard.putNumber("4", brEncoder.getPosition());
        if (Math.abs(flEncoder.getPosition()) > 0.005) {
            flTurn.set(-flEncoder.getPosition()*0.3);
        } else if (flEncoder.getPosition() > -0.001 && flEncoder.getPosition() < 0.001) {
            flTurn.set(0);
        }
        
        if (Math.abs(frEncoder.getPosition()) > 0.005) {
            frTurn.set(-frEncoder.getPosition()*0.3);
        } else if (frEncoder.getPosition() > -0.001 && frEncoder.getPosition() < 0.001) {
            frTurn.set(0);
        }

        if (Math.abs(blEncoder.getPosition()) > 0.005) {
            blTurn.set(-blEncoder.getPosition()*0.3);
        } else if (blEncoder.getPosition() > -0.001 && blEncoder.getPosition() < 0.001) {
            blTurn.set(0);
        }

        if (Math.abs(brEncoder.getPosition()) > 0.001) {
            brTurn.set(-brEncoder.getPosition()*0.3);
        } else if (brEncoder.getPosition() > -0.001 && brEncoder.getPosition() < 0.001) {
            brTurn.set(0);
        }
    }
}
