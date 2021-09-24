
package net.codetojoy.system

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

    def generateJson(def infos, def outputFile) {
        def json = new JsonBuilder().build(infos)

        new File(outputFile).withWriter { writer ->
            writer.write(json)
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


