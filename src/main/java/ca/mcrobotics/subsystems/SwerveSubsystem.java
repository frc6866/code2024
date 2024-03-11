package ca.mcrobotics.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import ca.mcrobotics.Constants.*;

public class SwerveSubsystem extends SubsystemBase {
    private final SwerveModule frontLeft = new SwerveModule(
            Drive.kFrontLeftDriveMotorPort,
            Drive.kFrontLeftTurningMotorPort,
            Drive.kFrontLeftDriveEncoderReversed,
            Drive.kFrontLeftTurningEncoderReversed,
            Drive.kFrontLeftDriveAbsoluteEncoderPort,
            Drive.kFrontLeftDriveAbsoluteEncoderOffsetRad,
            Drive.kFrontLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule frontRight = new SwerveModule(
            Drive.kFrontRightDriveMotorPort,
            Drive.kFrontRightTurningMotorPort,
            Drive.kFrontRightDriveEncoderReversed,
            Drive.kFrontRightTurningEncoderReversed,
            Drive.kFrontRightDriveAbsoluteEncoderPort,
            Drive.kFrontRightDriveAbsoluteEncoderOffsetRad,
            Drive.kFrontRightDriveAbsoluteEncoderReversed);

    private final SwerveModule backLeft = new SwerveModule(
            Drive.kBackLeftDriveMotorPort,
            Drive.kBackLeftTurningMotorPort,
            Drive.kBackLeftDriveEncoderReversed,
            Drive.kBackLeftTurningEncoderReversed,
            Drive.kBackLeftDriveAbsoluteEncoderPort,
            Drive.kBackLeftDriveAbsoluteEncoderOffsetRad,
            Drive.kBackLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule backRight = new SwerveModule(
            Drive.kBackRightDriveMotorPort,
            Drive.kBackRightTurningMotorPort,
            Drive.kBackRightDriveEncoderReversed,
            Drive.kBackRightTurningEncoderReversed,
            Drive.kBackRightDriveAbsoluteEncoderPort,
            Drive.kBackRightDriveAbsoluteEncoderOffsetRad,
            Drive.kBackRightDriveAbsoluteEncoderReversed);

    private final ADIS16470_IMU gyro = new ADIS16470_IMU();
    private final SwerveDriveOdometry odometer = new SwerveDriveOdometry(Drive.kDriveKinematics,
            new Rotation2d(0), 
            new SwerveModulePosition[]{frontLeft.getModulePos(), 
                                        frontRight.getModulePos(), 
                                        backLeft.getModulePos(), 
                                        backRight.getModulePos()});

    public SwerveSubsystem() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();
    }

    public void zeroHeading() {
        gyro.reset();
    }

    public double getHeading() {
        return Math.IEEEremainder(gyro.getAngle(), 360);
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(getHeading());
    }

    public Pose2d getPose() {
        return odometer.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        odometer.resetPosition(Rotation2d.fromDegrees(gyro.getAngle()), 
                               new SwerveModulePosition[]{frontLeft.getModulePos(), 
                                                          frontRight.getModulePos(), 
                                                          backLeft.getModulePos(), 
                                                          backRight.getModulePos()}, 
                               pose);
    }

    @Override
    public void periodic() {
        odometer.update(Rotation2d.fromDegrees(gyro.getAngle()),
                                               new SwerveModulePosition[]{frontLeft.getModulePos(), 
                                                                          frontRight.getModulePos(), 
                                                                          backLeft.getModulePos(), 
                                                                          backRight.getModulePos()});
        SmartDashboard.putNumber("Robot Heading", getHeading());
        SmartDashboard.putString("Robot Location", getPose().getTranslation().toString());
        SmartDashboard.putNumber("Ang 1", frontLeft.getAbsoluteEncoderRad());
    }

    public void stopModules() {
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }

    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Drive.kPhysicalMaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }
}