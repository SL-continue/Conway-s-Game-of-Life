import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class StatusView(private val model : Model) : IView, VBox() {
    private var x = 0
    private var y = 0
    private var change = false
    private val pane = GridPane()
    private val botline = HBox()
    private val label1 = Label()
    private val label2 = Label()
    init {
        // add stuff
        pane.isGridLinesVisible = false
        pane.hgap = 1.0
        pane.vgap = 1.0
        for (i in 0..74){  // i is column number
            for (j in 0..49){  // j is row number
                val rect = Rectangle(10.0,10.0)
                rect.fill = Color.AZURE
                pane.add(rect,i,j)
                rect.setOnMouseClicked{
                    model.reactOn(j,i)
                    x = i
                    y = j
                    println("${model.getChoice()} ${x} ${y}")
                    if (model.getChoice() != 6){  change = true }
                    model.notifyView()
                }
            }
        }
        botline.getChildren().add(label1)
        botline.getChildren().add(label2)
        label1.setText("Cleared")
        label1.setPrefWidth(200.0)
        label2.setText("Frame 0")
        botline.setSpacing(500.0)
        this.getChildren().add(pane)
        this.getChildren().add(botline)
    }

    override fun update() {
        // react to updates from model
        val board = model.boardval()
        for (node in pane.getChildren()){
            val nodei = GridPane.getColumnIndex(node)
            val nodej = GridPane.getRowIndex(node)
            if (board[nodej][nodei]){
                (node as Rectangle).fill = Color.BLACK
            }else{
                (node as Rectangle).fill = Color.AZURE
            }
        }
        if (change){
            val type = model.getChoice()
            when(type){
                1 -> { label1.setText("Created block at ${x},${y}")}
                2 -> { label1.setText("Created beehive at ${x},${y}")}
                3 -> { label1.setText("Created blinker at ${x},${y}")}
                4 -> { label1.setText("Created toad at ${x},${y}")}
                5 -> { label1.setText("Created glider at ${x},${y}")}
            }
        }
        if (model.getChoice() == 6){ label1.setText("Cleared")}
        if (model.getChoice() == 7){
            if (model.ifManual()){
                label1.setText("Manual On")
            }else{
                label1.setText("Manual Off")
            }
        }
        change = false
        val fr = model.getFrame()
        println(fr)
        label2.setText("Frame ${fr}")
    }

}