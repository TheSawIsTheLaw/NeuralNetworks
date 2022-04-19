import kotlin.math.exp
import kotlin.math.pow

class HiddenLayer(inputAmount: Int, outputAmount: Int) : Layer(inputAmount, outputAmount) {

    fun calculateDeltas(previousDeltas: List<Double>, previousLayer: Layer): List<Double> {
        return neurons.mapIndexed { index: Int, neuron: Neuron ->
            val g = exp(neuron.lastActivation) / (exp(neuron.lastActivation) + 1).pow(2.0)

            g * previousDeltas.mapIndexed { deltaIndex, delta ->
                delta * previousLayer.neurons[deltaIndex].weights[index]
            }.sum()
        }
    }
}