package perceptron.neurons

class RNeuron(
    initFunction: (Double) -> Double,
    activateFunction: (Double) -> Double,
    val learningSpeed: Double,
    bias: Double
) :
    ActivationNeuron(initFunction, activateFunction, bias) {
    override fun correct(expected: Double) {
        if (expected != lastResult) {
            val newWeights = arrayListOf<Double>()
            for (it in inputWeights.zip(lastInputs)) {
                newWeights.add(it.first - lastResult * learningSpeed * it.second)
            }

            inputWeights = newWeights
        }

        bias += lastResult * learningSpeed
    }
}