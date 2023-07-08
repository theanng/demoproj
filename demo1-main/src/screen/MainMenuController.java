package screen;

import java.util.Optional;
import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import view.stackview.*;
import view.queueview.*;

public class MainMenuController {

    @FXML
    private Button stackButton;

    @FXML
    private Button queueButton;

    @FXML
    private Button listButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button exitButton;

    // Các phương thức xử lý sự kiện cho các nút
    @FXML
    private void handleStackButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/stackview/StackView.fxml"));
        Parent stackView = loader.load();
        Scene scene = new Scene(stackView);

        // Tạo một Stage mới cho StackView
        Stage stackStage = new Stage();
        stackStage.setScene(scene);

        // Đặt vị trí và kích thước của cửa sổ
        stackStage.centerOnScreen();
        stackStage.setX((stackStage.getX() + stackStage.getWidth()) / 2 - stackStage.getWidth() / 2);
        stackStage.setY((stackStage.getY() + stackStage.getHeight()) / 2 - stackStage.getHeight() / 2);

        // Gán tiêu đề cho Stage của StackView
        stackStage.setTitle("Stack View");

        // Truyền thông tin về Stage của MainMenu vào StackViewController
        StackViewController stackViewController = loader.getController();
        stackViewController.setMainMenuStage(stackStage);

        // Hiển thị cửa sổ StackView
        stackStage.show();
    }

    @FXML
    private void handleQueueButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/queueview/QueueView.fxml"));
        Parent queueView = loader.load();
        Scene scene = new Scene(queueView);

        // Tạo một Stage mới cho QueueView
        Stage queueStage = new Stage();
        queueStage.setScene(scene);

        // Đặt vị trí và kích thước của cửa sổ
        queueStage.centerOnScreen();
        queueStage.setX((queueStage.getX() + queueStage.getWidth()) / 2 - queueStage.getWidth() / 2);
        queueStage.setY((queueStage.getY() + queueStage.getHeight()) / 2 - queueStage.getHeight() / 2);

        // Gán tiêu đề cho Stage của StackView
        queueStage.setTitle("Queue View");

        // Truyền thông tin về Stage của MainMenu vào QueueViewController
        QueueViewController queueViewController = loader.getController();
        queueViewController.setMainMenuStage(queueStage);

        // Hiển thị cửa sổ StackView
        queueStage.show();
    }


    @FXML
    private void handleListButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listview/ListView.fxml"));
            Parent listView = loader.load();
            Scene scene = new Scene(listView);

            // Tạo một Stage mới cho ListView
            Stage stackStage = new Stage();
            stackStage.setScene(scene);

            // Đặt vị trí và kích thước của cửa sổ
            stackStage.centerOnScreen();
            stackStage.setX((stackStage.getX() + stackStage.getWidth()) / 2 - stackStage.getWidth() / 2);
            stackStage.setY((stackStage.getY() + stackStage.getHeight()) / 2 - stackStage.getHeight() / 2);

            // Hiển thị cửa sổ StackView
            stackStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHelpButtonAction(ActionEvent event) {
        // Xử lý sự kiện khi nút "Help" được nhấn
        String message = "Welcome to the Data Structures Application!\n\n"
                + "This application demonstrates basic operations on different data structures, including Stack, Queue, and List.\n\n"
                + "To get started, select the desired data structure from the menu:\n"
                + "- Stack: Simulates a stack data structure and allows you to perform operations like push and pop.\n"
                + "- Queue: Simulates a queue data structure and allows you to perform operations like enqueue and dequeue.\n"
                + "- List: Simulates a list data structure and allows you to perform operations like add, remove, and search.\n\n"
                + "Enjoy exploring and learning about data structures!\n";

        // Hiển thị thông báo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleExitButtonAction(ActionEvent event) {

        confirmExit();
    }
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Scene scene = exitButton.getScene();
            if (scene != null) {
                Stage stage = (Stage) scene.getWindow();
                stage.setOnCloseRequest(this::handleWindowCloseRequest);
            }
        });
    }

    private void handleWindowCloseRequest(WindowEvent event) {
        event.consume(); // Ngăn không đóng cửa sổ tự động

        confirmExit();
    }

    private void confirmExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            Platform.exit(); // Đóng ứng dụng
        }
    }
}