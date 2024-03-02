package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Template extends SubsystemBase{
    TalonSRX motorName;
    ShuffleboardTab tab;
    GenericEntry mtrOut;
    double lastDataSendTime;
    
    public Template() {
        motorName = new TalonSRX(Constants.Template.TEMPLATE_CAN);
        motorName.setNeutralMode(null);
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

    @Override
    public void periodic() {
        if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
            lastDataSendTime = Util.getSeconds();
            
            Util.putEntry(mtrOut, motorName.getMotorOutputPercent());
        }
    }
}
