
/***********************************************************************
 * Grader class
 * 
 * Implements graders
 *
 ********************************************************************** */


object Grader {

    def create (name:String, birthplace:Room, speed:Int, miserly:Int):Grader = {
        val obj = new Grader(name, birthplace, speed, miserly)
        obj.install()
        obj
    }

}


class Grader protected (n:String,l:Room,s:Int,m:Int) 
    extends AutonomousPerson(n,l,s,m) {

    override def ask ():Unit = {

        def isCompleted (t:Thing):Boolean = 
            t.checkCompletedHomework.isSome()
        def toCompleted (t:Thing):CompletedHomework =
            t.checkCompletedHomework.valOf()

        val homeworks = 
            things().filter(isCompleted).map(toCompleted)

        if (homeworks.isEmpty())
            say("Get off reddit you lazy bugger and get me something" + 
                " that type checks!")

        for (h <- homeworks) {
            say("Let's see. What's this? An infinite loop? Mmmm...")
            report(name() + " finishes grading " + h.name())
            GradedHomework.create(h.coreName(),
                                  Adventure.me().location())
            h.destroy()
        }
        
    }

}
