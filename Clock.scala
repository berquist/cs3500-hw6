
/***********************************************************************
 * Clock class
 * 
 * Implements the clock
 * Manages the execution of registered actions at every time step
 *
 ********************************************************************** */

object Clock {

    def create (init:Int):Clock = new Clock(init)

}


class Clock protected (init:Int) {

    private var currentTime = init
    def time ():Int = currentTime

    private var actions:List[ClockAction] = List.empty()

    def callActions ():Unit =
        actions.foreach(x => x.action(time()))

    // each ClockAction is added to the list of registered actions
    // in order of decreasing priority
    def createActionList (ca:ClockAction, 
                          l:List[ClockAction]):List[ClockAction] = {

        if ((l.isEmpty()) ||
            (ca.priority < l.first().priority))
            List.cons(ca, l)
        else
            List.cons(l.first(),
                      createActionList(ca, l.rest()))
    }

    def register (act:(Int)=>Unit, priority:Int):Unit = {
        val tempAction = new ClockAction(act, priority)
        actions = createActionList(tempAction, actions) 
    }

    def tick ():Unit = {
        currentTime += 1
        callActions()
    }

    // slightly more descriptive than using Pairs
    protected class ClockAction (a:(Int)=>Unit, p:Int) {
        def action = a
        val priority = p
    }
}
