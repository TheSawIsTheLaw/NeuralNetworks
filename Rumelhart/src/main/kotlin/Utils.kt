fun List<Double>.normalize(min: Double, max: Double): List<Double> {
    return this.map {
        it.normalize(min, max)
    }
}

fun Double.normalize(min: Double, max: Double): Double {
    if (min == max) {
        return this
    }

    return (this - min) / (max - min)
}

fun Double.denormalize(min: Double, max: Double): Double {
    if (min == max) {
        return this
    }

    return this * (max - min) + min
}