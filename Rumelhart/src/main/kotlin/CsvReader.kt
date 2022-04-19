import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object CsvReader {

    fun read(filename: String): CsvResult {
        val classMap = HashMap<String, Double>()
        var nextClassIndex = 0.0

        val data = Files.readAllLines(Paths.get(CsvReader.javaClass.classLoader.getResource(filename).toURI())).map {
            val split = it.split(",").map(String::trim)

            if (split.size < 2) {
                null
            } else {
                try {
                    val classToUse = classMap.getOrPut(split.last()) {
                        val nextClassIndexToInsert = nextClassIndex
                        nextClassIndex++
                        nextClassIndexToInsert
                    }

                    split.subList(0, split.size - 2).map(String::toDouble) to listOf(classToUse)
                } catch (exception: NumberFormatException) {
                    null
                }
            }
        }.filterNotNull()

        return CsvResult(data)
    }

    class CsvResult(val data: List<Pair<List<Double>, List<Double>>>) {
        fun calculateMinMax(): Pair<Double, Double> {
            if (data.isEmpty()) {
                throw IllegalStateException("The data list cannot be empty")
            }

            val concatenatedValues = data.flatMap { it.first.plus(it.second) }

            return concatenatedValues.minOrNull()!! to concatenatedValues.maxOrNull()!!
        }

        fun calculateInputAmount(): Int {
            if (data.isEmpty()) {
                throw IllegalStateException("The data list cannot be empty")
            }

            return data.first().first.size
        }

        fun calculateOutputAmount(): Int {
            return 1
        }
    }
}