package ca.mcrobotics.subsystems.driveTrain.swerveLib;

public interface DriveController {
    void setReferenceVoltage(double voltage);

    double getStateVelocity();
}
