package com.example.proteamfantasy11rj.tudo_game

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class GameActivity : AppCompatActivity() {
    private lateinit var currentPlayerTextView: TextView
    private lateinit var player1ScoreTextView: TextView
    private lateinit var player2ScoreTextView: TextView
    private lateinit var newGameButton: Button
    private lateinit var gameGrid: GridLayout

    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private var currentPlayer = 1
    private var player1Score = 0
    private var player2Score = 0
    private val buttons = Array(3) { Array<Button?>(3) { null } }
    private val gameState = Array(3) { IntArray(3) { 0 } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Get player names from intent
        player1Name = intent.getStringExtra("PLAYER1_NAME") ?: "Player 1"
        player2Name = intent.getStringExtra("PLAYER2_NAME") ?: "Player 2"

        initializeViews()
        setupGame()
        updateUI()
    }

    private fun initializeViews() {
        currentPlayerTextView = findViewById(R.id.currentPlayerTextView)
        player1ScoreTextView = findViewById(R.id.player1ScoreTextView)
        player2ScoreTextView = findViewById(R.id.player2ScoreTextView)
        newGameButton = findViewById(R.id.newGameButton)
        gameGrid = findViewById(R.id.gameGrid)

        newGameButton.setOnClickListener { resetGame() }
    }

    private fun setupGame() {
        // Create the game grid
        for (i in 0..2) {
            for (j in 0..2) {
                val button = Button(this).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 120
                        height = 120
                        setMargins(8, 8, 8, 8)
                        setGravity(Gravity.CENTER)
                    }
                    setBackgroundColor(Color.LTGRAY)
                    tag = "${i}$j"
                    setOnClickListener { onCellClicked(this, i, j) }
                }
                buttons[i][j] = button
                gameGrid.addView(button)
            }
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (gameState[row][col] != 0) return

        gameState[row][col] = currentPlayer
        button.apply {
            text = if (currentPlayer == 1) "X" else "O"
            setBackgroundColor(
                if (currentPlayer == 1) 
                    Color.rgb(173, 216, 230) // Light Blue
                else 
                    Color.rgb(255, 182, 193) // Light Pink
            )
            isEnabled = false
        }

        if (checkWinner(row, col)) {
            if (currentPlayer == 1) player1Score++ else player2Score++
            disableAllButtons()
            updateUI()
            return
        }

        if (isBoardFull()) {
            updateUI()
            return
        }

        currentPlayer = if (currentPlayer == 1) 2 else 1
        updateUI()
    }

    private fun checkWinner(row: Int, col: Int): Boolean {
        val player = gameState[row][col]
        
        // Check row
        if (gameState[row].all { it == player }) return true
        
        // Check column
        if (gameState.all { it[col] == player }) return true
        
        // Check diagonals
        if (row == col && (0..2).all { gameState[it][it] == player }) return true
        if (row + col == 2 && (0..2).all { gameState[it][2 - it] == player }) return true
        
        return false
    }

    private fun isBoardFull(): Boolean {
        return gameState.all { row -> row.all { it != 0 } }
    }

    private fun disableAllButtons() {
        buttons.forEach { row ->
            row.forEach { button ->
                button?.isEnabled = false
            }
        }
    }

    private fun resetGame() {
        gameState.forEach { row -> row.fill(0) }
        buttons.forEach { row ->
            row.forEach { button ->
                button?.apply {
                    text = ""
                    setBackgroundColor(Color.LTGRAY)
                    isEnabled = true
                }
            }
        }
        currentPlayer = 1
        updateUI()
    }

    private fun updateUI() {
        currentPlayerTextView.text = when {
            checkWinner(0, 0) || checkWinner(0, 1) || checkWinner(0, 2) ||
            checkWinner(1, 0) || checkWinner(1, 1) || checkWinner(1, 2) ||
            checkWinner(2, 0) || checkWinner(2, 1) || checkWinner(2, 2) -> 
                "${if (currentPlayer == 1) player1Name else player2Name} Wins!"
            isBoardFull() -> "Game Draw!"
            else -> "${if (currentPlayer == 1) player1Name else player2Name}'s Turn"
        }

        player1ScoreTextView.text = "$player1Name: $player1Score"
        player2ScoreTextView.text = "$player2Name: $player2Score"
    }
}
