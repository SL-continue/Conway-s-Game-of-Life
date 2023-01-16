import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*
import javafx.scene.input.*


class Main : Application(){
    override fun start(stage: Stage?) {
        val model = Model()

        // our layout is the root of the scene graph
        val root = VBox()

        // views are the children of the vbox
        val toolbar = ToolbarView(model)
        // missing the grid here
        val status = StatusView(model)

        // register views with the model
        model.addView(toolbar)
        model.addView(status)

        // setup and display
        root.getChildren().add(toolbar)
        root.getChildren().add(status)

        stage?.scene = Scene(root)
        stage?.isResizable = false
        stage?.width = 840.0
        stage?.height = 630.0
        stage?.title = "Conway's Game of Life (j855liu)"
        stage?.show()

        stage?.scene?.setOnKeyPressed{ event ->
            println(model.ifManual())
            if (model.ifManual()) {
                if (event.code == KeyCode.N) {
                    println("next")
                    model.moveNext()
                }else if(event.code == KeyCode.M  && event.isControlDown()){
                    println("Manual off by ctrl+M")
                    model.manualOff()
                }
            }else {
                if (event.code == KeyCode.M){
                    model.manualOn()
                    println("Manual on by M")
                }
            }
        }

        stage?.setOnCloseRequest{
            model.cancel()
            Platform.exit()
        }

//        println(java.lang.Thread.currentThread().getName())

        model.update()

    }
}