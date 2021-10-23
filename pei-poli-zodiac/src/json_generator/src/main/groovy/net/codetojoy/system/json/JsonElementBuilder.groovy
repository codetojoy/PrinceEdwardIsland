
package net.codetojoy.system.json

import groovy.json.*

import static BuilderConstants.* 
import net.codetojoy.system.Signs

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

class JsonElementBuilder {

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
        def children = infos.findResults { info ->
            def dataSign = info.zodiac
            def thisDisplaySign = new Signs().getDisplaySign(dataSign)
            if (thisDisplaySign == displaySign) {
                def person = [:]
                person[NAME] = info.getQualifiedName()
                person[PARTY] = info.party
                person[SIZE] = getSizeForSign(infos, dataSign)
                return person
            } else {
                return null
            }
        }
        if (children.isEmpty()) {
            children << buildUnknown()
        }
        return children
    }

/*
    def buildChildren(def infos) {
        def children = []
        Signs.DISPLAY_SIGNS.each { sign ->
            if (sign != Signs.UNKNOWN_DISPLAY_SIGN) {
                def childMap = [:]
                childMap[NAME] = sign
                childMap[CHILDREN] = buildChildrenForSign(infos, sign)
                children << childMap
            }
        }
        return children
    }
*/

    def buildChildrenWithElement(def infos, def element) {
        def children = []
        def signs = new Signs()
        Signs.DISPLAY_SIGNS.each { sign ->
            if (signs.isSignInElement(sign, element)) {
                def childMap = [:]
                childMap[NAME] = sign
                childMap[CHILDREN] = buildChildrenForSign(infos, sign)
                children << childMap
            }
        }
        return children
    }

    def buildWithElements(def infos) {
        // this is not efficient, but it's a small list
        def fireChildren = buildChildrenWithElement(infos, "Fire")
        def waterChildren = buildChildrenWithElement(infos, "Water")
        def airChildren = buildChildrenWithElement(infos, "Air")
        def earthChildren = buildChildrenWithElement(infos, "Earth")

        def jsonMap = [
            "name" : "zodiac", "children" : [
                ["name": "Fire", "children" : fireChildren],
                ["name": "Water", "children" : waterChildren],
                ["name": "Air", "children" : airChildren],
                ["name": "Earth", "children" : earthChildren],
            ]
        ]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
