package perceptron.neurons

abstract class Neuron(
    var initFunction: (value: Double) -> Double,
    var inputWeights: ArrayList<Double> = arrayListOf(),
    var bias: Double = 0.0
) {

    private var currentCount = 0
    fun initWeights(count: Int) {
        for (i in 0 until count) {
            inputWeights.add(initFunction(0.0))
        }

        bias = initFunction(0.0)

        currentCount = count
    }

    fun reinitWeights() {
        for (i in inputWeights.indices) {
            inputWeights[i] = initFunction(0.0)
        }

        bias = initFunction(0.0)
    }

    open fun correct(expected: Double) {
        // DO-NOTHING
    }
}