package net.codetojoy.system

import net.codetojoy.custom.Info

import groovy.json.*

import org.junit.*
import static org.junit.Assert.*

class JsonBuilderTestCase {
    def jsonBuilder = new JsonBuilder()

    @Test
    void testCanary() {
        assertEquals(2+2, 4)
    }

    @Test
    void testBuild() {
        def name = "Mozart"
        def zodiac = "Aquarius"
        def birthday = "January 27"
        def riding = "Salzburg"
        def party = "PC" 	// which party would Mozart join?

        def info = new Info(name: name, zodiac: zodiac,
                            birthday: birthday, riding: riding,
                           party: party, hasSign: true)
	def infos = []
	infos << info

	// test
	def result = jsonBuilder.build(infos)

	def jsonSlurper = new JsonSlurper()
	def resultMap = jsonSlurper.parseText(result)
	def topLevelChildren = resultMap["children"]

	assertEquals(resultMap["name"], "zodiac")
	assertEquals(topLevelChildren.size(), 13)

	// this is ridiculous, but YOLO:

	topLevelChildren.each { child ->
	    def sign = child["name"]
	    assertTrue(Signs.DISPLAY_SIGNS.contains(sign))
            assertEquals(child["children"].size(), 1)
	    def grandChild = child["children"][0]

            if (sign == "Aquarius") {
		assertEquals(grandChild["name"], "Mozart")
		assertEquals(grandChild["party"], "PC")
		assertEquals(grandChild["size"], 1000)
	    } else {
		assertEquals(grandChild["name"], "None identified (yet)")
		assertEquals(grandChild["party"], "unknown")
		assertEquals(grandChild["size"], 1000)
	    }
	}
    }
}