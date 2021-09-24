
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

    def build(def infos) {
        def children = buildChildren(infos)
        def jsonMap = ["name" : "zodiac", "children" : children]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
