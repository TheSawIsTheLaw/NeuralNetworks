package perceptron.layers

import perceptron.neurons.SNeuron

class SLayer() : Layer() {

    fun addNeuron(initFunction: (Double) -> Double, transformFunction: (Double) -> Double) {
        neurons.add(SNeuron(initFunction, transformFunction))
    }

    override fun solve(inputs: ArrayList<Double>): ArrayList<Double> {
        val results = arrayListOf<Double>()

        neurons.forEachIndexed { index, it ->
            results.add((it as SNeuron).solve(inputs[index]))
        }

        return ArrayList(neurons.mapIndexed { index, it ->
            (it as SNeuron).solve(inputs[index])
        })
    }
}