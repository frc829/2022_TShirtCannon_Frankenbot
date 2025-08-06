// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class ShirtCannonSubsystem implements Subsystem {
    private final Solenoid solenoid;
    private final Compressor compressor;
    public final Trigger readyToFire;

    public ShirtCannonSubsystem(
            Solenoid solenoid,
            Compressor compressor
    ) {
        this.solenoid = solenoid;
        this.compressor = compressor;
        readyToFire = new Trigger(compressor::isEnabled).negate();
        register();
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Solenoid Valve Open", solenoid.get());
        SmartDashboard.putBoolean("Airing Up", this.compressor.isEnabled());
    }

    private void idle() {
        if (solenoid.get()) {
            solenoid.set(false);
        }
    }

    private void fire() {
        if (!solenoid.get() && !compressor.isEnabled()) {
            solenoid.set(true);
        } else if (compressor.isEnabled()) {
            solenoid.set(false);
        }
    }

    public Command createIdle() {
        return run(this::idle).withName("Idle");
    }

    public Command createFire() {
        return run(this::fire).withName("Fire");
    }
}
