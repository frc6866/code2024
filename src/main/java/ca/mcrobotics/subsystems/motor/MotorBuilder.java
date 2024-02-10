package ca.mcrobotics.subsystems.motor;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import ca.mcrobotics.Util;

public class MotorBuilder {
    TalonSRX motor = null;
    NeutralMode neutralMode = null;
    ShuffleboardTab shuffleboardTab = null;
    GenericEntry motorOut = null;
    double peakSpeedPositive = 0.2;
    double peakSpeedNegative = -0.2;
    int timeoutMs = 30;

    public Motor build() throws MotorNotConfiguredException {
        if (motor == null || shuffleboardTab == null || motorOut == null) {
            throw new MotorNotConfiguredException("Some required fields for configuring a Motor are missing.");
        }

        return new Motor(this);
    }

    /**
     * Set the device number for the motor. This has to be the first non-constructor
     * method called.
     * 
     * @param deviceNumber The device number of the motor.
     * @return The MotorBuilder object for method chaining.
     */
    public MotorBuilder setDeviceNumber(int deviceNumber) {
        this.motor = new TalonSRX(deviceNumber);
        return this;
    }

    public MotorBuilder setNeutralMode(NeutralMode neutralMode) {
        this.neutralMode = neutralMode;
        return this;
    }

    public MotorBuilder setShuffleboardTab(final String tabTitle) {
        this.shuffleboardTab = Shuffleboard.getTab(tabTitle);
        return this;
    }

    public MotorBuilder setGenericEntry(final String entryTitle, final int entryX, final int entryY, final int entryW,
            final int entryH) {
        this.motorOut = Util.makeEntry(shuffleboardTab, entryTitle, entryX, entryY, entryW, entryH);
        return this;
    }

    public MotorBuilder setPeakSpeedPositive(double peakSpeedPositive) {
        this.peakSpeedPositive = peakSpeedPositive;
        return this;
    }

    public MotorBuilder setPeakSpeedNegative(double peakSpeedNegative) {
        this.peakSpeedNegative = peakSpeedNegative;
        return this;
    }

    public MotorBuilder setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    /**
     * This method is used to configure the motor with any other settings that are
     * not predefined.
     * 
     * @param callback The callback function to configure the motor. The callback
     *                 function should accept a TalonSRX
     *                 object (a motor).
     * @return The MotorBuilder object for method chaining.
     * @throws MotorNotConfiguredException if the device number is not set before
     *                                     calling this method, i.e., TalonSRX
     *                                     hasn't been created yet so there's no
     *                                     motor to configure.
     */
    public MotorBuilder setMiscConfig(Consumer<TalonSRX> callback) throws MotorNotConfiguredException {
        if (motor == null) {
            throw new MotorNotConfiguredException("Please set device number first before calling this method.");
        }

        callback.accept(motor);

        return this;
    }
}
