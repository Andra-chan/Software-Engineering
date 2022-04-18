package library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import library.controllers.LoginController;
import library.controllers.RegisterController;
import library.domain.Book;
import library.domain.User;
import library.repository.BookRepository;
import library.repository.BorrowedBookRepository;
import library.repository.UserRepository;
import library.repository.interfaces.IBookRepository;
import library.repository.interfaces.IBorrowedBookRepository;
import library.repository.interfaces.IUserRepository;
import library.service.Service;
import library.validators.BookValidator;
import library.validators.IValidator;
import library.validators.UserValidator;

import java.io.IOException;

public class App extends Application {
    public static Stage currentStage;

    public static void changeSceneToRegister(Service service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("register.fxml"));
            AnchorPane root = fxmlLoader.load();
            App.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            App.currentStage.setMinHeight(root.getPrefHeight());
            App.currentStage.setMinWidth(root.getPrefWidth());
            App.currentStage.setResizable(false);

            RegisterController controller = fxmlLoader.getController();
            controller.setService(service);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void changeSceneToLogin(Service service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
            BorderPane root = fxmlLoader.load();
            App.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            App.currentStage.setMinHeight(root.getPrefHeight());
            App.currentStage.setMinWidth(root.getPrefWidth());
            App.currentStage.setResizable(false);
            App.currentStage.setMaximized(false);

            LoginController controller = fxmlLoader.getController();
            controller.setService(service);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.currentStage = stage;

        String url = "jdbc:postgresql://localhost:5432/LibraryDB";
        String username = "postgres";
        String password = "mypostgres";

        IValidator<User> userValidator = new UserValidator();
        IUserRepository userRepository = new UserRepository(url, username, password, userValidator);

        IValidator<Book> bookValidator = new BookValidator();
        IBookRepository bookRepository = new BookRepository(url, username, password, bookValidator);

        IBorrowedBookRepository borrowedBookRepository = new BorrowedBookRepository(url, username, password);

        Service service = new Service(userRepository, bookRepository, borrowedBookRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        BorderPane root = fxmlLoader.load();

        LoginController controller = fxmlLoader.getController();
        controller.setService(service);


        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        stage.setTitle("Open Book");
        stage.setScene(scene);
        stage.setMinHeight(root.getPrefHeight());
        stage.setMinWidth(root.getPrefWidth());
        stage.setMaxHeight(root.getPrefHeight());
        stage.setMaxWidth(root.getPrefWidth());
        stage.setResizable(false);
        stage.show();
    }

}