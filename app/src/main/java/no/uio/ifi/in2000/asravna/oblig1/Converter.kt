package no.uio.ifi.in2000.asravna.oblig1

import no.uio.ifi.in2000.asravna.oblig1.ui.ConverterUnits
import kotlin.math.roundToInt



fun converter(verdi: Int, enhet: ConverterUnits): Int {
    val liters = when (enhet) {
        ConverterUnits.OUNCE -> verdi * 0.02957
        ConverterUnits.CUP -> verdi * 0.23659
        ConverterUnits.GALLON -> verdi * 3.78541
        ConverterUnits.HOGSHEAD -> verdi * 238.481
    }
    return liters.roundToInt()
}

