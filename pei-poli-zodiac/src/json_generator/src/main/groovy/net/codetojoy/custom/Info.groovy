
package net.codetojoy.custom

class Info {
    def honourific = ""
    def name = ""
    def zodiac = ""
    def birthday = ""
    def riding = ""
    def party = ""
    def hasSign = false

    def getQualifiedName() {
        def prefix = (honourific) ? honourific + " " : ""
        return "${prefix}${name}"
    }
}
