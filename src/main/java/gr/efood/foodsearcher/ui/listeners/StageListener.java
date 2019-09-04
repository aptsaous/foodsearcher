package gr.efood.foodsearcher.ui.listeners;

import java.io.IOException;
import java.net.URL;

import gr.efood.foodsearcher.ui.events.StageReadyEvent;
import javafx.scene.control.SplitPane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

    private final String applicationTitle;
    private final Resource fxml;
    private final Resource css;
    private final ApplicationContext context;

    @Value("${foodsearcher.ui.app.default-width}")
    private double defaultWidth;

    @Value("${foodsearcher.ui.app.default-height}")
    private double defaultHeight;

    @Value("${foodsearcher.ui.app.min-width}")
    private double minWidth;

    @Value("${foodsearcher.ui.app.min-height}")
    private double minHeight;

    StageListener(
            @Value("${spring.application.ui.title}") String applicationTitle,
            @Value("classpath:/ui.fxml") Resource fxml,
            @Value("classpath:/ui.css") Resource css,
            ApplicationContext context) {
        this.applicationTitle = applicationTitle;
        this.fxml = fxml;
        this.css = css;
        this.context = context;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        try {
            URL url = fxml.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(context::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, defaultWidth, defaultHeight);
            scene.getStylesheets().add(css.getURL().toExternalForm());
            stage.setMinHeight(minHeight);
            stage.setMinWidth(minWidth);
            stage.setTitle(applicationTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}