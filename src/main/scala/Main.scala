import java.io.File

import uk.co.turingatemyhamster.graphvizs.exec._

object Main {
    def main(args: Array[String]): Unit = {
        val argString = args.mkString
        val fa = stringToFiniteAutomaton(argString)
        println(fa.getImage(DotFormat.svg))
    }

    def stringToFiniteAutomaton(st: String): FiniteAutomaton = {
        var str = st.replaceAll(" ", "") //remove whitespace

        //parse string of sets i.e. "{7,3,4},{2,1}" into a sequence like "7,3,4", "2,1"
        val setRegex = """\{[a-zA-Z0-9,\(\)]*\}""".r
        val tmpSetArgs = setRegex.findAllIn(str).toSeq
        val setArgs: Seq[String] = tmpSetArgs.map { s => """\{|\}""".r.replaceAllIn(s, "") }

        //parse string of tuples i.e. "(7,q,4),(3,p,4)" into a sequence like "7,q,4", "3,p,4"
        val tupleRegex = """\([a-zA-Z0-9,]*\)""".r
        val tmpTupleArgs = tupleRegex.findAllIn(setArgs(3)).toSeq

        //turns "7,q,4"-style strings into ("7", 'q', "4") set of tuples
        val tmpTransitionTuples = tmpTupleArgs.map { s => """\(|\)""".r.replaceAllIn(s, "").split(',').toSeq }
        val transitionTuples = tmpTransitionTuples.map { seq => (seq(0), seq(1).head, seq(2)) }.toSet

        new FiniteAutomaton(
            setArgs(0).split(',').toSet,
            setArgs(1).split(',').toSet,
            setArgs(2).split(',').toSet.map { s: String => s.head },
            transitionTuples,
            str.substring(str.lastIndexOf(',') + 1)
        )
    }
}
