import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

public class NucleotidProperty {

    private StringProperty nucleotidName;
    private StringProperty costToA;
    private StringProperty costToC;
    private StringProperty costToG;
    private StringProperty costToT;

    public NucleotidProperty(String nucleotidName, String costToA, String costToC, String costToG, String costToT) {
        this.nucleotidName =  new SimpleStringProperty(nucleotidName);
        this.costToA = new SimpleStringProperty(costToA);
        this.costToC = new SimpleStringProperty(costToC);
        this.costToG = new SimpleStringProperty(costToG);
        this.costToT = new SimpleStringProperty(costToT);
    }

    public String getNucleotidName() {
        return nucleotidName.get();
    }

    public void setNucleotidName(String nucleotidName) {
        this.nucleotidName.set(nucleotidName);
    }

    public String getCostToA() {
        return costToA.get();
    }

    public void setCostToA(String costToA) {
        this.costToA.set(costToA);
    }

    public String getCostToC() {
        return costToC.get();
    }

    public void setCostToC(String costToC) {
        this.costToC.set(costToC);
    }

    public String getCostToG() {
        return costToG.get();
    }

    public void setCostToG(String costToG) {
        this.costToG.set(costToG);
    }

    public String getCostToT() {
        return costToT.get();
    }

    public void setCostToT(String costToT) {
        this.costToT.set(costToT);
    }
}
