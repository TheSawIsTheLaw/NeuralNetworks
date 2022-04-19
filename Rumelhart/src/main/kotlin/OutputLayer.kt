import kotlin.math.exp
import kotlin.math.pow

class OutputLayer(inputAmount: Int, outputAmount: Int) : Layer(inputAmount, outputAmount) {

    fun calculateDeltas(trainerValues: List<Double>): List<Double> {
        if (trainerValues.size != neurons.size) {
            throw IllegalArgumentException(
                "The input must have a size of ${neurons.size}. Actual size: " +
                        "${trainerValues.size}"
            )
        }

        return neurons.mapIndexed { index: Int, neuron: Neuron ->
            val g = exp(neuron.lastActivation) / (Math.exp(neuron.lastActivation) + 1).pow(2.0)
            val delta = g * (neuron.lastTransfer - trainerValues[index])

            delta
        }
    }
}