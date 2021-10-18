
package net.codetojoy.custom

import net.codetojoy.system.Signs

class InfoMapper {
    static final def INDEX_HONOURIFIC = 0
    static final def INDEX_NAME = 1
    static final def INDEX_ZODIAC = 2
    static final def INDEX_BIRTHDAY = 3
    static final def INDEX_RIDING = 4
    static final def INDEX_PARTY = 5

    def mapLine(def line) {
        def info = null

        try {
            def honourific = line.getAt(INDEX_HONOURIFIC)
            def name = line.getAt(INDEX_NAME)
            def zodiac = line.getAt(INDEX_ZODIAC)
            def birthday = line.getAt(INDEX_BIRTHDAY)
            def riding = line.getAt(INDEX_RIDING)
            def party = line.getAt(INDEX_PARTY)

            def hasSign = false
            def zodiacSign = Signs.UNKNOWN_DATA_SIGN

            if (zodiac) {
                hasSign = true
                zodiacSign = zodiac
            }

            info = new Info(honourific: honourific, name: name, zodiac: zodiacSign,
                            birthday: birthday, riding: riding,
                            party: party, hasSign: hasSign)
        } catch(Exception ex) {
            System.err.println("TRACER caught ex : ${ex.message}")
        }

        return info
    }
}

