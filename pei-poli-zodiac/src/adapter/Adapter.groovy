

def getQualifiedName(honourific, name) {
    def thisHonourific = honourific.replaceAll('"', '')
    def thisName = name.replaceAll('"', '')
    def prefix = (thisHonourific) ? thisHonourific + " " : ""
    return "${prefix}${thisName}"
}

def parseLine(def line) {
    def result = new Expando()

    result.line = line
    def tokens = line.split(",")

    // "Honourific","Name","Zodiac","Birthday","Party","Source"
    result.hon = tokens[0].replaceAll('"', '')
    result.name = tokens[1].replaceAll('"', '')
    result.sign = tokens[2].replaceAll('"', '')
    result.party = tokens[4].replaceAll('"', '')

    if (result.party == "Conservative" || result.party == "Progressive Conservative") {
        result.party = "PC"
    }

    return result
}

def collectEntries(def file) {
    def entries = []
    def isHeader = true
    file.eachLine{ line ->
        if (isHeader) {
            isHeader = false
        } else {
            entries << parseLine(line)
        }
    }
    return entries
}

def q(def s) {
    def dq = '"'
    return "${dq}${s}${dq}"
}

// "Honourific","Name","Zodiac","Birthday","Riding","Party","Source"
def buildLine(def info) {
    def empty = q('')
    def hon = q(info.hon)
    def name = q(info.name)
    def sign = q(info.sign)
    def birthday = empty
    def riding = empty
    def party = q(info.party)
    def source = empty
    return "${hon},${name},${sign},${birthday},${riding},${party},${source}"
}

def writeFile(def file, def officialEntries) {
    file.withWriter { writer ->
        writer.write(/"Honourific","Name","Zodiac","Birthday","Riding","Party","Source"/)
        writer.write("\n")
        officialEntries.each { officialEntry ->
            def line = buildLine(officialEntry)
            writer.write(line + "\n")
        }
    }
}

// ------ main

def srcDir = args[0]
def officialFile = new File("${srcDir}/zodiac_premiers.csv")
def officialEntries = collectEntries(officialFile)
println "# official entries: " + officialEntries.size()

def outputFile = new File("./zodiac_premiers_normalized.csv")
writeFile(outputFile, officialEntries)
