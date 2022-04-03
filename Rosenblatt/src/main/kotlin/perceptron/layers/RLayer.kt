package perceptron.layers

import perceptron.neurons.RNeuron

class RLayer() : Layer() {

    override fun solve(inputs: ArrayList<Double>): ArrayList<Double> {
        val results = arrayListOf<Double>()

        neurons.forEach {
            results.add((it as RNeuron).solve(inputs))
        }

        return results
    }

    override fun correct(expected: ArrayList<Double>) {
        neurons.forEachIndexed { index, it ->
            it.correct(expected[index])
        }
    }

    public fun addNeuron(
        inputCount: Int,
        initFunction: (Double) -> Double,
        activateFunction: (Double) -> Double,
        learningSpeed: Double,
        bias: Double
    ) {
        val neuron = RNeuron(initFunction, activateFunction, learningSpeed, bias)
        neuron.initWeights(inputCount)

        neurons.add(neuron)
    }
}