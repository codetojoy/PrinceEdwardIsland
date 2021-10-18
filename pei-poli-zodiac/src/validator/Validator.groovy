
@Grab(group='org.mod4j.org.apache.commons', module='lang', version='2.1.0')

import org.apache.commons.lang.StringEscapeUtils

class DummyForGrab {}

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

    // "Hon.","Dennis King","Scorpio","November 1","Brackley - Hunter River","PC","Wikipedia"
    result.name = getQualifiedName(tokens[0], tokens[1])
    result.sign = tokens[2].replaceAll('"', '')

    return result
}

def collectEntries(def file) {
    def result = []
    def isHeader = true

    file.eachLine { line ->
        if (! isHeader) {
            def trimLine = line.trim()
            result << parseLine(trimLine)
        } else {
            isHeader = false
        }
    }

    return result
}

def findSignForLine(def signs, def line) {
    def result = signs.find { sign ->
        def pureLine = StringEscapeUtils.unescapeJava(line)
        return pureLine.indexOf("\"${sign}\"") != -1
    }

    return result
}

def findSign(def lines, def index) {
    def result = null

    def SIGNS = [
        "Aries",
        "Taurus",
        "Gemini",
        "Cancer",
        "Leo",
        "Virgo",
        "Libra",
        "Scorpio",
        "Sagittarius",
        "Capricorn",
        "Aquarius",
        "Pisces",
    ]

    def isFound = false

    for (def i = index; (!isFound) && i >= 0; i--) {
        def line = lines[i]
        def sign = findSignForLine(SIGNS, line)
        if (sign != null) {
            result = sign
            isFound = true
        }
    }

    return result
}

def findEntry(def entry, def lines) {
    def result = false

    def targetName = entry.name

    for (def i = 0; (! result) && i < lines.size(); i++) {
        def pureLine = StringEscapeUtils.unescapeJava(lines[i])
        def isFound = pureLine.indexOf(targetName) != -1
        if (isFound) {
            def currentSign = findSign(lines, i)
            result = (currentSign == entry.sign)
        }
    }

    if (!result) {
        println "TRACER could not find '${targetName}' '${entry.sign}'"
        System.exit(-1)
    }

    return result
}

def findEntries(def officialEntries, def file) {
    def result = true
    def lines =  file.readLines()

    officialEntries.each { entry ->
        def tmpResult = findEntry(entry, lines)
        if (! tmpResult) {
            result = false
        }
    }

    return result
}

// ------ main

def srcDir = args[0]
def officialFile = new File("${srcDir}/zodiac.csv")
def officialEntries = collectEntries(officialFile)
println "# official entries: " + officialEntries.size()

def file

println "validate #1"
file = new File("${srcDir}/zodiac.json")
assert findEntries(officialEntries, file)

println "validate #2"
file = new File("${srcDir}/zodiac_elements.json")
assert findEntries(officialEntries, file)
