import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Game(val frame: JFrame) : Canvas(), Runnable {

    // consts
    private val WIDTH = 800
    private val HEIGHT = 600
    private val SCALE = 1
    private val DESIRED_FPS = 60.0
    private val NANO_TIME = 1_000_000_000 * 1 / DESIRED_FPS

    private var square_x = 5
    private var square_y = 5


    private lateinit var thread: Thread
    private var isRunning = false
    private val image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)

    // ctor
    init {
        preferredSize = Dimension(WIDTH * SCALE, HEIGHT * SCALE)
        frame.add(this)
        frame.pack()
        frame.isVisible = true
    }

    @Synchronized
    fun start() {
        thread = Thread(this)
        isRunning = true
        thread.start()
    }

    @Synchronized
    fun stop() {
        isRunning = false
        thread.join()
    }

    // update values
    private fun update() {
        square_x++;
        square_y++;
    }

    // renders on screen
    private fun render() {
        val bf = this.bufferStrategy
        if (bf == null) {
            this.createBufferStrategy(3)
            return
        }
        // limpa
        var g = image.graphics
        g.color = Color.BLACK
        g.fillRect(0, 0, WIDTH, HEIGHT)


        g.color = Color.RED
        g.drawRect(square_x, square_y, 50, 50)

       // desenha no canvas
        g.dispose()
        g = bf.drawGraphics
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null)

        bf.show()
    }

    // game loop
    override fun run() {
        var lastTime = System.nanoTime()
        var delta = 0.0
        var fps = 0
        var timer = System.currentTimeMillis()

        while (isRunning) {
            val now = System.nanoTime()
            delta += (now - lastTime) / NANO_TIME
            lastTime = now

            if (delta >= 1) {
                update()
                render()
                fps++
                delta--
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                println("FPS: $fps")
                fps = 0
                timer += 1000
            }
        }

        stop()
    }


}