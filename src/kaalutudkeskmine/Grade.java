package kaalutudkeskmine;

import java.util.ArrayList;

/**
 * Created by Madis on 27.12.2016.
 */
public class Grade {
    private int grade;
    private int points;


    public Grade(int grade, int points) {
        this.grade = grade;
        this.points = points;
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public static double calculateWeightedAverage(ArrayList<Grade> grades) {
        int gradeSum = 0;
        int pointSum = 0;
        if (!grades.isEmpty()) {
            for (Grade grade : grades) {
                gradeSum += grade.getGrade() * grade.getPoints();
                pointSum += grade.getPoints();
            }


            return (double) gradeSum / pointSum;
        } else {
            return 0;
        }

    }
}
