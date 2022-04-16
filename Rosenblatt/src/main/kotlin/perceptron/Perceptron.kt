package perceptron

import perceptron.dataset.Dataset
import perceptron.layers.ALayer
import perceptron.layers.RLayer
import perceptron.layers.SLayer
import perceptron.neurons.ANeuron

class Perceptron(
    val sLayer: SLayer = SLayer(), val aLayer: ALayer = ALayer(), val rLayer: RLayer = RLayer()
) {

    fun solve(inputs: ArrayList<Double>): ArrayList<Double> {
        return rLayer.solve(aLayer.solve(sLayer.solve(inputs)))
    }

    fun correct(expected: ArrayList<Double>) {
        rLayer.correct(expected)
    }

    fun train(datasets: List<Dataset>) {
        println(
            "****************\n" + "Education starts"
        )

        var trainingFlag = true
        var currentEpoch = 0

        val totalClassifications = datasets.size * datasets[0].results.size
        var minimumWrongClassifications = totalClassifications
        var stabilityTime = 0
        while (trainingFlag && stabilityTime < 100) {
            var wrongClassifications = 0
            trainingFlag = false
            for (dataset in datasets) {
                val results = solve(ArrayList(dataset.inputs.map { it.toDouble() }))

                results.forEachIndexed { index, result ->
                    if (result.toInt() != dataset.results[index]) {
                        wrongClassifications++
                        correct(ArrayList(dataset.results.map { it.toDouble() }))
                        trainingFlag = true
                    }
                }
            }

            currentEpoch++
            println("Current epoch is: $currentEpoch; Wrong classifications: $wrongClassifications")

            if (minimumWrongClassifications <= wrongClassifications) stabilityTime++
            else {
                minimumWrongClassifications = wrongClassifications
                stabilityTime = 0
            }
        }

        println("Training ended in $currentEpoch epochs\n\nResult accuracy in training is ${(totalClassifications - minimumWrongClassifications) / totalClassifications * 100}")
    }

    fun optimize(datasets: List<Dataset>) {
        println("************")
        println("Optimization is starting")

        val activations = arrayListOf<ArrayList<Int>>()
        aLayer.neurons.forEach { _ -> activations.add(arrayListOf()) }

        val aInputs = arrayListOf<ArrayList<Double>>()
        datasets.forEach {
            aInputs.add(sLayer.solve(ArrayList(it.inputs.map { it.toDouble() })))
        }

        aInputs.forEach {
            aLayer.neurons.forEachIndexed { neuronIndex, neuron ->
                activations[neuronIndex].add((neuron as ANeuron).solve(it).toInt())
            }
        }

        val toRemove = MutableList(aLayer.neurons.size) { false }

        // Мёртвые -- те нейроны, которые не меняют значения своих весов, т.е. от них не зависит результат распознавания
        println("***********")
        println("Counting dead neurons from A Layer")

        activations.forEachIndexed { index, activation ->
            val zeroes = activation.count { it == 0 }
            if (zeroes == 0 || zeroes == aLayer.neurons.size) {
                toRemove[index] = true
            }
        }

        val deadNeurons = toRemove.count { !it }
        println("${toRemove.count { !it }} neurons found to remove")

        println("**********")
        println("Counting correlating neurons from a layer...")

        // Коррелянты - пары нейронов, выдающие одинаковый результат
        for (i in 0 until activations.size - 1) {
            if (!toRemove[i]) {
                for (j in i + 1 until activations.size) {
                    // лучше использовать формулу корреляционной зависимости
                    if (activations[j] == activations[i]) toRemove[j] = true
                }
            }
        }

        println("Number of correlating neurons: ${toRemove.count { it } - deadNeurons}")

        for (i in toRemove.size - 1 downTo 0) {
            if (toRemove[i]) {
                aLayer.neurons.removeAt(i)
                for (j in 0 until rLayer.neurons.size) {
                    rLayer.neurons[j].inputWeights.removeAt(i)
                }
            }
        }

        println("Ok.")
        println("${aLayer.neurons.size} neurons remaining")
    }
}