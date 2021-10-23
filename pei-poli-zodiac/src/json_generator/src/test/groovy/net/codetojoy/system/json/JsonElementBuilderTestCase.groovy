package net.codetojoy.system.json

import net.codetojoy.custom.Info
import net.codetojoy.system.Signs

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonElementBuilderTestCase {
    def jsonBuilder = new JsonElementBuilder()

    @Test
    void testBuildWithElements() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def birthday = "January 27"
        def riding = "Salzburg"
        def party = "PC"     // which party would Mozart join?

        def info = new Info(honourific: "", name: name, zodiac: zodiac,
                            birthday: birthday, riding: riding,
                           party: party, hasSign: true)
        def infos = []
        infos << info

        // test
        def result = jsonBuilder.buildWithElements(infos)

        // this is ridiculous, but YOLO:

        def jsonSlurper = new JsonSlurper()
        def resultMap = jsonSlurper.parseText(result)
        def topLevelChildren = resultMap["children"]

        assertEquals(resultMap["name"], "zodiac")
        assertEquals(topLevelChildren.size(), 4)

        def fireNode = topLevelChildren[0]
        def waterNode = topLevelChildren[1]
        def airNode = topLevelChildren[2]
        def earthNode = topLevelChildren[3]

        assertEquals(fireNode["name"], "Fire")
        assertEquals(waterNode["name"], "Water")
        assertEquals(airNode["name"], "Air")
        assertEquals(earthNode["name"], "Earth")

        def fireChildren = topLevelChildren[0]["children"]
        def waterChildren = topLevelChildren[1]["children"]
        def airChildren = topLevelChildren[2]["children"]
        def earthChildren = topLevelChildren[3]["children"]

        fireChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
                assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], BuilderConstants.NONE)
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }

        waterChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
                assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], BuilderConstants.NONE)
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }

        airChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
                assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

                if (sign == "Aquarius") {
            assertEquals(grandChild["name"], "Mozart")
            assertEquals(grandChild["party"], "PC")
            assertEquals(grandChild["size"], 1000)
            } else {
            assertEquals(grandChild["name"], BuilderConstants.NONE)
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
            }
        }

        earthChildren.each { child ->
            def sign = child["name"]
            assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
                assertEquals(child["children"].size(), 1)
            def grandChild = child["children"][0]

            assertEquals(grandChild["name"], BuilderConstants.NONE)
            assertEquals(grandChild["party"], "unknown")
            assertEquals(grandChild["size"], 1000)
        }
    }
}
