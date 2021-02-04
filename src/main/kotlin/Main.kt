import javax.swing.JFrame

fun main(args: Array<String>){
    // criacao da janela
    val frame = JFrame("Game Teste")
    frame.isResizable = false
    frame.setLocationRelativeTo(null)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    // criacao do game
    val game = Game(frame)
    game.start()
}