package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class Motor {
    TalonSRX motorName;
    ShuffleboardTab tab;
    GenericEntry mtrOut;
    double lastDataSendTime;

    public Motor() {
        motorName = new TalonSRX(Constants.Template.TEMPLATE_CAN);
        motorName.setNeutralMode(null);

        lastDataSendTime = Util.staggerUpdates();
        tab = Shuffleboard.getTab("Drivetrain");
        mtrOut = Util.makeEntry(tab, "Motor out", 0, 0, 1, 1);
    }

    public void configure() {
        motorName.configNominalOutputForward(0, 30);
        motorName.configNominalOutputReverse(0, 30);
        motorName.configPeakOutputForward(Constants.Template.peakSpeed, 30);
        motorName.configPeakOutputReverse(-Constants.Template.peakSpeed, 30);
        motorName.setSensorPhase(true);
    }

    public void stop() {
        motorName.set(ControlMode.Disabled, 0);
    }

    public void move(double value) {
        motorName.set(ControlMode.PercentOutput, value);
    }

    public void setReversed(boolean rev) {
        motorName.setInverted(rev);
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

            Util.putEntry(mtrOut, motorName.getMotorOutputPercent());
        }
    }
}
