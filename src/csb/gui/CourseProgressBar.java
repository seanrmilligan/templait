package csb.gui;


import csb.data.CoursePage;
import csb.file.CourseSiteExporter;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class CourseProgressBar extends Stage {
    ProgressBar bar;
    ProgressIndicator indicator;
    Button button;
    Label processLabel;
    int numTasks = 0;
    ReentrantLock progressLock;

    
    public CourseProgressBar (CoursePage[] pages) throws Exception {
        progressLock = new ReentrantLock();
        VBox box = new VBox();

        HBox toolbar = new HBox();
        bar = new ProgressBar(0);      
        indicator = new ProgressIndicator(0);
        toolbar.getChildren().add(bar);
        toolbar.getChildren().add(indicator);
        
        button = new Button("Restart");
        processLabel = new Label();
        processLabel.setFont(new Font("Serif", 36));
        box.getChildren().add(toolbar);
        box.getChildren().add(button);
        box.getChildren().add(processLabel);
        
        Scene scene = new Scene(box);
        this.setScene(scene);

                button.setOnAction(e -> {
                Task<Void> task = new Task<Void>() {
                    int task = numTasks++;
                    double max = 200;
                    double perc;
                    @Override
                    protected Void call() throws Exception {
                        try {
                            progressLock.lock();
                        for (int i = 0; i < pages.length; i++) {
                            System.out.println(i);
                            perc = i/pages.length;
                            
                            // THIS WILL BE DONE ASYNCHRONOUSLY VIA MULTITHREADING
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    bar.setProgress(perc);
                                    indicator.setProgress(perc);
                                    processLabel.setText("Task #" + task);
                                }
                            });

                            // SLEEP EACH FRAME
                            //try {
                                Thread.sleep(100);
                           // } catch (InterruptedException ie) {
                          //      ie.printStackTrace();
                          //  }
                        }}
                        finally {
                                progressLock.unlock();
                                }
                        return null;
                    }
                };
                // THIS GETS THE THREAD ROLLING
                Thread thread = new Thread(task);
                thread.start();            
        }); 
		this.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}