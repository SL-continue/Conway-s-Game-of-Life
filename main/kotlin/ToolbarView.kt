import javafx.scene.control.Button
import javafx.scene.control.ToolBar
import javafx.scene.image.ImageView
import javafx.scene.image.Image
import java.io.File
import java.lang.StringBuilder
class ToolbarView(private val model : Model) : IView, ToolBar() {
    init {
        // add buttons to toolbar
        val path = System.getProperty("user.dir")+"\\src\\main\\resources"
        val blockgraph = File("${path}\\block.png")
        val blockview = ImageView(Image(blockgraph.inputStream()))
        blockview.isPreserveRatio = true
        blockview.setFitHeight(12.0)
        val block = Button("Block", blockview)

        val beehivegraph = File("${path}\\beehive.png")
        val beehiveview = ImageView(Image(beehivegraph.inputStream()))
        beehiveview.isPreserveRatio = true
        beehiveview.setFitHeight(12.0)
        val beehive = Button("Beehive",beehiveview)

        val blinkergraph = File("${path}\\blinker.png")
        val blinkerview = ImageView(Image(blinkergraph.inputStream()))
        blinkerview.isPreserveRatio = true
        blinkerview.setFitHeight(12.0)
        val blinker = Button("Blinker",blinkerview)

        val toadgraph = File("${path}\\toad.png")
        val toadview = ImageView(Image(toadgraph.inputStream()))
        toadview.isPreserveRatio = true
        toadview.setFitHeight(12.0)
        val toad = Button("Toad",toadview)

        val glidergraph = File("${path}\\glider.png")
        val gliderview = ImageView(Image(glidergraph.inputStream()))
        gliderview.isPreserveRatio = true
        gliderview.setFitHeight(12.0)
        val glider = Button("Glider",gliderview)

        val clear = Button("Clear")
        val manual = Button("Manual")
        val next = Button("Next")
        this.items.add(block)
        this.items.add(beehive)
        this.items.add(blinker)
        this.items.add(toad)
        this.items.add(glider)
        this.items.add(clear)
        this.items.add(manual)
        this.items.add(next)
        next.setVisible(false)
        block.setOnAction{ event ->
            model.setChoice(1)
        }
        beehive.setOnAction{ event ->
            model.setChoice(2)
        }
        blinker.setOnAction{ event ->
            model.setChoice(3)
        }
        toad.setOnAction{ event ->
            model.setChoice(4)
        }
        glider.setOnAction{ event ->
            model.setChoice(5)
        }
        clear.setOnAction{ event ->
            model.setChoice(6)
            model.clearAll()
        }
        manual.setOnAction{ event ->
            model.setChoice(7)
            if (model.ifManual()){
                println("Manual off")
                next.setVisible(false)
                model.manualOff()
            }else{
                println("Manual On")
                next.setVisible(true)
                model.manualOn()
            }
            model.notifyView()
        }
        next.setOnAction{
            model.moveNext()
        }
    }

    override fun update() {
        // update my button state
    }
}