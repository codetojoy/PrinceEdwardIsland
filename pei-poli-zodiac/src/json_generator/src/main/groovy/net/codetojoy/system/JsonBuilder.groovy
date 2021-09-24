
package net.codetojoy.system

import groovy.json.*

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

class JsonBuilder {
    static final def NAME = "name"
    static final def PARTY = "party"
    static final def SIZE = "size"
    static final def NONE = "None identified (yet)"
    static final def DEFAULT_SIZE = 1000
    static final def UNKNOWN_SIGN = "unknown"

    def getSizeForSign(def infos, def sign) {
        def result = DEFAULT_SIZE
        def count = infos.findAll{ it.zodiac == sign}.size()
        result = result / count
        return result
    }

    def buildUnknown() {
        return ["$NAME": NONE, "$PARTY": UNKNOWN_SIGN, "$SIZE": DEFAULT_SIZE]
    }

    def buildChildrenForSign(def infos, def displaySign) {
        def children = []
        infos.each { info ->
            def thisDisplaySign = new Signs().getDisplaySign(info.zodiac)
            if (thisDisplaySign == displaySign) {
                def person = [:]
                person[NAME] = info.name
                person[PARTY] = info.party
                person[SIZE] = getSizeForSign(infos, info.zodiac)
                children << person
            }
        }
        if (children.isEmpty()) {
            children << buildUnknown()
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

    def build(def infos) {
        def children = buildChildren(infos)
        def jsonMap = ["name" : "zodiac", "children" : children]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
