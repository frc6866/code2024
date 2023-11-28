package ca.mcrobotics.subsystems.driveTrain;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import ca.mcrobotics.Constants.*;

public class Swerve extends SubsystemBase {
    public final ADIS16470_IMU imu = new ADIS16470_IMU();
    private final SwerveModule frontLeft = new SwerveModule(
        Drive.FRONT_LEFT_SPEED,
        Drive.FRONT_LEFT_ROTATION,
        Drive.FRONT_LEFT_SPEED_REVERSED,
        Drive.FRONT_LEFT_ROTATION_ENODER_A,
        Drive.FRONT_LEFT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD);

    private final SwerveModule frontRight = new SwerveModule(
        Drive.FRONT_RIGHT_SPEED,
        Drive.FRONT_RIGHT_ROTATION,
        Drive.FRONT_RIGHT_SPEED_REVERSED,
        Drive.FRONT_RIGHT_ROTATION_ENODER_A,
        Drive.FRONT_RIGHT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD);

    private final SwerveModule backLeft = new SwerveModule(
        Drive.BACK_LEFT_SPEED,
        Drive.BACK_LEFT_ROTATION,
        Drive.BACK_LEFT_SPEED_REVERSED,
        Drive.BACK_LEFT_ROTATION_ENODER_A,
        Drive.BACK_LEFT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD);

    private final SwerveModule backRight = new SwerveModule(
        Drive.BACK_RIGHT_SPEED,
        Drive.BACK_RIGHT_ROTATION,
        Drive.BACK_RIGHT_SPEED_REVERSED,
        Drive.BACK_RIGHT_ROTATION_ENODER_A,
        Drive.BACK_RIGHT_SPEED_ABSOLUTE_ENCODER_OFFSET_RAD);
    
    //private final SwerveDriveOdometry odometer = new SwerveDriveOdometry(Drive.kDriveKinematics, new Rotation2d(0));

    public Swerve() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                zeroHeading();
            } catch (Exception e) {
            }
        }).start();
    }

    public void zeroHeading() {
        imu.reset();
    }

    public double getHeading() {
        return Math.IEEEremainder(imu.getAngle(), 360);
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(getHeading());
    }
    /*
    public Pose2d getPose() {
        return odometer.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        odometer.resetPosition(pose, getRotation2d());
    }
    */

    @Override
    public void periodic() {
        /*odometer.update(getRotation2d(), frontLeft.getState(), frontRight.getState(), backLeft.getState(),
                backRight.getState());*/
        SmartDashboard.putNumber("Robot Heading", getHeading());
        //SmartDashboard.putString("Robot Location", getPose().getTranslation().toString());
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