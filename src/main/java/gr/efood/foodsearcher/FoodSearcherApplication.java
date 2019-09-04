package gr.efood.foodsearcher;

import gr.efood.foodsearcher.ui.events.StageReadyEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class FoodSearcherApplication extends Application {

    public static void main(String[] args) {
        launch(FoodSearcherApplication.class, args);
    }

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(FoodSearcherApplication.class);
        log.info("Document Base: [{}]", getHostServices().getDocumentBase());
    }

    @Override
    public void start(Stage stage) throws Exception {
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() throws Exception {
        context.close();
        Platform.exit();
    }

}
