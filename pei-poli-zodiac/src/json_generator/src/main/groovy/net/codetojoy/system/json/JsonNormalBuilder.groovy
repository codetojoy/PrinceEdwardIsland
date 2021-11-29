
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

class JsonNormalBuilder extends BaseBuilder {

    def buildChildren(def infos, def locale) {
        def children = []
        Signs.DISPLAY_SIGNS.each { sign ->
            // NOV 2021: Mark McLane is unknown
            // if (sign != Signs.UNKNOWN_DISPLAY_SIGN) {
                def childMap = [:]
                childMap[NAME] = locale.get(sign)
                childMap[CHILDREN] = buildChildrenForSign(infos, sign, locale)
                children << childMap
            // }
        }
        return children
    }

    def buildNormal(def infos, def locale) {
        def children = buildChildren(infos, locale)
        def jsonMap = ["name" : "zodiac", "children" : children]
        def json = JsonOutput.toJson(jsonMap)
        return JsonOutput.prettyPrint(json)
    }
}
