import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LyricsAnimation extends Application {
    private int index = 0;
    private final String[] lyrics = {
        "Yang ku ingin",
  "Bukan sekedar hanya untuk pacaran",
  "Yang ku mau memberikan seluruh cinta",
  "Sampai akhir waktu nanti",
  "♪♪♪",
    };
    private final int[] lyricDurations = {3, 5, 5, 3, 8}; 

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        BackgroundFill initialBackground = new BackgroundFill(getGradient(0), null, null);
        root.setBackground(new Background(initialBackground));
        
        Text lyricText = new Text();
        lyricText.setFont(Font.font("Arial", 30));
        lyricText.setFill(Color.WHITE);
        root.getChildren().add(lyricText);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lyrics Animation");
        primaryStage.show();
        
        animateLyrics(lyricText, root);
        animateBackground(root);
    }
    
    private void animateLyrics(Text lyricText, StackPane root) {
        int currentDuration = lyricDurations[index];
        showTypingEffect(lyricText, lyrics[index], () -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(currentDuration));
            pause.setOnFinished(event -> {
                index = (index + 1) % lyrics.length;
                animateLyrics(lyricText, root);
            });
            pause.play();
        });
    }

    private void showTypingEffect(Text lyricText, String text, Runnable onComplete) {
        lyricText.setText("");
        Timeline typingTimeline = new Timeline();
        for (int i = 0; i < text.length(); i++) {
            final int charIndex = i;
            typingTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100 * charIndex),
                e -> lyricText.setText(text.substring(0, charIndex + 1))));
        }
        typingTimeline.setOnFinished(e -> onComplete.run());
        typingTimeline.play();
    }
    
    private void animateBackground(StackPane root) {
        Timeline bgTimeline = new Timeline();
        for (int i = 0; i <= 5; i++) {
            int step = i;
            bgTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(i * 2),
                e -> root.setBackground(new Background(new BackgroundFill(getGradient(step), null, null)))));
        }
        bgTimeline.setCycleCount(Animation.INDEFINITE);
        bgTimeline.play();
    }

    private LinearGradient getGradient(int step) {
        Stop[] stops;
        switch (step % 4) {
            case 0: stops = new Stop[]{ new Stop(0, Color.BLUE), new Stop(1, Color.PURPLE) }; break;
            case 1: stops = new Stop[]{ new Stop(0, Color.PURPLE), new Stop(1, Color.BLUE) }; break;
            case 2: stops = new Stop[]{ new Stop(0, Color.BLUE), new Stop(1, Color.PURPLE) }; break;
            default: stops = new Stop[]{ new Stop(0, Color.PURPLE), new Stop(1, Color.BLUE) }; break;
        }
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
