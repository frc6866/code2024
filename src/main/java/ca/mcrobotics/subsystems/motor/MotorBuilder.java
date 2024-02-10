package ca.mcrobotics.subsystems.motor;

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
}
