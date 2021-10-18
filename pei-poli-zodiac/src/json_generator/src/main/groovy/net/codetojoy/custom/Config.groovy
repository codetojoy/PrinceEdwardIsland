
package net.codetojoy.custom

import net.codetojoy.system.Parser

class Config {
    def parser

    def Config() {
        def infoMapper = new InfoMapper()
        parser = new Parser(infoMapper: infoMapper)
    }
}

