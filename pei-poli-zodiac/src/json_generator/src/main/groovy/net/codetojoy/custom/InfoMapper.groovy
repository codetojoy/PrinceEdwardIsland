
package net.codetojoy.custom

import net.codetojoy.utils.Utils

class InfoMapper {
    static final def INDEX_NAME = 0
    static final def INDEX_ZODIAC = 1
    static final def INDEX_BIRTHDAY = 2
    static final def INDEX_RIDING = 3
    static final def INDEX_PARTY = 4

    def utils = new Utils()

    def mapLine(def line) {
        def info = null

        try {
            def name = line.getAt(INDEX_NAME)
            def zodiac = line.getAt(INDEX_ZODIAC)
            def birthday = line.getAt(INDEX_BIRTHDAY)
            def riding = line.getAt(INDEX_RIDING)
            def party = line.getAt(INDEX_PARTY)

            if (zodiac) {
                info = new Info(name: name, zodiac: zodiac,
                                birthday: birthday, riding: riding,
                                party: party, hasSign: true)
            } else {
                // System.err.println "TRACER: skipping " + line
            }
        } catch(Exception ex) {
            System.err.println("TRACER caught ex : ${ex.message}")
        }

        return info
    }
}

