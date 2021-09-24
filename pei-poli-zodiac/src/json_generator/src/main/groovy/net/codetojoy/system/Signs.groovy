
package net.codetojoy.system

class Signs {
  final static def DISPLAY_SIGNS = [
            "Aries", //  ♈",
            "Taurus", //  ♉",
            "Gemini", //  ♊",
            "Cancer", //  ♋",
            "Leo", //  ♌",
            "Virgo", //  ♍",
            "Libra", //  ♎",
            "Scorpio", //  ♏",
            "Sagittarius", //  ♐",
            "Capricorn", //  ♑",
            "Aquarius", //  ♒",
            "Pisces", //  ♓"
        ]

  final static def DATA_SIGNS = [
            "aries",
            "taurus",
            "gemini",
            "cancer",
            "leo",
            "virgo",
            "libra",
            "scorpio",
            "sagittarius",
            "capricorn",
            "aquarius",
            "pisces"
        ]

  def getDisplaySign(def dataSign) {
    def target = dataSign.trim().toUpperCase()
    def index = DATA_SIGNS.findIndexOf{ it.trim().toUpperCase() == target }
    if (index >= 0) {
	return DISPLAY_SIGNS[index]
    } else {
	throw new IllegalStateException("internal error for '$dataSign' : $index")
    }
  }
}