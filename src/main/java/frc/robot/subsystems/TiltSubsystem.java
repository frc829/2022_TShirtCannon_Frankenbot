package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.DoubleSupplier;

public class TiltSubsystem implements Subsystem {

    private final TalonSRX talonSRX;
    private final DigitalInput sensor0;
    private final DigitalInput sensor1;
    private boolean check0 = false;
    private boolean check1 = false;

    public TiltSubsystem(TalonSRX talonSRX,
                         DigitalInput sensor0,
                         DigitalInput sensor1) {
        this.talonSRX = talonSRX;
        this.sensor0 = sensor0;
        this.sensor1 = sensor1;
        register();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Sensor 0", !sensor0.get());
        SmartDashboard.putBoolean("Sensor 1", !sensor1.get());
    }

    private void tilt(double output) {
        output *= 0.5;

        this.check0 = !sensor0.get() && output < 0;
        this.check1 = !sensor1.get() && output > 0;

        if (check0 || check1) {
            talonSRX.set(ControlMode.PercentOutput, 0);
        } else {
            talonSRX.set(ControlMode.PercentOutput, output);
        }
    }

    public Command createTiltUp(DoubleSupplier up) {
        return run(() -> tilt(up.getAsDouble())).withName("Up");
    }

    public Command createTiltDown(DoubleSupplier down) {
        return run(() -> tilt(-down.getAsDouble())).withName("Down");
    }

    public Command stop(){
        return run(() -> talonSRX.set(ControlMode.PercentOutput, 0)).withName("Stop");
    }


}
