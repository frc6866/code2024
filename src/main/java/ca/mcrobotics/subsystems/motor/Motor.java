package ca.mcrobotics.subsystems.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Motor {
    TalonSRX motor;
    ShuffleboardTab tab;
    GenericEntry mtrOut;
    double lastDataSendTime;

    final double peakSpeedPositive;
    final double peakSpeedNegative;
    final int timeoutMs;

    /**
     * The Motor class is a wrapper around the TalonSRX class. It provides a simple
     * interface to control a motor.
     * 
     * @param builder The builder object to configure a Motor.
     * 
     *                <h1>Example</h1>
     * 
     *                <pre>
     *                Motor motor = new MotorBuilder()
     *                        .setDeviceNumber(1)
     *                        .setNeutralMode(null)
     *                        .setShuffleboardTab("Tab")
     *                        .otherConfig()
     *                        .build();
     *                </pre>
     */
    public Motor(final MotorBuilder builder) {
        motor = builder.motor;
        motor.setNeutralMode(builder.neutralMode);

        lastDataSendTime = Util.staggerUpdates();
        tab = builder.shuffleboardTab;
        mtrOut = builder.motorOut;

        peakSpeedPositive = builder.peakSpeedPositive;
        peakSpeedNegative = builder.peakSpeedNegative;
        timeoutMs = builder.timeoutMs;
    }

    public void configure() {
        motor.configNominalOutputForward(0, 30);
        motor.configNominalOutputReverse(0, 30);
        motor.configPeakOutputForward(peakSpeedPositive, 30);
        motor.configPeakOutputReverse(peakSpeedNegative, 30);
        motor.setSensorPhase(true);
    }

    public void stop() {
        motor.set(ControlMode.Disabled, 0);
    }

    public void move(double value) {
        motor.set(ControlMode.PercentOutput, value);
    }

    public void setReversed(boolean rev) {
        motor.setInverted(rev);
    }

    /**
     * The periodic method is designed to be called by subsystems
     * 
     * <h1>Example</h1>
     * <p>
     * 
     * <pre>
     * <code>
     * class Subsystem extends SubsystemBase {
     *    private final Motor motor = new Motor();
     *    
     *    @Override
     *    public void periodic() {
     *        motor.periodic();
     *    }
     * }
     * </code>
     * </pre>
     * </p>
     */
    public void periodic() {
        if (Util.shouldUpdateShuffleboard(lastDataSendTime)) {
            lastDataSendTime = Util.getSeconds();

            Util.putEntry(mtrOut, motor.getMotorOutputPercent());
        }
    }
}
