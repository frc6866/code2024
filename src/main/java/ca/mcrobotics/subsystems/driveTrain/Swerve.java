package ca.mcrobotics.subsystems.driveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.Constants;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {

    private final TalonSRX driveMotor;
    private final TalonSRX turningMotor;

    private final Encoder driveEncoder;
    private final Encoder turningEncoder;

    private final PIDController turningPidController;

    private final boolean absoluteEncoderReversed;
    private final double absoluteEncoderOffsetRad;

    public Swerve(int driveMotorId, int turningMotorId, boolean driveMotorReversed, boolean turningMotorReversed,
        int absoluteEncoderIdA, int absoluteEncoderIdB, double absoluteEncoderOffset, boolean absoluteEncoderReversed) {

        this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
        this.absoluteEncoderReversed = absoluteEncoderReversed;
        this.driveEncoder = new Encoder(0, 0);
        this.turningEncoder = new Encoder(absoluteEncoderIdA, absoluteEncoderIdB);

        driveMotor = new TalonSRX(driveMotorId);
        turningMotor = new TalonSRX(turningMotorId);

        driveMotor.setInverted(driveMotorReversed);
        turningMotor.setInverted(turningMotorReversed);

        turningPidController = new PIDController(Constants.DRIVE.kPTurning, 0, 0);
        turningPidController.enableContinuousInput(-Math.PI, Math.PI);

        resetEncoders();
    }

    public double getTurningPosition() {
        return turningEncoder.getRaw();
    }

    public double getDriveVelocity() {
        return driveEncoder.getRaw();
    }


    public double getAbsoluteEncoderRad() {
        double angle = turningEncoder.getRaw() / RobotController.getVoltage5V();
        angle *= 2.0 * Math.PI;
        angle -= absoluteEncoderOffsetRad;
        return angle * (absoluteEncoderReversed ? -1.0 : 1.0);
    }

    public void resetEncoders() {
        driveEncoder.reset();
        turningEncoder.reset();
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningPosition()));
    }

    public void setDesiredState(SwerveModuleState state) {
        if (Math.abs(state.speedMetersPerSecond) < 0.001) {
            stop();
            return;
        }
        state = SwerveModuleState.optimize(state, getState().angle);
        driveMotor.set(ControlMode.PercentOutput, state.speedMetersPerSecond / Constants.DRIVE.kPhysicalMaxSpeedMetersPerSecond);
        turningMotor.set(ControlMode.PercentOutput, turningPidController.calculate(getTurningPosition(), state.angle.getRadians()));
        SmartDashboard.putString("Swerve[" + turningEncoder.getRaw() + "] state", state.toString());
    }

    public void stop() {
        driveMotor.set(ControlMode.PercentOutput, 0);
        turningMotor.set(ControlMode.PercentOutput, 0);
    }
}