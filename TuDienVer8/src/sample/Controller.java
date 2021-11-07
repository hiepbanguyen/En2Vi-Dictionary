package sample;

import jaco.mp3.player.MP3Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.controlsfx.control.textfield.TextFields;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea suggestArea;

    private String currentWord;
    private boolean legitWord = false;


    @FXML
    private void searchTheWord(ActionEvent event) {
        suggestArea.setText("");
        if (searchBar.getText().length() == 0) {
            textArea.setText("Bạn chưa nhập từ nào.");
            legitWord = false;
        } else {
            currentWord = searchBar.getText();
            textArea.setText(Database.getMeaning(currentWord));
            legitWord = true;
            if(textArea.getText().equals("Không tồn tại từ trong từ điển.")) {
                legitWord = false;
                Spelling spellfix = new Spelling();
                String correctWord = spellfix.correct(currentWord);
                if (!correctWord.equals(currentWord)) {
                    suggestArea.setText("Ý bạn là:\n" + spellfix.correct(currentWord));
                }
            }
        }
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        if (!legitWord) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.setTitle("Lỗi!");
            alert.setHeaderText("Bạn chưa tìm kiếm từ nào!");
            alert.showAndWait();
            return;
        }
        if (yesToDelete()) {
            Database.deleteWord(currentWord);
            textArea.setText("");
        }
    }

    private boolean yesToDelete() {
        boolean delete = false;
        ButtonType yesButton = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.WARNING,
                "", yesButton, noButton);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Bạn có chắc mình muốn xóa từ này không?");
        alert.setGraphic(new ImageView(this.getClass().getResource("trashcan.png").toString()));

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(noButton) == yesButton) {
            delete = true;
        }

        return delete;
    }

    public void addButtonClicked(ActionEvent event) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Thêm từ mới");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 10, 10));

        TextField from = new TextField();
        TextArea to = new TextArea();

        gridPane.add(new Label("Từ mới:"), 0, 0);
        gridPane.add(from, 1, 0);
        gridPane.add(new Label("Định nghĩa:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(from::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> Database.addWord(pair.getKey(), pair.getValue()));
    }

    public void editButtonClicked(ActionEvent event) {
        if (!legitWord) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.setTitle("Lỗi!");
            alert.setHeaderText("Bạn chưa tìm kiếm từ nào!");
            alert.showAndWait();
            return;
        }
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Chỉnh sửa");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 10, 10));

        TextField from = new TextField(currentWord);
        from.setDisable(true);
        TextArea to = new TextArea(Database.getMeaning(currentWord));

        gridPane.add(new Label("Từ:"), 0, 0);
        gridPane.add(from, 1, 0);
        gridPane.add(new Label("Định nghĩa:"), 0, 1);
        gridPane.add(to, 1, 1);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(from::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(pair -> Database.editWord(pair.getKey(), pair.getValue()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFields.bindAutoCompletion(searchBar,( t -> Database.d.target.stream().filter(elem ->
                elem.toLowerCase().startsWith(t.getUserText().toLowerCase())).limit(20).collect(Collectors.toList())));

    }

    public void audioButtonClicked(ActionEvent event) throws MalformedURLException, InterruptedException {
        if (legitWord) {
            URL url = new URL("https://ssl.gstatic.com/dictionary/static/sounds/oxford/" + currentWord + "--_gb_1.mp3");
            MP3Player m = new MP3Player(url);
            m.play();
            while (!m.isStopped()) {
                Thread.sleep(0);
            }
        }
    }
}
