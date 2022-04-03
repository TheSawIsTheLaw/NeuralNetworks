package perceptron.neurons

class SNeuron(initFunction: (Double) -> Double, val transformFunction: (Double) -> Double) :
    Neuron(initFunction) {

    fun solve(input: Double): Double = transformFunction(input)
}