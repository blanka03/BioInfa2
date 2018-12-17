import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;


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
    private CheckBox customMatrixId;
    @FXML
    private TextArea textArea;
    @FXML
    private TableView tableId;

    Stage stage;
    private String sequenceQuery;
    private String sequenceTarget;

    private ObservableList<NucleotidProperty> data = FXCollections.observableArrayList(
            new NucleotidProperty("A", "5", "-4", "-4", "-4"),
            new NucleotidProperty("C", "-4", "5", "-4", "-4"),
            new NucleotidProperty("G", "-4", "-4", "5", "-4"),
            new NucleotidProperty("T", "-4", "-4", "-4", "5")
    );

    Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
        @Override
        public TableCell call(TableColumn p) {
            return new EditingCell();
        }
    };

    @FXML
    void initialize() {

        TableColumn zeroColumn = new TableColumn();
        zeroColumn.setCellValueFactory(
                new PropertyValueFactory<NucleotidProperty, String>("nucleotidName"));
        zeroColumn.setMaxWidth(65);
        zeroColumn.setResizable(false);
        zeroColumn.setEditable(true);

        TableColumn firstColumn = new TableColumn("A");
        firstColumn.setCellValueFactory(
                new PropertyValueFactory<NucleotidProperty, String>("costToA"));
        firstColumn.setMaxWidth(65);
        firstColumn.setResizable(false);
        firstColumn.setEditable(true);
        firstColumn.setCellFactory(cellFactory);

        TableColumn secondColumn = new TableColumn("C");
        secondColumn.setCellValueFactory(
                new PropertyValueFactory<NucleotidProperty, String>("costToC"));
        secondColumn.setMaxWidth(65);
        secondColumn.setResizable(false);
        secondColumn.setEditable(true);
        secondColumn.setCellFactory(cellFactory);

        TableColumn thirdColumn = new TableColumn("G");
        thirdColumn.setCellValueFactory(
                new PropertyValueFactory<NucleotidProperty, String>("costToG"));
        thirdColumn.setMaxWidth(65);
        thirdColumn.setResizable(false);
        thirdColumn.setEditable(true);
        thirdColumn.setCellFactory(cellFactory);

        TableColumn fourthColumn = new TableColumn("T");
        fourthColumn.setCellValueFactory(
                new PropertyValueFactory<NucleotidProperty, String>("costToT"));
        fourthColumn.setMaxWidth(65);
        fourthColumn.setResizable(false);
        fourthColumn.setEditable(true);
        fourthColumn.setCellFactory(cellFactory);

        tableId.getColumns().addAll(zeroColumn, firstColumn, secondColumn, thirdColumn, fourthColumn);
        tableId.setItems(data);
        tableId.setEditable(true);
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

        String processResponse = dnaProcessor.process(localId.isSelected(), globalId.isSelected(), customMatrixId.isSelected(), createCustomMatrix());
        textArea.setText(processResponse);
    }

    private String createCustomMatrix() {
        StringBuilder stringBuilder = new StringBuilder(" A C G T");
        ObservableList<NucleotidProperty> data = tableId.getItems();
        for (NucleotidProperty row : data) {
            stringBuilder.append("\n")
                    .append(row.getNucleotidName())
                    .append(" ")
                    .append(row.getCostToA())
                    .append(" ")
                    .append(row.getCostToC())
                    .append(" ")
                    .append(row.getCostToG())
                    .append(" ")
                    .append(row.getCostToT());

        }
        return stringBuilder.toString();
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
