import perceptron.Perceptron
import perceptron.dataset.Dataset
import perceptron.dataset.moreValuesXD
import perceptron.dataset.values
import perceptron.neurons.ANeuron
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

val dataset = values.map {
    Dataset(ArrayList(it.key.map { innerIt -> innerIt.digitToInt() }), it.value)
}

val testDataset = moreValuesXD.map {
    Dataset(ArrayList(it.key.map { innerIt -> innerIt.digitToInt() }), it.value)
}

fun trainPerceptron(): Perceptron {
    val neuralNetwork = Perceptron()
    val inputCount = dataset[0].inputs.size

    println("Generating layers...")

    for (i in 0 until inputCount) {
        neuralNetwork.sLayer.addNeuron({ it -> it }, { it -> it })
    }

    println("Done.")

    val random = Random()
    val countOfANeurons = 2.0.pow(inputCount - 1).toInt()
    for (pos in 0 until countOfANeurons) {
        val neuron = ANeuron({ it }, { it })

        neuron.inputWeights = ArrayList(inputCount)
        for (i in 0 until inputCount) {
            neuron.inputWeights.add((random.nextInt(3) - 1).toDouble())
        }
        neuron.calculateBias()
        neuralNetwork.aLayer.neurons.add(neuron)
    }

    println("A layer was added")

    for (i in 0 until 10) {
        neuralNetwork.rLayer.addNeuron(countOfANeurons, { 0.0 }, { if (it >= 0.0) 1.0 else -1.0 }, 0.01, 0.0)
    }

    println("R layer was generated")

    neuralNetwork.train(dataset)
    neuralNetwork.optimize(dataset)
    return neuralNetwork
}

fun test(neuronNetwork: Perceptron) {
    val totalClassifications = testDataset.size * testDataset[0].results.size
    var misc = 0

    for (data in testDataset) {
        val results = neuronNetwork.solve(ArrayList(data.inputs.map { it.toDouble() }))

        results.forEachIndexed { index, res ->
            if (res != data.results[index].toDouble())
                misc++
        }
    }

    println("Test ended. Accuracy: ${(totalClassifications - misc).toDouble() / totalClassifications * 100}")
}

fun main() {
    val neuralNetwork = trainPerceptron()
    test(neuralNetwork)
}