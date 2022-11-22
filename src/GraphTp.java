import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class GraphTp extends JFrame {

    public GraphTp() {

        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        add(chartPanel);

        pack();
        setTitle("Dicho/Seq comparaison");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {
        ///////////////////////////
        LinkedHashMap<Integer, Integer> linearsomme = new LinkedHashMap<Integer, Integer>();
        LinkedHashMap<Integer, Integer> dichosomme = new LinkedHashMap<Integer, Integer>();
        int slinear = 0;
        int sdicho = 0;
        Random rd = new Random();
        Linear linear = new Linear();
        Dicho dicho = new Dicho();
        int N = 1000;
        int nbrExecution = 100;
        int target = -10;
        int[] donnes = new int[N];
        for (int i = 0; i < donnes.length; i++) {
            donnes[i] = rd.nextInt(1000); // storing random integers in an array
            System.out.println(donnes[i]); // printing each array element
        }
//        Arrays.sort(donnes);
        System.out.println("le temps pour la recherche sequencielle");
        for (int i = 0; i < nbrExecution; i++) {
            long debut = System.nanoTime();
            linear.linearSearch(donnes, target);
            long fin = System.nanoTime();
            slinear += fin - debut;
        }
        System.out.println("la moyenne de sequencielle:  " + slinear / nbrExecution);


        for (int i = 0; i <= nbrExecution; i++) {
            long debut = System.nanoTime();
            dicho.binarySearch(donnes, 0, donnes.length - 1, target);
            long fin = System.nanoTime();
            sdicho += fin - debut;
        }
        System.out.println("");
        System.out.println("la moyenne de dicho:  " + sdicho / nbrExecution);

        // table des valeur pour le graphe
        //dicho
        for (int i = 10; i <= 10000; i += 100) {
            int[] internaldonnes = new int[i];
            for (int j = 0; j <= nbrExecution; j++) {
                long debut = System.nanoTime();
                dicho.binarySearch(internaldonnes, 0, internaldonnes.length - 1, target);
                long fin = System.nanoTime();
                sdicho += fin - debut;
            }
            dichosomme.put(i, sdicho / nbrExecution);
        }
        System.out.println(dichosomme);

        //Linear
        for (int i = 10; i <= 10000; i += 100) {
            int[] internaldonnes = new int[i];
            for (int j = 0; j <= nbrExecution; j++) {
                long debut = System.nanoTime();
                linear.linearSearch(internaldonnes, target);
                long fin = System.nanoTime();
                slinear += fin - debut;
            }
            linearsomme.put(i, slinear / nbrExecution);
        }
        System.out.println(linearsomme);


        ///////////////////////////

        var series = new XYSeries("dichotomique");

        for (Map.Entry<Integer, Integer> entry : dichosomme.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            series.add(key, value);
        }
//        series.add(10, 534);
//        series.add(110, 792);
//        series.add(210, 1071);
//        series.add(310, 1421);
//        series.add(410, 1729);
//        series.add(510, 2034);
//        series.add(610, 2365);
//        series.add(710, 2699);
//        series.add(810, 3010);
//        series.add(910, 3323);

        //////////////////////////////

        var series2 = new XYSeries("sequentielle");
        for (Map.Entry<Integer, Integer> entry : linearsomme.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            series2.add(key, value);
        }
//        series2.add(10, 13356);
//        series2.add(110, 14478);
//        series2.add(210, 15084);
//        series2.add(310, 15891);
//        series2.add(410, 16946);
//        series2.add(510, 18261);
//        series2.add(610, 19806);
//        series2.add(710, 21606);
//        series2.add(810, 23669);
//        series2.add(910, 26005);

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Comparaison entre la recherche dichotomique et sequentielle",
                "Taille de donnes",
                "temps (ns)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Comparaison entre la recherche dichotomique et sequentielle",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            var ex = new GraphTp();
            ex.setVisible(true);
        });
    }
}