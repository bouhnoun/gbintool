package fr.uvsq.doosa.gbintool

import groovy.util.logging.Slf4j

@Slf4j
class Main {
    static void main(def args) {
        log.info 'Hello'
    }

    String returnHello() {
        'Hello'
    }
}