package org.firstinspires.ftc.teamcode.ro025.Auto.Pose;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import java.util.HashMap;
import java.util.Map;

public abstract class Pose {
    private static final Map<PoseNames, double[]> poses = new HashMap<>();

    public static Pose2d getPose(PoseNames poseName) {
        double[] poseValues = poses.get(poseName);
        if (poseValues != null) {
            return new Pose2d(new Vector2d(poseValues[0], poseValues[1]), Math.toRadians(poseValues[2]));
        } else {
            throw new IllegalArgumentException("Pose " + poseName + " not found.");
        }
    }

    public static void setPose(PoseNames poseName, double x, double y, double headingDegrees) {
        poses.put(poseName, new double[]{x, y, headingDegrees});
    }
}