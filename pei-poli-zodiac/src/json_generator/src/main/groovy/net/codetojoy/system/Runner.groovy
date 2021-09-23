
package net.codetojoy.system

import groovy.json.*

import net.codetojoy.custom.Config

class Runner {

    def parser
    def outputHeader

    def Runner() {
        def config = new Config()
        parser = config.parser
        outputHeader = config.outputHeader
    }

    def buildInfos(def infile) {
        def infos = []
        def isHeader = true
        def header = ""

        new File(infile).eachLine { line ->
            if (isHeader) {
                isHeader = false
                header = line
            } else {
                def text = "${header}\n${line}\n"
                infos = parser.parseFile(text, infos)
            }
        }

        return infos
    }

    /*
    {
  "name": "zodiac",
  "children": [
    {
      "name": "aries",
      "children": [
        { "name": "Person a-ABC", "size": 1000 },
        { "name": "Person a-DEF", "size": 1000 },
        { "name": "Person a-IJK", "size": 1000 },
        { "name": "Person a-XYZ", "size": 1000 }
      ]
    },
    */

    def buildChildrenForSign(def infos, def displaySign) {
        def children = []
        def signs = new Signs()
        infos.each { info ->
            def thisDisplaySign = signs.getDisplaySign(info.zodiac)
            if (thisDisplaySign == displaySign) {
                def person = [:]
                person["name"] = info.name
                person["party"] = info.party
                person["size"] = 1000
                children << person
            }
        }
        if (children.isEmpty()) {
            children << ["name": "N/A", "party": "N/A", "size": 1000]
        }
        return children
    }

    def buildChildren(def infos) {
        def children = []
        Signs.DISPLAY_SIGNS.each { sign ->
            def childMap = [:]
            childMap["name"] = sign
            childMap["children"] = buildChildrenForSign(infos, sign)
            children << childMap
        }
        return children
    }

    def buildTestChildren() {
        def children = []
        Signs.DATA_SIGNS.each { sign ->
            def childMap = [:]
            childMap["name"] = sign
            // { "name": "Person a-ABC", "size": 1000 },
            def p1 = ["name": "test/$sign person A", "size": 1000]
            def p2 = ["name": "test/$sign person B", "size": 1000]
            childMap["children"] = [p1, p2]
            children << childMap
        }
        return children
    }

    def generateJson(def infos, def outputFile) {
        def children = buildChildren(infos)
        def jsonMap = ["name" : "zodiac", "children" : children]
        def json = JsonOutput.toJson(jsonMap)

        new File(outputFile).withWriter { writer ->
            writer.write(JsonOutput.prettyPrint(json))
            writer.write("\n")
        }
    }

    def run(def infile, def outfile) {
        def infos = buildInfos(infile)
        generateJson(infos, outfile)
    }

    def static void main(String[] args) {
        if (args.size() < 2) {
            println "Usage: groovy Runner.groovy infile outfile"
            System.exit(-1)
        }

        def infile = args[0]
        assert new File(infile).exists()
        def outfile = args[1]

        new Runner().run(infile, outfile)
    }
}


