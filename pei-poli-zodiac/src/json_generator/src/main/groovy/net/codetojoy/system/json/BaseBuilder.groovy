
package net.codetojoy.system.json

import groovy.json.*

import static BuilderConstants.*
import net.codetojoy.system.Signs

// TODO: prefer composition over inheritence
abstract class BaseBuilder {

    def getSizeForSign(def infos, def sign) {
        def result = DEFAULT_SIZE
        def count = infos.findAll{ it.zodiac == sign}.size()
        result = result / count
        return result
    }

    def buildUnknown(def locale) {
        return ["$NAME": locale.get(NONE), "$PARTY": UNKNOWN_SIGN, "$SIZE": DEFAULT_SIZE]
    }

    def buildChildrenForSign(def infos, def displaySign, def locale) {
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
            children << buildUnknown(locale)
        }
        return children
    }
}
