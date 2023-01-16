import java.util.*
import javafx.application.Platform
class Model{
    // represent my board
    private val sizeOuter = 50
    private val sizeInner = 75

    private val views = ArrayList<IView>()
    private val board = Array(sizeOuter) { BooleanArray(sizeInner) } // initialed to all false
    private val boardcopy = Array(sizeOuter) { BooleanArray(sizeInner) }

    private var choice = 1

    private var manual = false

    private val timer = Timer("Timer Thread",false)

    private class Task(val model:Model) : TimerTask(){
        override fun run(){
//            println("Task: "+java.lang.Thread.currentThread().getName())
            Platform.runLater(Runnable(){
                model.moveNext()
            })
        }
    }

    private class manualTask() : TimerTask(){
        override fun run(){
            println("Manual")
        }
    }

    private var autotask = Task(this)

    private var frame = 0

    // view management
    fun addView(view: IView) {
        views.add(view)
    }

    fun removeView(view: IView) {
        views.remove(view)
    }

    fun notifyView() {
        for (view in views) {
            view.update()
        }
    }

    fun boardval():Array<BooleanArray>{
        return board
    }

    fun reactOn(i:Int, j:Int){
        when(choice){
            1 -> {
                setOnBoard(i,j,true)
                setOnBoard(i+1,j,true)
                setOnBoard(i,j+1,true)
                setOnBoard(i+1,j+1,true)
            }
            2 -> {
                setOnBoard(i,j,false)
                setOnBoard(i+1,j,true)
                setOnBoard(i+2,j,false)
                setOnBoard(i,j+1,true)
                setOnBoard(i+1,j+1,false)
                setOnBoard(i+2,j+1,true)
                setOnBoard(i,j+2,true)
                setOnBoard(i+1,j+2,false)
                setOnBoard(i+2,j+2,true)
                setOnBoard(i,j+3,false)
                setOnBoard(i+1,j+3,true)
                setOnBoard(i+2,j+3,false)
            }
            3 -> {
                setOnBoard(i,j,true)
                setOnBoard(i,j+1,true)
                setOnBoard(i,j+2,true)
//                setOnBoard(i,j,false)
//                setOnBoard(i+1,j,true)
//                setOnBoard(i+2,j,false)
//                setOnBoard(i,j+1,false)
//                setOnBoard(i+1,j+1,true)
//                setOnBoard(i+2,j+1,false)
//                setOnBoard(i,j+2,false)
//                setOnBoard(i+1,j+2,true)
//                setOnBoard(i+2,j+2,false)
            }
            4 -> {
                setOnBoard(i,j,false)
                setOnBoard(i+1,j,true)
                setOnBoard(i,j+1,true)
                setOnBoard(i+1,j+1,true)
                setOnBoard(i,j+2,true)
                setOnBoard(i+1,j+2,true)
                setOnBoard(i,j+3,true)
                setOnBoard(i+1,j+3,false)
            }
            5 -> {
                setOnBoard(i,j,false)
                setOnBoard(i+1,j,true)
                setOnBoard(i+2,j,false)
                setOnBoard(i,j+1,false)
                setOnBoard(i+1,j+1,false)
                setOnBoard(i+2,j+1,true)
                setOnBoard(i,j+2,true)
                setOnBoard(i+1,j+2,true)
                setOnBoard(i+2,j+2,true)
            }
        }
    }

    fun setOnBoard(i:Int, j: Int, se : Boolean){
        if ((i < 50) and (j < 75)){
            board[i][j] = se
        }
    }

    fun setChoice(i:Int){
        choice = i
    }

    fun getChoice():Int{
        return choice
    }

    fun frameAdd(){
        frame++
    }

    fun getFrame() : Int{
        return frame
    }

    fun clearAll(){
        for (i in 0..49){
            for (j in 0..74){
                board[i][j] = false
            }
        }
        this.notifyView()
    }

    fun updateBoard(){
//        for (i in 0..49){  // make a copy of the board
//            System.arraycopy(board[i],0,boardcopy[i],0,sizeOuter)
//            println("${i} ${boardcopy[i].size}")
//        }

        for (a in 0..49){
            for (b in 0..74){
                boardcopy[a][b] = board[a][b]
            }
        }

        for (i in 0..49){
            for (j in 0..74){
//                println("i${i}j${j}")
                board[i][j] = alive(i, j, boardcopy)
//                if (board[i][j] != boardcopy[i][j]){
//                    println("Board diff in ${i} ${j} and ${board[i][j]} ${boardcopy[i][j]}")
//                }
            }
        }

    }

    fun update() {
//        println("update:" + java.lang.Thread.currentThread().getName())
        timer.scheduleAtFixedRate(autotask,0,1000)
    }

    fun manualOn(){
        autotask.cancel()
        timer.purge()
        manual = true
    }

    fun manualOff(){
        autotask = Task(this)
        timer.scheduleAtFixedRate(autotask,0,1000)
        manual = false
    }

    fun moveNext(){
        this.updateBoard()
        this.frameAdd()
        this.notifyView()
    }

    fun ifManual():Boolean{
        return manual
    }

    fun cancel(){
        timer.cancel()
    }
}

fun alive(i:Int,j:Int,boardcopy:Array<BooleanArray>):Boolean{
    var count = 0
    var m = -1
    var n = -1
    while (m < 2){
        while (n < 2) {
            if ((m == 0) and (n == 0)) {
                n++
                continue
            }
            if (validity(m + i, n + j)) {
//                if ((i>40) and (j > 49)){ println("${i} ${j}")}
                if (boardcopy[m + i][n + j]) {
                    count++
//                    println("coord${i} ${j} is ${m+i} ${n+j} yes")
                }
            }
            n ++
        }
        n = -1
        m ++
    }
    if (count == 3){ // becomes alive with exactly 3
        return true
    }else if (count == 2){
        if (boardcopy[i][j]){
            return true
        }else{
            return false
        }
    }else{
        return false
    }
}

fun validity(i : Int, j : Int): Boolean{
    if ((i >= 0) and (i < 50) and (j >= 0) and (j < 75)){
        return true
    }
    return false
}