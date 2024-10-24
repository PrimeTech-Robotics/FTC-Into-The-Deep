package org.firstinspires.ftc.teamcode.ro025.Outtake;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.teamcode.ro025.Gamepad.GamepadClass;

public class Extension {

    private PIDController controller;

    public static double p =0 , i = 0 ,d = 0;
    public static double f =0;
    public static double target = 0;
    public final double MAX_TICKS = 0; //setam mai incolo
    public final double MIN_TICKS = 0; //setam mai incolo
    public final double increment = 0; //setam mai incolo
    enum LiftState{
        MAX, INRANGE,  MIN
    }

    LiftState liftState = LiftState.MIN;

    DcMotorEx extindere_left = null;
    DcMotorEx extindere_right = null;

    private static Extension instance = null;

    public static synchronized Extension getInstance() { //creezi o instanta
        if (instance == null) {
            instance = new Extension();
        }
        return instance;
    }

    public void init() {
        controller = new PIDController(p, i, d);

        extindere_left = hardwareMap.get(DcMotorEx.class, "extindere_left");
        extindere_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extindere_right = hardwareMap.get(DcMotorEx.class, "extindere_right");
        extindere_right.setDirection(DcMotorSimple.Direction.REVERSE); extindere_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

       // telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }
    //PID stuff
    public void loop() {
        //FSM PID type shit
        double last_target = target;

        switch(liftState){
            case MIN:
                target = target + increment * GamepadClass.getInstance().right_trigger();
                if(last_target != target){
                    liftState = LiftState.INRANGE;
                }
                break;
            case MAX:
                target = target - increment * GamepadClass.getInstance().left_trigger();
                if(last_target != target){
                    liftState = LiftState.INRANGE;
                }
                break;
            case INRANGE:
                target = target + increment * GamepadClass.getInstance().right_trigger() - increment * GamepadClass.getInstance().left_trigger();
                if(target > MAX_TICKS){
                    liftState = LiftState.MAX;
                    target = MAX_TICKS;
                }
                if(target < MIN_TICKS){
                    liftState = LiftState.MIN;
                    target = MIN_TICKS;
                }
                break;
        }

        controller.setPID(p, i, d);
        int lift_pos = extindere_left.getCurrentPosition();
        double pid = controller.calculate(lift_pos, target);
        //mai trebuie sa vorbesc cu sover de feedforward
        double power = pid;

        extindere_right.setPower(power);
        extindere_left.setPower(power);

        /*telemetry.addData("pos ", lift_pos);
        telemetry.addData("target ", target);
        telemetry.update();*/
    }

}
