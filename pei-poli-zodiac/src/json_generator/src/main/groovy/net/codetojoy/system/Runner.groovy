
package net.codetojoy.system

import net.codetojoy.custom.Config
import net.codetojoy.system.json.*

class Runner {
    static final def MODE_NORMAL = "normal"
    static final def MODE_ELEMENTS = "elements"

    def parser

    def Runner() {
        parser = new Config().parser
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

    def generateJson(def mode, def infos, def outputFile, def locale) {
        def json = null

        if (mode.trim().toLowerCase() == MODE_NORMAL) {
            json = new JsonNormalBuilder().buildNormal(infos, locale)
        } else {
            json = new JsonElementBuilder().buildWithElements(infos, locale)
        }

        new File(outputFile).withWriter { writer ->
            writer.write(json)
            writer.write("\n")
        }
    }

    def run(def mode, def infile, def outfile, def locale) {
        if ((! mode.trim().toLowerCase() == MODE_NORMAL) && (! mode.trim().toLowerCase() == MODE_ELEMENTS)) {
            throw new IllegalArgumentException("unknown mode: $mode")
        }
        def infos = buildInfos(infile)
        generateJson(mode, infos, outfile, locale)
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
        def locale = new net.codetojoy.system.Locale(args[3])

        new Runner().run(mode, infile, outfile, locale)
    }
}
