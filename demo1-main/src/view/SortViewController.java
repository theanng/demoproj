package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SortViewController {
    public Stage mainMenuStage;
    public void setMainMenuStage(Stage stage) {
        this.mainMenuStage = stage;
    }
    @FXML
    public Button backButton;
    @FXML
    public ToggleButton createArrayButton;
    @FXML
    public ChoiceBox<String> choiceBox;
    @FXML
    public HBox arrayOptionsBox;
    @FXML
    public Button emptyButton;
    @FXML
    public Button userListButton;
    @FXML
    public Button randomButton;
    @FXML
    public void handleBackButtonAction() throws IOException {
        // Lấy Stage hiện tại của QueueView
        Stage currentStage = (Stage) backButton.getScene().getWindow();
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
    @FXML
    public void handleCreateArrayButtonAction() {
        arrayOptionsBox.setVisible(createArrayButton.isSelected());
    }
}
