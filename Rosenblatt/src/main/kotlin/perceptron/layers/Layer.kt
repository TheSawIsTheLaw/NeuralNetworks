package perceptron.layers

import perceptron.neurons.Neuron

abstract class Layer(val neurons: ArrayList<Neuron> = arrayListOf()) {

    fun reinitWeights() {
        neurons.forEach { it.reinitWeights() }
    }

    abstract fun solve(inputs: ArrayList<Double>): ArrayList<Double>

    open fun correct(expected: ArrayList<Double>) {
        // DO-NOTHING
    }
}