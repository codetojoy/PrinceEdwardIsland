
/*
"Name","Zodiac","Birthday","Riding","Party","Source"
"Trish Altass","Virgo","","Tyne Valley - Sherbrooke","Green","https://twitter.com/AltassTrish/status/1443356255897243652"
*/

def getRandomItem(def random, def list) {
    int max = list.size() - 1
    assert random instanceof Random
    def index = random.nextInt() % max
    return list[index]
}

def q(s) {
    return "\"$s\""
}

def buildRow(def random, def i) {
    def DATA_SIGNS = [
          "aries", "taurus", "gemini", "cancer", "leo", "virgo",
          "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces", "unknown"
    ]

    def PARTIES = ["PC","Liberal","Green"]
    def name = "person $i"
    def zodiac = getRandomItem(random, DATA_SIGNS)
    def birthday = i 
    def riding = i 
    def party = getRandomItem(random, PARTIES) 
    def source = "n/a"
    return  "${q name},${q zodiac},${q birthday},${q riding},${q party},${q source}"
}

// ---------------------------- 

def random = new Random()

def header = "${q "Name"},${q "Zodiac"},${q "Birthday"},${q "Riding"},${q "Party"},${q "Source"}"
println header

338.times {
    println buildRow(random, it)
}

