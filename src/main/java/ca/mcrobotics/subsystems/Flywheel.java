package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase{
    TalonSRX flywheelMotor;
    ShuffleboardTab tab;
    GenericEntry mtrOut;
    double lastDataSendTime;
    
    public Flywheel() {
        flywheelMotor = new TalonSRX(Constants.Flywheel.FLYWHEEL1_CAN);
        flywheelMotor.setNeutralMode(null);

        lastDataSendTime = Util.staggerUpdates();
        tab = Shuffleboard.getTab("Flywheel");
        mtrOut = Util.makeEntry(tab, "Flywheel", 0, 0, 1, 1);
    }

    public void configure() {
        flywheelMotor.configNominalOutputForward(0, 30);
        flywheelMotor.configNominalOutputReverse(0, 30);
        flywheelMotor.configPeakOutputForward(Constants.Template.peakSpeed, 30);
        flywheelMotor.configPeakOutputReverse(-Constants.Template.peakSpeed, 30);
        flywheelMotor.setSensorPhase(true);
    }

    public void stop() {
        flywheelMotor.set(ControlMode.Disabled, 0);
        // flywheelMotor.set(ControlMode.PercentOutput, 0); // maybe idk ?
    }

    public void move(double value) {
        flywheelMotor.set(ControlMode.PercentOutput, value);
    }

    public void setReversed(boolean rev) {
        flywheelMotor.setInverted(rev);
    }

    @Override
    public void periodic() {
        if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
            lastDataSendTime = Util.getSeconds();
            
            Util.putEntry(mtrOut, flywheelMotor.getMotorOutputPercent());
        }
    }
}
