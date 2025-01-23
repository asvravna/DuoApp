package no.uio.ifi.in2000.asravna.oblig1

fun isPalindrome(tekst: String): Boolean {
    val sanitized = tekst.lowercase()
    return sanitized == sanitized.reversed()
}
