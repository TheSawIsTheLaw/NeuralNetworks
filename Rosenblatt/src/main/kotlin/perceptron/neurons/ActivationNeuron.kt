package perceptron.neurons

open class ActivationNeuron(
    initFunction: (Double) -> Double,
    val activateFunction: (Double) -> Double,
    bias_: Double,
    var lastInputs: ArrayList<Double> = arrayListOf(),
    var lastResult: Double = 0.0
) : Neuron(initFunction, bias = bias_) {

    fun accumulate(inputs: ArrayList<Double>): Double {
        var accumulation = bias
        for (it in inputs.zip(inputWeights)) {
            accumulation += it.first * it.second
        }

        return accumulation
    }

    fun solve(inputs: ArrayList<Double>): Double {
        lastInputs = inputs
        lastResult = activateFunction(accumulate(inputs))

        return lastResult
    }
}