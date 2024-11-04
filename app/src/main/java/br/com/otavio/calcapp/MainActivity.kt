package br.com.otavio.calcapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput: String = ""
    private var lastOperator: Char = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        setupButtons()
    }

    private fun setupButtons() {
        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnClear, R.id.btnEquals, R.id.btnDot
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { handleButtonClick(it as Button) }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener { clearDisplay() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
    }

    private fun handleButtonClick(button: Button) {
        val buttonText = button.text.toString()

        when (buttonText) {
            in "0".."9", "." -> appendNumber(buttonText)
            "+", "-", "*", "/" -> setOperator(buttonText[0])
        }
    }

    private fun appendNumber(number: String) {
        currentInput += number
        display.text = currentInput
    }

    private fun setOperator(operator: Char) {
        if (currentInput.isNotEmpty() && lastOperator == ' ') {
            currentInput += " $operator "
            lastOperator = operator
            display.text = currentInput
        }
    }

    private fun calculateResult() {
        try {
            val tokens = currentInput.split(" ")
            if (tokens.size < 3) return

            val firstNumber = tokens[0].toDouble()
            val secondNumber = tokens[2].toDouble()
            val result = when (lastOperator) {
                '+' -> firstNumber + secondNumber
                '-' -> firstNumber - secondNumber
                '*' -> firstNumber * secondNumber
                '/' -> if (secondNumber != 0.0) firstNumber / secondNumber else throw ArithmeticException("Divisão por zero")
                else -> 0.0
            }

            display.text = result.toString()
            currentInput = result.toString()
            lastOperator = ' '
        } catch (e: Exception) {
            display.text = "Erro"
            currentInput = ""
        }
    }

    private fun clearDisplay() {
        currentInput = ""
        lastOperator = ' '
        display.text = "0"
    }
}
