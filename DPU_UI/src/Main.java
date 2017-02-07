import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage arg0) throws Exception {
        StackPane root = new StackPane();
        VBox bx = new VBox();
        bx.setAlignment(Pos.CENTER);
        TextField userName = new TextField("User Name");
        userName.setMaxWidth(200);
        TextField email = new TextField("Email");
        email.setMaxWidth(200);
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                ProgressIndicator pi = new ProgressIndicator();
                VBox box = new VBox(pi);
                box.setAlignment(Pos.CENTER);
                // Grey Background
                bx.setDisable(true);
                root.getChildren().add(box);
            }
        });
        bx.getChildren().addAll(userName, email, submit);
        root.getChildren().add(bx);
        Scene c = new Scene(root);
        arg0.setScene(c);
        arg0.setMinWidth(500);
        arg0.setMinHeight(500);
        arg0.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}