package blackjack

import blackjack.controller.Casino
import blackjack.ui.InputView
import blackjack.ui.ResultView

fun main() {
    val casino = Casino(InputView(), ResultView())
    casino.playBlackjack()
}
