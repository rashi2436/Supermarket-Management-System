package supermarket;

public class ModelChart {

    String Label;
    double[] Values;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        this.Label = label;
    }

    public double[] getValues() {
        return Values;
    }

    public void setValues(double[] values) {
        this.Values = values;
    }

    public ModelChart(String label, double[] values) {
        this.Label = label;
        this.Values = values;
    }

    public ModelChart() {
    }

    private String label;
    private double values[];

    public double getMaxValues() {
        double max = 0;
        for (double v : Values) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }
}
