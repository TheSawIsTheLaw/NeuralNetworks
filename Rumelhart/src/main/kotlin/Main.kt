import org.jfree.data.xy.XYDataset
import javax.swing.*
import java.awt.*
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation


class Main(private val errors: List<Double>) : JFrame("Error function") {

    init {
        add(createChartPanel(), BorderLayout.CENTER)

        setSize(640, 480)
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }

    private fun createChartPanel(): JPanel {
        val chart = ChartFactory.createXYLineChart(
            "Error function",
            "Epoch", "Value", createDataset(), PlotOrientation.VERTICAL, false, false, false
        )

        return ChartPanel(chart)
    }

    private fun createDataset(): XYDataset {
        val series = XYSeries("Epochs")

        errors.forEachIndexed { index, error ->
            series.add(index, error)
        }

        return XYSeriesCollection().apply {
            addSeries(series)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val csvData = CsvReader.read("iris.csv")
            val minMax = csvData.calculateMinMax()
            val mlp = MultiLayerPerceptron(
                csvData.calculateInputAmount(), csvData.calculateOutputAmount(),
                hiddenNeuronAmount = 2, minValue = minMax.first, maxValue = minMax.second
            )

            val errors = mlp.learnAllUntil(csvData.data, { it < 0.07 }, { epoch, error ->
                if (epoch % 100 == 0) {
                    println("Currently at epoch $epoch, with an error of $error")
                }
            })

            SwingUtilities.invokeLater { Main(errors).isVisible = true }

            val csvToCheck = CsvReader.read("irisToCheck.csv")
            val result = mlp.learnAll(csvToCheck.data)
            println("So... We've got: $result")
        }
    }
}