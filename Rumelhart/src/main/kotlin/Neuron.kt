import java.util.*
import kotlin.math.exp

class Neuron(private val inputAmount: Int) {

    private companion object {
        private const val eta = 0.01
    }

    var weights = List(inputAmount) { (Random().nextDouble() - 0.5) * 4.0 }

    private var lastInput = List(inputAmount) { Double.MIN_VALUE }
    var lastActivation = Double.MIN_VALUE
    var lastTransfer = Double.MIN_VALUE

    fun feedForward(input: List<Double>): Double {
        if (input.size != inputAmount) {
            throw IllegalArgumentException("The input must have a size of $inputAmount. Actual size: ${input.size}")
        }

        lastInput = input
        lastActivation = input.mapIndexed { index, value -> value * weights[index] }.sum()
        lastTransfer = 1.0 / (1.0 + exp(-lastActivation))

        return lastTransfer
    }

    fun propagateBack(delta: Double, previousTransfers: List<Double>) {
        weights = weights.mapIndexed { index, it -> it + (-eta * delta * previousTransfers[index]) }
    }
}