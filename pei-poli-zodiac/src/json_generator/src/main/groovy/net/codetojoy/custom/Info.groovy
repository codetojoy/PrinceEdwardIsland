
package net.codetojoy.custom

import net.codetojoy.utils.Utils

class Info {
    def name = ""
    def zodiac = ""
    def birthday = ""
    def riding = ""
    def party = ""
    def hasSign = false

    static def utils = new Utils()

    static String getHeader() {
        utils.buildList(["Name","Zodiac","Birthday","Riding","Party"])
    }

    String toString() {
        utils.buildList([name, zodiac, birthday, riding, party])
    }
}
