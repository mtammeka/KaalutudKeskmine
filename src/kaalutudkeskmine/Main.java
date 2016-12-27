package kaalutudkeskmine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Madis on 27.12.2016.
 */
public class Main extends Application {
    String firstLine = "Hinne\tPunktikaal\n";
    String summaryText = "Kaalutud keskmine:\n";
    StringBuilder listOfData = new StringBuilder(firstLine);

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        ArrayList<Grade> grades = new ArrayList<>();

        DecimalFormat rounder = new DecimalFormat("#.##");
        rounder.setRoundingMode(RoundingMode.HALF_UP);

        GridPane grid = new GridPane();

        Text gradeFieldName = new Text("Hinne:");
        grid.add(gradeFieldName, 0, 0);
        TextField gradeField = new TextField();
        grid.add(gradeField, 1, 0);

        Text pointsFieldName = new Text("Ainepunktid:");
        grid.add(pointsFieldName, 2, 0);
        TextField pointsField = new TextField();
        grid.add(pointsField, 3, 0);

        Button submitButton = new Button("Lisa");
        grid.add(submitButton, 4, 0);
        Button removeLastButton = new Button("Eemalda viimane");
        grid.add(removeLastButton, 5, 0);

        TextArea contents = new TextArea();
        contents.setEditable(false);

        contents.setText(listOfData.toString());
        grid.add(contents, 0, 1, 5, 20);

        grid.setGridLinesVisible(false);

        submitButton.setOnAction(event -> {

            int grade, points;
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sisend ei sobi hinneteks-punktideks.");

            try {
                grade = Integer.parseInt(gradeField.getText());
                points = Integer.parseInt(pointsField.getText());

            } catch (NumberFormatException exception) {
                // do nothing with exception
                gradeField.setText("");
                pointsField.setText("");
                alert.showAndWait();
                return;
            }

            if ((grade > 5) || (grade < 1) || (points < 1)) {
                alert.showAndWait();
                return;
            }

            grades.add(new Grade(grade, points));

            gradeField.setText("");
            pointsField.setText("");

            listOfData = new StringBuilder(firstLine);
            for (Grade thisGrade : grades) {
                listOfData.append(thisGrade.getGrade());
                listOfData.append("\t\t");
                listOfData.append(thisGrade.getPoints());
                listOfData.append("\n");
            }

            listOfData.append(summaryText);
            double weightedAverage = Grade.calculateWeightedAverage(grades);
            if (weightedAverage != 0) {

                listOfData.append(rounder.format(weightedAverage));
            }

            contents.setText(listOfData.toString());

        });

        removeLastButton.setOnAction(event -> {

            if (!grades.isEmpty()) {
                grades.remove(grades.size() - 1);

                listOfData = new StringBuilder(firstLine);
                for (Grade thisGrade : grades) {
                    listOfData.append(thisGrade.getGrade());
                    listOfData.append("\t\t");
                    listOfData.append(thisGrade.getPoints());
                    listOfData.append("\n");
                }
                listOfData.append(summaryText);
                double weightedAverage = Grade.calculateWeightedAverage(grades);
                if (weightedAverage != 0) {
                    listOfData.append(rounder.format(weightedAverage));
                }
                contents.setText(listOfData.toString());
            }
        });

        Scene scene = new Scene(grid, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
