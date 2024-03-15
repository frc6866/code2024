package ca.mcrobotics.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import ca.mcrobotics.Constants;
import ca.mcrobotics.Constants.*;
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

    private CANCoder flEncoder;
    private CANCoder frEncoder;
    private CANCoder blEncoder;
    private CANCoder brEncoder;

    private double wheelAng;

    public Drive() {
        flDrive = new CANSparkMax(Constants.Drive.kFrontLeftDriveMotorPort, MotorType.kBrushless);
        frDrive = new CANSparkMax(Constants.Drive.kFrontRightDriveMotorPort, MotorType.kBrushless);
        blDrive = new CANSparkMax(Constants.Drive.kBackLeftDriveMotorPort, MotorType.kBrushless);
        brDrive = new CANSparkMax(Constants.Drive.kBackRightDriveMotorPort, MotorType.kBrushless);

        flTurn = new CANSparkMax(Constants.Drive.kFrontLeftDriveMotorPort, MotorType.kBrushless);
        frTurn = new CANSparkMax(Constants.Drive.kFrontRightDriveMotorPort, MotorType.kBrushless);
        blTurn = new CANSparkMax(Constants.Drive.kBackLeftDriveMotorPort, MotorType.kBrushless);
        brTurn = new CANSparkMax(Constants.Drive.kBackRightDriveMotorPort, MotorType.kBrushless);

        flEncoder = new CANCoder(Constants.Drive.kFrontLeftDriveMotorPort);
        frEncoder = new CANCoder(Constants.Drive.kFrontRightDriveMotorPort);
        blEncoder = new CANCoder(Constants.Drive.kBackLeftDriveMotorPort);
        brEncoder = new CANCoder(Constants.Drive.kBackRightDriveMotorPort);

        wheelAng = 0;
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
        if (Math.abs(flEncoder.getPosition()) > 0.1) {
            flTurn.set(-flEncoder.getPosition()+wheelAng);
        }
        if (Math.abs(flEncoder.getPosition()) > 0.1) {
            frTurn.set(-frEncoder.getPosition()+wheelAng);
        }
        if (Math.abs(flEncoder.getPosition()) > 0.1) {
            blTurn.set(-blEncoder.getPosition()+wheelAng);
        }
        if (Math.abs(flEncoder.getPosition()) > 0.1) {
            brTurn.set(-brEncoder.getPosition()+wheelAng);
        }
    }
}
