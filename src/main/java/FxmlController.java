import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


public class FxmlController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField seq1FileId;

    @FXML
    private TextField seq2FileId;
    @FXML
    private TextField matchId;
    @FXML
    private TextField replaceId;
    @FXML
    private TextField insertId;
    @FXML
    private TextField deleteId;
    @FXML
    private TextField gapExtId;
    @FXML
    private CheckBox localId;
    @FXML
    private CheckBox globalId;
    @FXML
    private TextArea textArea;

    Stage stage;
    private String sequenceQuery;
    private String sequenceTarget;

    @FXML
    void initialize() {

    }

    @FXML
    void QueryFile(MouseEvent event) {
        sequenceQuery = chooseFile(seq1FileId);
    }

    @FXML
    void TargetFile(MouseEvent event) {
        sequenceTarget = chooseFile(seq2FileId);
    }

    private String chooseFile(TextField seqFile) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                seqFile.setText(selectedFile.getAbsolutePath());
                return readFile(selectedFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @FXML
    void startProcess(MouseEvent event) {
        DnaProcessor dnaProcessor = new DnaProcessor.DnaProcessorBuilder(sequenceQuery, sequenceTarget).buildDeletePenalty(deleteId.getText())
                .buildGapExtendPenalty(gapExtId.getText()).buildInsertPenalty(insertId.getText()).buildMatchPenalty(matchId.getText())
                .buildReplacePenalty(replaceId.getText()).build();

        String processResponse = dnaProcessor.process(localId.isSelected(), globalId.isSelected());
        textArea.setText(processResponse);
    }


    void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
    }

    String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public void clear(MouseEvent mouseEvent) {
        textArea.setText("");
        matchId.setText("0");
        replaceId.setText("0");
        insertId.setText("0");
        deleteId.setText("0");
        gapExtId.setText("0");
    }
}
