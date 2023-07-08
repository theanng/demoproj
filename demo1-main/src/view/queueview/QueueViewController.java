package view.queueview;

import array.RandomArrayGenerator;
import algorithms.queue.Queue;
import view.SortViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class QueueViewController extends SortViewController {


    private Circle[] circles;

    private int[] createdArray;

    private Queue queue;

    private RandomArrayGenerator randomArrayGenerator;
    @FXML
    private ToggleButton peekButton;
    @FXML
    private HBox peekBox;
    @FXML
    private Button frontButton;
    @FXML
    private Button back1Button;
    @FXML
    private HBox enqueueBox;
    @FXML
    private TextField inputTextField;
    @FXML
    private Button goButton;
    @FXML
    private Button dequeueButton;
    @FXML
    private AnchorPane canvas;


    public QueueViewController() {
        randomArrayGenerator = new RandomArrayGenerator();
        queue = new Queue(12); // Replace "12" with the desired size of the stack
    }
    public void handleBackButtonAction() throws IOException {
        // Lấy Stage hiện tại của QueueView
        Stage currentStage = (Stage) back1Button.getScene().getWindow();
        // Đóng Stage hiện tại
        currentStage.close();
        // Tạo FXMLLoader để tải file fxml của MainMenu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/MainMenu.fxml"));
        Parent mainMenu = loader.load();
        Scene scene = new Scene(mainMenu);
        // Tạo một Stage mới cho MainMenu
        Stage mainMenuStage = new Stage();
        mainMenuStage.setScene(scene);
        // Hiển thị cửa sổ MainMenu
        mainMenuStage.show();
    }
    public void handleCreateArrayButtonAction(ActionEvent actionEvent) {
        arrayOptionsBox.setVisible(createArrayButton.isSelected());
    }

    private void drawArray(int[] array ) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double radius = 15.0; // Bán kính của hình tròn
        double horizontalSpacing = 20.0; // Khoảng cách ngang giữa các hình tròn

        double startX = 0.0; // Tọa độ x của hình tròn đầu tiên
        double startY = (canvasHeight - 2 * radius) / 2.0; // Tọa độ y của hình tròn đầu tiên

        // Xóa các hình tròn và mũi tên hiện tại trên canvas
        canvas.getChildren().clear();

        // Vẽ các hình tròn và số
        Circle[] circles = new Circle[array.length];
        Text[] texts = new Text[array.length];

        for (int i = 0; i < array.length; i++) {
            double circleX = startX + i * (radius * 2 + horizontalSpacing);
            double circleY = startY;

            // Vẽ hình tròn
            Circle circle = new Circle(circleX, circleY, radius);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.BLACK);
            canvas.getChildren().add(circle);
            circles[i] = circle;

            // Hiển thị số
            int number = array[i];
            Text text = new Text(String.valueOf(number));
            text.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_BOLD, Font.getDefault().getSize()));
            text.setFill(Color.BLACK);
            text.setTextOrigin(VPos.CENTER);
            text.setX(circleX - text.getBoundsInLocal().getWidth() / 2);
            text.setY(circleY);
            canvas.getChildren().add(text);
            texts[i] = text;

            // Vẽ mũi tên (nếu có)
            if (i < array.length - 1) {
                double arrowStartX = circleX + radius;
                double arrowStartY = circleY;
                double arrowEndX = circleX + radius + horizontalSpacing;
                double arrowEndY = circleY;

                Line arrowLine = new Line(arrowStartX, arrowStartY, arrowEndX, arrowEndY);
                arrowLine.setStroke(Color.WHITE);
                arrowLine.setStrokeWidth(2);
                canvas.getChildren().add(arrowLine);

                // Vẽ dấu mũi tên
                double arrowSize = 5.0; // Kích thước dấu mũi tên

                Polygon arrowHead = new Polygon();
                arrowHead.getPoints().addAll(
                        arrowEndX - arrowSize, arrowEndY - arrowSize / 2,
                        arrowEndX - arrowSize, arrowEndY + arrowSize / 2,
                        arrowEndX, arrowEndY
                );
                arrowHead.setFill(Color.WHITE);
                arrowHead.setStroke(Color.WHITE);
                arrowHead.setStrokeWidth(2);
                canvas.getChildren().add(arrowHead);
            }
        }
    }
    @FXML
    public void handleEmptyButtonAction() {
        queue.clear();
        // In ra thông báo hàng đợi đã được xóa
        System.out.println("Đã xóa hàng đợi.");
        // In ra hàng đợi đã cập nhật
        queue.print();
        // Vẽ lại mảng đã cập nhật
        drawArray(queue.toArray());
    }
    public void handleUserListButtonAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("User Defined List");
        dialog.setHeaderText("Enter a list of elements separated by commas");
        dialog.setContentText("List:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String input = result.get();
            String[] elements = input.split(",");
            int[] userListArray = new int[elements.length];

            for (int i = 0; i < elements.length; i++) {
                int value = Integer.parseInt(elements[i].trim());
                userListArray[i] = value;
            }

            createdArray = userListArray;

            // Clear the stack and push elements from the new createdArray
            queue.clear();
            for (int element : createdArray) {
                queue.enqueue(element);
            }

            // Print the updated stack
            queue.print();
            drawArray(queue.toArray());
        } else {
            System.out.println("User defined list canceled. Cannot create new Stack.");

        }
    }
    public void handleRandomButtonAction() {
        String selectedOption = choiceBox.getValue();
        int size = Integer.parseInt(selectedOption);
        int[] randomArray = randomArrayGenerator.generateRandomArray(size);
        createdArray = randomArray;
        // Clear the stack and push elements from the new createdArray
        queue.clear();
        for (int element : createdArray) {
            queue.enqueue(element);
        }
        // Print the updated stack
        queue.print();
        drawArray(queue.toArray());
    }
    public void handlePeekButtonAction() {
        if (queue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Queue is empty");
            alert.setHeaderText(null);
            alert.setContentText("Queue is empty. Cannot peek element.");
            alert.showAndWait();
            return;
        }else {
            peekBox.setVisible(true);
        }
    }
    @FXML
    public void handleFrontButtonAction() {
        int frontItem = queue.peek();
        System.out.println("Phần tử đầu hàng đợi: " + frontItem);
        int[] currentArray = queue.toArray();
        drawArray(currentArray);

        int frontIndex = 0; // Vị trí front
        Circle frontCircle = circles[frontIndex];
        frontCircle.setFill(Color.ORANGE);
    }
    public void handleBack1ButtonAction() {
        if (queue.isEmpty()) {
            System.out.println("Hàng đợi trống.");
            return;
        }
        int backItem = queue.peekBack();
        System.out.println("Phần tử cuối hàng đợi: " + backItem);
        int[] currentArray = queue.toArray();
        drawArray(currentArray);

        int backIndex = queue.size() - 1; // Vị trí back
        Circle backCircle = circles[backIndex];
        backCircle.setFill(Color.ORANGE);
    }

    public void handleEnqueueButtonAction() {enqueueBox.setVisible(true);}

    public void handleGoButtonAction() {
    }
    @FXML
    public void handleDequeueButtonAction() {
        if (queue.isEmpty()) {
            System.out.println("Hàng đợi trống. Không thể xóa phần tử.");
            return;
        }

        int removedItem = queue.dequeue();
        System.out.println("Phần tử đã xóa: " + removedItem);
        System.out.println("Hàng đợi sau khi xóa:");
        queue.print();
        int[] currentArray = queue.toArray();
        drawArray(currentArray);
    }

    public void initialize() {
        // Perform any initialization tasks here
        // ...
    }

}
