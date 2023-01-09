package blackjack.controller

import blackjack.controller.DrawAction.Draw
import blackjack.controller.DrawAction.NoDraw
import blackjack.domain.Dealer
import blackjack.domain.Game
import blackjack.domain.Gamer
import blackjack.domain.Player
import blackjack.ui.InputView
import blackjack.ui.ResultView

class Casino(private val inputView: InputView, private val resultView: ResultView) {

    val dealer = Dealer()
    val gamers: List<Gamer> = inputView.inputNames()
    private val game = Game()

    fun playBlackjack() {
        drawTwoCards()
        showPlayers()
        relay()
        showResult()
        showReport()
    }

    private fun drawTwoCards() {
        repeat(gamers.size) {
            game.drawTwoCards(gamers[it])
        }
        game.drawTwoCards(dealer)
    }

    fun names(): String = gamers.joinToString(", ") { player -> player.name }

    private fun showPlayers() {
        resultView.showPlayers(this)
    }

    private fun showResult() {
        resultView.showResult(this)
    }

    private fun showReport() {
        resultView.showReport(this)
    }

    private fun relay() {
        var index = 0
        do {
            val gamer = gamers[index]
            val action = ask(gamer)
            if (action == Draw) {
                game.draw(gamer)
                resultView.showPlayer(gamer)
                if (gamer.canDraw().not()) break
                continue
            }
            if (action == NoDraw) index++
        } while (index < gamers.size)

        if (dealer.canDraw()) {
            resultView.showPlayer(dealer)
            game.draw(dealer)
        }
    }

    private fun ask(player: Player): DrawAction = inputView.ask(player)
}

enum class DrawAction {
    Draw, NoDraw
}
