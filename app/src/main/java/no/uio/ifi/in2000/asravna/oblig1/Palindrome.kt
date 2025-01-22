package no.uio.ifi.in2000.asravna.oblig1


fun isPalindrome(tekst: String): Boolean {
    val cleanedText = tekst.filter { it.isLetterOrDigit() }.replace(" ", "").lowercase()
    val reversedStr = cleanedText.reversed()
    return cleanedText == reversedStr
}

