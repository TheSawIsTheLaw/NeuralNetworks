package perceptron.neurons

class ANeuron(initFunction: (Double) -> Double, activateFunction: (Double) -> Double) :
    ActivationNeuron(initFunction, activateFunction, 0.0) {

    fun calculateBias() {
        bias = 0.0
        for (i in inputWeights) if (i > 0) bias++ else bias--
    }
}