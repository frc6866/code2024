package ca.mcrobotics.subsystems.motor;

/**
 * Class body is intentionally left empty. Should be thrown only when a motor is
 * not configured properly, e.g., some
 * required fields are not configured or some configuration failed.
 */
public class MotorNotConfiguredException extends Exception {
    MotorNotConfiguredException() {
        super();
    }

    MotorNotConfiguredException(String message) {
        super(message);
    }
}
