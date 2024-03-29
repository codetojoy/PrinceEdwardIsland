
package net.codetojoy.system

import net.codetojoy.system.json.BuilderConstants

// TODO: use property files! this is brutal
class Locale {
    def mode
    def translationMap = [:]

    Locale(def mode) {
        this.mode = mode
        assert (this.mode == "en" || this.mode == "fr")

        translationMap["Aries"] = "Bélier"
        translationMap["Taurus"] = "Taureau"
        translationMap["Gemini"] = "Gémeaux"
        translationMap["Cancer"] = "Cancer"
        translationMap["Leo"] = "Lion"
        translationMap["Virgo"] = "Vierge"
        translationMap["Libra"] = "Balance"
        translationMap["Scorpio"] = "Scorpion"
        translationMap["Sagittarius"] = "Sagittaire"
        translationMap["Capricorn"] = "Capricorne"
        translationMap["Aquarius"] = "Verseau"
        translationMap["Pisces"] = "Poissons"
        translationMap["Unknown"] = "Inconnues"

        translationMap["Fire"] = "Feu"
        translationMap["Water"] = "Eau"
        translationMap["Earth"] = "Terre"
        translationMap["Air"] = "Air"

        translationMap[BuilderConstants.NONE] = "Aucun ici!"
    }

    def getInverse(def t) {
        def result = null
        def isDone = false
        translationMap.each { k, v ->
            if ((! isDone) && v == t) {
                result = k
                isDone = true
            }
        }
        return result
    }

    def get(def s) {
        return (mode == "en") ? s : translate(s)
    }

    def translate(def s) {
        def result = "FR: UNKNOWN"
        if (translationMap.keySet().contains(s)) {
            result = translationMap.get(s)
        }
        return result
    }
}
