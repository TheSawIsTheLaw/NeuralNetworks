abstract class Layer(private val inputAmount: Int, private val outputAmount: Int) {

    val neurons: List<Neuron> = List(outputAmount) { Neuron(inputAmount) }

    fun feedForward(input: List<Double>): List<Double> {
        return List(outputAmount) { neurons[it].feedForward(input) }
    }

    fun propagateBack(deltas: List<Double>, previousTransfers: List<Double>) {
        deltas.forEachIndexed { index, delta ->
            neurons[index].propagateBack(delta, previousTransfers)
        }
    }
}