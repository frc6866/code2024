package ca.mcrobotics.subsystems.driveTrain.swerveLib;

public interface SteerController {
    double getReferenceAngle();

    void setReferenceAngle(double referenceAngleRadians);

    double getStateAngle();
}
