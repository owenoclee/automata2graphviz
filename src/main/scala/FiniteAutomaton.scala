case class InvalidFiniteAutomatonException(message: String) extends Exception {
    override def getMessage: String = message
}

class FiniteAutomaton(states: Set[String], initial: String, finals: Set[String], alphabet: Set[Char],
                      transitions: Set[(String, Char, String)]) {
    //states that have at least one transition to/from
    val tStates = transitions.map{case (s1, _, _) => s1}.union(transitions.map{case (_, _, s2) => s2})

    //sanity checking
    if(!states.contains(initial))
        throw InvalidFiniteAutomatonException("Initial state is not in the set of states")
    else if(states.union(finals) != states)
        throw InvalidFiniteAutomatonException("Set of final states contains one or more unknown states")
    else if(states.union(tStates) != states)
        throw InvalidFiniteAutomatonException("Set of transitions contains one or more unknown states")
}
