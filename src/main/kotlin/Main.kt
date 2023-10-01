import kotlin.random.Random
import kotlin.random.nextInt

open class Creature(val attack: Int, val defense: Int, var health: Int) {
    open val damageRange: IntRange = 1..6

    open fun attack(target: Creature) {
        val attackModifier = attack - target.defense + 1
        val diceRolls = attackModifier.coerceAtLeast(1)
        var successfulHit = false

        repeat(diceRolls) {
            val diceRoll = Random.nextInt(1, 7).coerceIn(1, 6)
            if (diceRoll == 5 || diceRoll == 6) {
                successfulHit = true
                val damage = Random.nextInt(damageRange).coerceIn(1, 6)
                target.health -= damage
                println("Удар по $target успешен! Нанесено $damage урона.")
            }
        }

        if (!successfulHit) {
            println("Удар неудачен.")
        }
    }
}

class Player(attack: Int, defense: Int, health: Int) : Creature(attack, defense, health) {
    override val damageRange: IntRange = 1..10
    var healCount: Int = 0

    override fun attack(target: Creature) {
        super.attack(target)
        if (target.health <= 0) {
            println("Монстр побежден!")
        }
    }

    fun heal() {
        val maxHealth = 100
        val healAmount = (maxHealth * 0.3).toInt()
        if (healCount < 4) {
            if (health == maxHealth) println("У вас максимальное количество здоровья")
            else if (health + healAmount <= maxHealth) {
                health += healAmount
                healCount++
                println("Вы исцелились на $healAmount единиц здоровья.")
            } else {
                val remainingHeal = maxHealth - health
                health = maxHealth
                healCount++
                println("Вы исцелились на $remainingHeal единиц здоровья.")
            }
        } else {
            println("Вы не можете исцелить себя больше.")
        }
    }
}

class Monster(attack: Int, defense: Int, health: Int) : Creature(attack, defense, health) {
    override fun attack(target: Creature) {
        super.attack(target)
        if (target.health <= 0) {
            println("Игрок погиб!")
        }
    }
}

fun main() {
    val player = Player(10, 5, 100)
    val monster = Monster(8, 3, 80)

    player.attack(monster)
    monster.attack(player)
    monster.attack(player)
    player.attack(monster)
    player.attack(monster)
    monster.attack(player)
    monster.attack(player)
    player.attack(monster)
    player.attack(monster)
    player.attack(monster)

    player.heal()
    player.heal()
    player.heal()
    player.heal()

    println("Здоровье игрока: ${player.health}")
    println("Здоровье монстра: ${monster.health}")
}
