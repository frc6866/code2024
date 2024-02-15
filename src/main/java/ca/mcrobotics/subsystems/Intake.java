package ca.mcrobotics.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.mcrobotics.*;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    TalonSRX Motor;

    //ShuffleboardTab tab;
    //GenericEntry mtrOut;
    //double lastDataSendTime;
    
    public Intake() {
        Motor = new TalonSRX(Constants.Intake.INTAKE1_CAN);
 
        Motor.setNeutralMode(null);


        //lastDataSendTime = Util.staggerUpdates();
        //tab = Shuffleboard.getTab("Drivetrain");
        //mtrOut = Util.makeEntry(tab, "Motor out", 0, 0, 1, 1);
    }

    public void configure() {
      Motor.configNominalOutputForward(0, 30);
        Motor.configNominalOutputReverse(0, 30);
        Motor.configPeakOutputForward(Constants.Intake.MAXIMUMOVERDRIVE, 30);
        Motor.configPeakOutputReverse(-Constants.Intake.MAXIMUMOVERDRIVE, 30);
      //dont think we even need this just suck and not suck.
    }

    public void StopSuck() {
        Motor.set(ControlMode.Disabled, 0);
    }

    public void Suck() {
        Motor.set(ControlMode.PercentOutput, Constants.Intake.speeeed);
    }



//    @Override
  //  public void periodic() {
    //    if(Util.shouldUpdateShuffleboard(lastDataSendTime)) {
      //      lastDataSendTime = Util.getSeconds();
            
        //    Util.putEntry(mtrOut, motorName.getMotorOutputPercent());
        //}
    //}
}