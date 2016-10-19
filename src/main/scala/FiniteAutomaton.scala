import uk.co.turingatemyhamster.graphvizs.dsl._
import uk.co.turingatemyhamster.graphvizs.exec._

case class InvalidFiniteAutomatonException(message: String) extends Exception {
    override def getMessage: String = message
}

class FiniteAutomaton(states: Set[String], finals: Set[String], alphabet: Set[Char],
                      transitions: Set[(String, Char, String)], initial: String) {
    //states that have at least one transition to/from
    val tStates = transitions.map{case (s1, _, _) => s1}.union(transitions.map{case (_, _, s2) => s2})
    val tAlphabet = transitions.map{case (_, a, _) => a} //letters used by transitions
    
    //sanity checking
    if(!states.contains(initial))
        throw InvalidFiniteAutomatonException("Initial state is not in the set of states")
    else if(states.union(finals) != states)
        throw InvalidFiniteAutomatonException("Set of final states contains one or more unknown states")
    else if(states.union(tStates) != states)
        throw InvalidFiniteAutomatonException("Set of transitions contains one or more unknown states")
    else if(alphabet.union(tAlphabet) != alphabet)
        throw InvalidFiniteAutomatonException("Set of transitions contains a letter not in the alphabet")

    //map transitions to a sequence of dot statements
    var statements: Seq[Statement] = transitions.map{case (s1, c, s2) => s1 --> s2 :| ("label" := c.toString)}.toSeq
    statements = statements :+ AssignmentStatement("rankdir", "LR")
    val finiteAutomatonDot = StrictDigraph(
        "finiteAutomaton",
        statements: _* //unpack statements into var-arg
    )

    def getImage(format: DotFormat): String = {
        dot2dot[Graph, String](finiteAutomatonDot, DotLayout.dot, format)
    }
}
