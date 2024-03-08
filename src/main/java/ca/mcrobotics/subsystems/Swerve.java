package ca.mcrobotics.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import ca.mcrobotics.Constants;

public class Swerve extends SubsystemBase {
    private final ADIS16470_IMU gyro;

    private SwerveModule[] mSwerveMods;

    private Field2d field;

    public Swerve() {
        gyro = new ADIS16470_IMU();
        gyro.calibrate();
        zeroGyro();

        mSwerveMods = new SwerveModule[] {
                new SwerveModule(0, Constants.Drive.Mod0.CONSTANTS),
                new SwerveModule(1, Constants.Drive.Mod1.CONSTANTS),
                new SwerveModule(2, Constants.Drive.Mod2.CONSTANTS),
                new SwerveModule(3, Constants.Drive.Mod3.CONSTANTS)
        };

        field = new Field2d();
        SmartDashboard.putData("Field", field);
    }

    public void drive(
            Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates = Constants.Drive.kDriveKinematics.toSwerveModuleStates(
                fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                translation.getX(), translation.getY(), rotation, getYaw())
                        : new ChassisSpeeds(translation.getX(), translation.getY(), rotation));

        for (SwerveModule mod : mSwerveMods) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }

    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Drive.maxSpeed);

        for (SwerveModule mod : mSwerveMods) {
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }

    public SwerveModuleState[] getStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        for (SwerveModule mod : mSwerveMods) {
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public void zeroGyro() {
        // gyro.setYaw(0);
    }

    public Rotation2d getYaw() {
        return (Constants.Drive.invertGyro)
                ? Rotation2d.fromDegrees(360 - gyro.getAngle())
                : Rotation2d.fromDegrees(gyro.getAngle());
        // return Rotation2d.fromDegrees(90);
    }

    public SwerveModulePosition[] getModulePos() {
        return new SwerveModulePosition[] { mSwerveMods[0].getPos(),
                mSwerveMods[1].getPos(),
                mSwerveMods[2].getPos(),
                mSwerveMods[3].getPos() };
    }

    @Override
    public void periodic() {
        gyro.setGyroAngleX(gyro.getAngle() - Constants.Common.GYRO_OFFSET);
        for (SwerveModule mod : mSwerveMods) {
            SmartDashboard.putNumber(
                    "Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
            SmartDashboard.putNumber(
                    "Mod " + mod.moduleNumber + " Integrated", mod.getState().angle.getDegrees());
            SmartDashboard.putNumber(
                    "Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
        }
        SmartDashboard.putNumber("Gryo Values", gyro.getAngle());
    }
}