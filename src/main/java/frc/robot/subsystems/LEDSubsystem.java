// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.StrobeAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class LEDSubsystem implements Subsystem {

    private final CANdle candle;
    private final int ledCount;
    private Animation animate;
    private final int redChannelAmount = 255;
    private final int greenChannelAmount = 223;
    private final int blueChannelAmount = 0;
    private boolean isOn = true;

    public LEDSubsystem(CANdle candle, int ledCount) {
        this.candle = candle;
        this.ledCount = ledCount;
    }

    private void setReadyToFireAnimation() {
        if (isOn) {
            animate = new StrobeAnimation(redChannelAmount, greenChannelAmount, blueChannelAmount, 0, 50.0 / ledCount, ledCount);
            candle.animate(animate);
        } else {
            candle.animate(null);
        }
    }

    private void setChargingAnimation() {
        if (isOn) {
            animate = new ColorFlowAnimation(redChannelAmount, greenChannelAmount, blueChannelAmount, 0, 0.7, ledCount, Direction.Forward);
            candle.animate(animate);
        } else {
            candle.animate(null);
        }
    }

    private void toggleOnOff() {
        isOn = !isOn;
    }

    public Command createReadyToFire() {
        return runOnce(this::setReadyToFireAnimation).withName("Ready to Fire");
    }

    public Command createCharging() {
        return runOnce(this::setChargingAnimation).withName("Charging");
    }

    public Command toggle() {
        return runOnce(this::toggleOnOff).withName("Toggle");
    }

}
