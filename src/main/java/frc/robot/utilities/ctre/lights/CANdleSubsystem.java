// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utilities.ctre.lights;

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.FireAnimation;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.RgbFadeAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;
import com.ctre.phoenix.led.StrobeAnimation;
import com.ctre.phoenix.led.TwinkleAnimation;
import com.ctre.phoenix.led.TwinkleOffAnimation;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANdleSubsystem extends SubsystemBase{

    private final CANdle candle;
    private final int LedCount;
    private Animation animate;
    private AnimationTypes currentAnimation;
    private int redChannelAmount = 255;
    private int greenChannelAmount = 215;
    private int blueChannelAmount = 0;

    public enum AnimationTypes {
        ColorFlow,
        Fire,
        Larson,
        Rainbow,
        RgbFade,
        SingleFade,
        Strobe,
        Twinkle,
        TwinkleOff,
        SetAll
    }

    public CANdleSubsystem(int deviceId, int LedCount) {
        this.candle = new CANdle(deviceId, "rio");
        this.LedCount = 400;
        this.currentAnimation = AnimationTypes.ColorFlow;
        SmartDashboard.putString("CurrentAnimation", "ColorFlow");
        this.animate = new ColorFlowAnimation(128, 20, 70, 0, 0.7, LedCount, Direction.Forward);
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = true;
        configAll.disableWhenLOS = false;
        configAll.stripType = LEDStripType.GRB;
        configAll.brightnessScalar = 0.1;
        configAll.vBatOutputMode = VBatOutputMode.Modulated;
        candle.configAllSettings(configAll, 100);

        SmartDashboard.putNumber("RedCh", 255);
        SmartDashboard.putNumber("GreenCh", 101);
        SmartDashboard.putNumber("BlueCh", 0);
        


    }

    public void changeAnimation(AnimationTypes toChange) {
        currentAnimation = toChange;
        
        switch(toChange)
        {
            case ColorFlow:
            animate = new ColorFlowAnimation(128, 20, 70, 0, 0.7, LedCount, Direction.Forward);
            SmartDashboard.putString("CurrentAnimation", "ColorFlow");
                break;
            case Fire:
            animate = new FireAnimation(0.5, 0.7, LedCount, 0.7, 0.5);
            SmartDashboard.putString("CurrentAnimation", "Fire");
                break;
            case Larson:
            animate = new LarsonAnimation(0, 255, 46, 0, 1, LedCount, BounceMode.Front, 3);
            SmartDashboard.putString("CurrentAnimation", "Larson");
                break;
            case Rainbow:
            animate = new RainbowAnimation(1, 0.1, LedCount);
            SmartDashboard.putString("CurrentAnimation", "Rainbow");
                break;
            case RgbFade:
            animate = new RgbFadeAnimation(0.7, 0.4, LedCount);
            SmartDashboard.putString("CurrentAnimation", "RgbFade");
                break;
            case SingleFade:
            animate = new SingleFadeAnimation(50, 2, 200, 0, 0.5, LedCount);
            SmartDashboard.putString("CurrentAnimation", "SingleFade");
                break;
            case Strobe:
            animate = new StrobeAnimation(240, 10, 180, 0, 98.0 / 256.0, LedCount);
            SmartDashboard.putString("CurrentAnimation", "Strobe");
                break;
            case Twinkle:
            animate = new TwinkleAnimation(30, 70, 60, 0, 0.4, LedCount, TwinklePercent.Percent6);
            SmartDashboard.putString("CurrentAnimation", "Twinkle");
                break;
            case TwinkleOff:
            animate = new TwinkleOffAnimation(70, 90, 175, 0, 0.8, LedCount, TwinkleOffPercent.Percent100);
            SmartDashboard.putString("CurrentAnimation", "TwinkleOff");
                break;
            case SetAll:
            animate = null;
            SmartDashboard.putString("CurrentAnimation", "SetAll");
                break;
        }
    }

    public void incrementAnimation() {
        switch(currentAnimation) {
            case ColorFlow: changeAnimation(AnimationTypes.Fire); break;
            case Fire: changeAnimation(AnimationTypes.Larson); break;
            case Larson: changeAnimation(AnimationTypes.Rainbow); break;
            case Rainbow: changeAnimation(AnimationTypes.RgbFade); break;
            case RgbFade: changeAnimation(AnimationTypes.SingleFade); break;
            case SingleFade: changeAnimation(AnimationTypes.Strobe); break;
            case Strobe: changeAnimation(AnimationTypes.Twinkle); break;
            case Twinkle: changeAnimation(AnimationTypes.TwinkleOff); break;
            case TwinkleOff: changeAnimation(AnimationTypes.ColorFlow); break;
            case SetAll: changeAnimation(AnimationTypes.ColorFlow); break;
        }
    }
    
    public void decrementAnimation() {
        switch(currentAnimation) {
            case ColorFlow: changeAnimation(AnimationTypes.TwinkleOff); break;
            case Fire: changeAnimation(AnimationTypes.ColorFlow); break;
            case Larson: changeAnimation(AnimationTypes.Fire); break;
            case Rainbow: changeAnimation(AnimationTypes.Larson); break;
            case RgbFade: changeAnimation(AnimationTypes.Rainbow); break;
            case SingleFade: changeAnimation(AnimationTypes.RgbFade); break;
            case Strobe: changeAnimation(AnimationTypes.SingleFade); break;
            case Twinkle: changeAnimation(AnimationTypes.Strobe); break;
            case TwinkleOff: changeAnimation(AnimationTypes.Twinkle); break;
            case SetAll: changeAnimation(AnimationTypes.ColorFlow); break;
        }
    }
    
    public void setColors() {
        changeAnimation(AnimationTypes.SetAll);
    }

    public void setLEDs(int r, int g, int b){
        candle.setLEDs(r, g, b, 0, 0, 255);
    }

    public void setReadyToFireAnimation(){
        animate = new StrobeAnimation(redChannelAmount, greenChannelAmount, blueChannelAmount, 0, 50.0 / LedCount, LedCount);
        candle.animate(animate);
    }

    public void setChargingAnimation(){
        animate = new ColorFlowAnimation(redChannelAmount, greenChannelAmount, blueChannelAmount, 0, 0.7, LedCount, Direction.Forward);
        candle.animate(animate);
    }

    public void Off(){
        animate = null;
        candle.animate(animate);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        if(animate == null) {
            candle.setLEDs(0, 0, 0);
        } else {
            candle.animate(animate);
        }
        candle.modulateVBatOutput(1);

        redChannelAmount = (int)SmartDashboard.getNumber("RedCh", 255);
        greenChannelAmount = (int)SmartDashboard.getNumber("GreenCh", 223);
        blueChannelAmount = (int)SmartDashboard.getNumber("BlueCh", 0);
        
    }

}
