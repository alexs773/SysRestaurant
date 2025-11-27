package pe.edu.upeu.sysrestaurant.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sysrestaurant.components.StageManager;
import pe.edu.upeu.sysrestaurant.components.Toast;
import pe.edu.upeu.sysrestaurant.dto.SessionManager;
import pe.edu.upeu.sysrestaurant.model.Usuario;
import pe.edu.upeu.sysrestaurant.service.IUsuarioService;

import java.io.IOException;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private IUsuarioService us;
    @FXML
    TextField txtUsuario;
    @FXML
    PasswordField txtClave;
    @FXML
    Button btnIngresar;

    @FXML
    public void cerrar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }


    @FXML
    public void login(ActionEvent event) throws IOException {
        try {
            Usuario usu = us.loginUsuario(txtUsuario.getText(), txtClave.getText());
            if (usu!=null) {
                SessionManager.getInstance().setUserId(usu.getIdUsuario());
                SessionManager.getInstance().setUserName(usu.getUser());

                SessionManager.getInstance().setUserPerfil(usu.getIdPerfil().getNombre());
                FXMLLoader loader = new  FXMLLoader(getClass().getResource("/view/maingui.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent mainRoot = loader.load();
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getBounds();
                Scene mainScene = new Scene(mainRoot,bounds.getWidth(), bounds.getHeight()-30);
                mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResource("/img/store.png").toExternalForm()));
                stage.setScene(mainScene);
                stage.setTitle("SysRestaurante");
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setResizable(true);
                StageManager.setPrimaryStage(stage);
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                stage.show();
            } else {
                Stage stage = (Stage) ((Node)
                        event.getSource()).getScene().getWindow();
                double with = stage.getWidth() * 2;
                double h = stage.getHeight() / 2;
                Toast.showToast(stage, "Credencial invalido!! intente nuevamente", 2000, with, h);
            }
        } catch (Exception e) {
            logger.error("Error durante el login", e);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Toast.showToast(stage, "Error al iniciar sesión", 2000, stage.getWidth() / 2, stage.getHeight() / 2);
        }
    }


}


