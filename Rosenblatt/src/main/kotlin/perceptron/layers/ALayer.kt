package perceptron.layers

import perceptron.neurons.ANeuron

class ALayer() : Layer() {
    fun addNeuron(inputsCount: Int, initFunction: (Double) -> Double, activateFunction: (Double) -> Double) {
        val neuron = ANeuron(initFunction, activateFunction)
        neuron.initWeights(inputsCount)

        neurons.add(neuron)
    }

    override fun solve(inputs: ArrayList<Double>): ArrayList<Double> {
        return ArrayList(neurons.map {
            (it as ANeuron).solve(inputs)
        })
    }
}