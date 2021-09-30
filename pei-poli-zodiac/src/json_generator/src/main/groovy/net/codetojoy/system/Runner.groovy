
package net.codetojoy.system

import net.codetojoy.custom.Config

class Runner {
    static final def MODE_NORMAL = "normal"

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

    def run(def mode, def infile, def outfile) {
        if (mode.trim().toLowerCase() == MODE_NORMAL) {
            def infos = buildInfos(infile)
            generateJson(infos, outfile)
        } else {
            throw new IllegalArgumentException("unknown mode: $mode")
        }
    }

    def static void main(String[] args) {
        if (args.size() < 3) {
            println "Usage: groovy Runner.groovy mode infile outfile"
            System.exit(-1)
        }

        def mode = args[0]
        def infile = args[1]
        assert new File(infile).exists()
        def outfile = args[2]

        new Runner().run(mode, infile, outfile)
    }
}


