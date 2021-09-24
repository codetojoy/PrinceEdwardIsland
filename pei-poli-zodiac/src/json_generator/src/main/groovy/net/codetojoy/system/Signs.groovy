
package net.codetojoy.system

class Signs {
  static final def DISPLAY_SIGNS = [
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
	    "Unknown",
        ]

  static final def UNKNOWN_DATA_SIGN = "unknown"

  static final def DATA_SIGNS = [
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
            "pisces",
	    UNKNOWN_DATA_SIGN
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